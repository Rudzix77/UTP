package zad1;

import com.sun.tools.corba.se.idl.constExpr.Not;

public class Main {
	public static void main(String[] args) {

		String path = System.getProperty("user.home") + "/dictionary.txt";

		Dictionary d = new Dictionary(path);

		try{
			d.lookup("los");
		}catch (NotFoundException e){
			System.out.println("KURWA");
		}


		System.out.println("Załadowane definicje:");
		d.definitions.entrySet().forEach(System.out::println);
		System.out.println();


		for(String s : new String[]{"lol", "bacca"}){
			System.out.println("Definicje dla: " + s);

			try{
				d.lookup(s).forEach(System.out::println);
			}catch(NotFoundException e){
				System.out.println("Wybrany klucz nie istnieje");
			}

			System.out.println();
		}

		String def = "bacca";

		for(String s : new String[]{"stary", "ziomo", "algorytm"}){
			System.out.println(String.format("Dodaje definicje [%s - %s]", def, s));
			d.add(def, s);
		}

		System.out.println();

		System.out.println(def + " po operacjach add()");

		try{
			d.lookup(def).forEach(System.out::println);
		}catch(NotFoundException e){
			System.out.println("Wybrany klucz nie istnieje");
		}

		System.out.println();


		try{
			System.out.println(String.format("Usuwam definicję %s numer %d", def, 1));
			d.delete(def, 1);
		}catch(NotFoundException e){
			System.out.println("Wybrany klucz nie istnieje");
		}


		try{
			System.out.println(String.format("Usuwam definicję %s numer %d", def, 5));
			d.delete(def, 5);
		}catch(NotFoundException e){
			System.out.println("Wybrany klucz nie istnieje");
		}

		System.out.println();

		System.out.println(def + " po operacjach delete()");

		try{
			d.lookup(def).forEach(System.out::println);
		}catch(NotFoundException e){
			e.printStackTrace();
		}

		System.out.println();

		try{
			System.out.println(String.format("Aktualizuje definicję [%s - %s] na %s -> ", def, "koleś", "szprycer"));
			d.update(def, "koleś", "szprycer");
		}catch(NotFoundException e){
			System.out.println("Wybrany klucz nie istnieje");
		}

		try{
			System.out.println(String.format("Aktualizuje definicję [%s - %s] na %s -> ", def, "ziomo", "👌"));
			d.update(def, "ziomo", "👌");
		}catch(NotFoundException e){
			System.out.println("Wybrany klucz nie istnieje");
		}


		System.out.println();

		System.out.println(def + " po operacjach update()");

		try{
			d.lookup(def).forEach(System.out::println);
		}catch(NotFoundException e){
			System.out.println("Wybrany klucz nie istnieje");
		}

		System.out.println();

		//System.out.println(d.save() ? "Poprawnie zapisano zawartość słownika do pliku" : "Nie udało się zapisać zawartości słownika do pliku!");

		Dictionary d2 = new Dictionary(path);

		System.out.println();

		System.out.println("Po operacji save() - ponowny odczyt");

		System.out.println();

		System.out.println("Załadowane definicje:");

		System.out.println();

		d2.definitions.entrySet().forEach(System.out::println);
		System.out.println();
	}
}
