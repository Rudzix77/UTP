/**
 *
 *  @author Jankowski Michał S16842
 *
 */

package zad1;


import java.util.Locale;

public class Purchase {
	int id;

	String name;
	String lastName;

	String product;
	float amount;
	float price;

	private Purchase(int id, String name, String lastName, String product, float amount, float price){
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.product = product;
		this.amount = amount;
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format(Locale.US,"c%05d;%s %s;%s;%.1f;%.1f",id, lastName, name, product, amount, price);
	}

	public static Purchase of(String data){
		String[] e = data.split(";");
		String[] name = e[1].split(" ");

		return new Purchase(Integer.parseInt(e[0].substring(1)), name[1], name[0], e[2], Float.parseFloat(e[3]), Float.parseFloat(e[4]));

	}
}
