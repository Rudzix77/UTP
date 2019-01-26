import java.sql.*;

import static java.sql.ResultSet.FETCH_REVERSE;

public class Zadanie13 {

	public static void main(String[] args){
		new Zadanie13();
	}

	private Statement stmt;

	public Zadanie13() {
		Connection con = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();

			con = DriverManager.getConnection("jdbc:derby://localhost:1527//Users/yaneek/tmp2/ksidb");

			stmt = con.createStatement();
		} catch (Exception exc) {
			System.out.println(exc);
			System.exit(1);
		}

		//a
		String sel = "SELECT * FROM Pozycje JOIN Autor ON Autor.autid = Pozycje.autid WHERE Rok > 2000 AND Cena > 30";

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sel);

			while (rs.next()) {

				String title = rs.getString("tytul");
				int release = rs.getInt("rok");
				int price = rs.getInt("cena");
				String surname = rs.getString("name");

				System.out.println("Autor: " + surname);
				System.out.println("Tytuł: " + title);
				System.out.println("Rok wydania: " + release);
				System.out.println("Cena: " + price);
				System.out.println();
			}

			con.close();
			stmt.close();
		} catch (SQLException exc) {
			System.out.println(exc.getMessage());
		}

	}
}
