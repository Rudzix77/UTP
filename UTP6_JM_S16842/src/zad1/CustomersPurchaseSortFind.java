/**
 *
 *  @author Jankowski Michał S16842
 *
 */

package zad1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CustomersPurchaseSortFind {

	List<Purchase> list = new ArrayList();

	public void readFile(String path){
		try(Scanner in = new Scanner(new File(path))){

			while(in.hasNextLine()){
				list.add(Purchase.of(in.nextLine()));
			}

		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	public void showSortedBy(String action){

		System.out.println(action);

		if(action.equals("Nazwiska")){
			list.stream().sorted((a, b) -> {

				int c = a.lastName.compareTo(b.lastName);

				if(c == 0){
					return a.id - b.id;
				}

				return c;
			}).forEach(System.out::println);
		}else if(action.equals("Koszty")){
			list.stream().sorted((a, b) -> {

				float c = (b.amount*b.price) - (a.amount*a.price);

				if(c == 0){
					return a.id - b.id;
				}

				return (int) c;

			}).forEach(e -> System.out.println(String.format(Locale.US, "%s (koszt: %.1f)", e, (e.price*e.amount))));
		}

		System.out.println();
	}

	public void showPurchaseFor(String id){
		System.out.println("Klient "+id);
		list.stream().filter(e -> e.id == Integer.parseInt(id.substring(1))).forEach(System.out::println);
		System.out.println();
	}

}
