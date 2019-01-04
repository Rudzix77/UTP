package zad2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws Exception{


		Class<?> XClass = Main.class.getClassLoader().loadClass("zad2.files.A");


		/*
			Super classes
		 */

		Class x = XClass;

		System.out.println("[Nadklasy]");
		while((x = x.getSuperclass()) != null){
			System.out.println(x.getSimpleName());
		}

		/*
			Constructors
		 */

		List<Constructor> constructors = Arrays.stream(XClass.getDeclaredConstructors())
				.filter(e -> e.getParameterCount() >= 1)
				.collect(Collectors.toList());

		System.out.println("[Konstruktory]");
		constructors.forEach(e -> System.out.printf("%s %s(%s)%n", Modifier.toString(e.getModifiers()), XClass.getSimpleName(), Arrays.stream(e.getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining(", "))));

		/*
			Methods
		 */

		List<Method> methods =  Arrays.stream(XClass.getDeclaredMethods())
				.filter(e -> !Modifier.isPrivate(e.getModifiers()))
				.collect(Collectors.toList());

		System.out.println("[Metody]");
		methods.forEach(e-> System.out.printf("%s %s %s(%s)%n", Modifier.toString(e.getModifiers()), e.getReturnType().getSimpleName(), e.getName(), Arrays.stream(e.getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining(", ") )));

		/*
			Fields
		 */

		System.out.println("[Pola]");
		Arrays.stream(XClass.getDeclaredFields())
				.filter(e -> Modifier.isPublic(e.getModifiers()))
				.forEach(e -> System.out.printf("%s %s %s%n", Modifier.toString(e.getModifiers()), e.getType().getName(), e.getName()));

		/*
			Fields of super class
		 */

		System.out.println("[Pola bezpozredniej nadklasy]");
		Arrays.stream(XClass.getSuperclass().getDeclaredFields())
				.forEach(e -> System.out.printf("%s %s %s%n", Modifier.toString(e.getModifiers()), e.getType().getName(), e.getName()));

		/*
			New instance
			@INFO: Needs complex types of parameters to work.
				   'A' class contains constructor & method with String parameter as first ones.
		 */


		System.out.println("[Nowa instancja klasy]");

		if(constructors.size() != 0){
			Object o = constructors.get(0).newInstance(Arrays.stream(constructors.get(0).getParameterTypes()).map((ThrowingFunction<Class, ?>) Class::newInstance).toArray());

			System.out.println(o);

			if(methods.size() != 0){
				System.out.print("Wynik wywolania metody " + methods.get(0).getName() + " -> ");
				System.out.println(methods.get(0).invoke(o,Arrays.stream(methods.get(0).getParameterTypes()).map((ThrowingFunction<Class, ?>) Class::newInstance).toArray()));
			}else{
				System.out.println("Klasa nie zawiera metod o publicznej hermetyzacji");
			}

		}else{
			System.out.println("Klasa nie zawiera konstruktora posiadajcego przynajmniej jeden parametr");
		}


	}
}

@FunctionalInterface
interface ThrowingFunction<T, R> extends Function<T, R>
{
	R doApply(T t)
			throws Throwable;

	default R apply(T t)
	{
		try {
			return doApply(t);
		} catch (Error | RuntimeException e) {
			throw e;
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}
}