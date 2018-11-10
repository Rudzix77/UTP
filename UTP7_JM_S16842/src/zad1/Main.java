package zad1;

public class Main {
	public static void main(String[] args){

		String path = System.getProperty("user.home") + "/dictionary.txt";

		Dictionary d = new Dictionary(path);

		System.out.println("Załadowane definicje:");
		d.definitions.entrySet().forEach(System.out::println);
		System.out.println();

		for(String s : new String[]{"lol", "bacca"}){
			System.out.println("Definicje dla: " + s);
			d.lookup(s).forEach(System.out::println);
			System.out.println();
		}

		String def = "bacca";

		for(String s : new String[]{"stary", "ziomo", "algorytm", "koles"}){
			System.out.print(String.format("Dodaje definicje [%s - %s] -> ", def, s));
			System.out.println(d.add(def, s) ? "Dodano" : "Juz wystąpiła");
		}

		System.out.println();

		System.out.println(def + " po operacjach add()");
		d.lookup(def).forEach(System.out::println);

		System.out.println();

		System.out.print(String.format("Usuwam definicję %s numer %d] -> ", def, 1));
		System.out.println(d.delete(def, 1) ? "Skasowano" : "Wybrana definicja nie istnieje");
		System.out.print(String.format("Usuwam definicję %s numer %d] -> ", def, 5));
		System.out.println(d.delete(def, 5) ? "Skasowano" : "Wybrana definicja nie istnieje");

		System.out.println();

		System.out.println(def + " po operacjach delete()");
		d.lookup(def).forEach(System.out::println);

		System.out.println();

		System.out.print(String.format("Aktualizuje definicję [%s - %s] na %s -> ", def, "koles", "szprycer"));
		System.out.println(d.update(def, "koles", "szprycer") ? "Zaktualizowano" : "Wybrana definicja nie istniala");

		System.out.print(String.format("Aktualizuje definicję [%s - %s] na %s -> ", def, "lol", "szprycer"));
		System.out.println(d.update(def, "lol", "szprycer") ? "Zaktualizowano" : "Wybrana definicja nie istnieje");


		System.out.println();

		System.out.println(def + " po operacjach update()");
		d.lookup(def).forEach(System.out::println);

		System.out.println();

		d.save();

		Dictionary d2 = new Dictionary(path);

		System.out.println();

		System.out.println("Po operacji save() - ponowny odczyt");
		d.lookup(def).forEach(System.out::println);


	}
}
