package zad1;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T>{

	private static Maybe<?> e = new Maybe(null);

	private T element;

	private Maybe(T element){
		this.element = element;
	}

	public static <T> Maybe<T> of(T s){
		return (s != null) ? new Maybe(s) : (Maybe<T>)e;
	}

	public void ifPresent(Consumer<T> c){
		if(element != null){
			c.accept(element);
		}
	}

	public <S> Maybe<S> map(Function<T, S> f){
		if(element != null){

			S tmp = f.apply(element);

			if(tmp != null){
				return new Maybe(tmp);
			}
		}

		return of(null);
	}

	public T get(){
		if(element != null){
			return element;
		}else{
			throw new NoSuchElementException("maybe is empty");
		}
	}

	public T orElse(T def){
		return (element != null) ? element : def;
	}

	public Maybe<T> filter(Predicate<T> p){
		if(element != null){
			if(!p.test(element)){
				return of(null);
			}
		}

		return this;
	}

	@Override
	public String toString() {
		return (element != null) ? "Maybe has value " + element : "Maybe is empty";
	}
}
