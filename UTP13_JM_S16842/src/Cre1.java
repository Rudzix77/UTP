import java.sql.*;

public class Cre1 {

	static public void main(String[] args) {
		new Cre1();
	}

	Statement stmt;

	Cre1() {
		Connection con = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();

			con = DriverManager.getConnection("jdbc:derby://localhost:1527//Users/yaneek/tmp2/ksidb-copy");

			stmt = con.createStatement();
		} catch (Exception exc) {
			System.out.println(exc);
			System.exit(1);
		}

		// metoda dropTable jest naszą własną metodą napisaną dla skrócenia programu
		// usuwa ona tabelę podaną jako argument
		// Aby w każdych okolicznościach stworzyć nową tabelę AUTOR
		// musimy usunąć ew.  już istniejącą tabelę AUTOR
		dropTable("POZYCJE"); // usunięcie tabeli pochodnej, będącej w relacji z tabelą AUTOR
		dropTable("AUTOR");   // usunięcie tabeli AUTOR

		String crestmt = "create table AUTOR (AUTID integer not null generated by default as identity, NAME varchar(255) not null, PRIMARY KEY(AUTID))";

		try {
			stmt.execute(crestmt);
		} catch (SQLException exc) {                      // przechwycenie wyjątku:
			System.out.println("SQL except.: " + exc.getMessage());
			System.out.println("SQL state  : " + exc.getSQLState());
			System.out.println("Vendor errc: " + exc.getErrorCode());
			System.exit(1);
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException exc) {
				System.out.println(exc);
				System.exit(1);
			}
		}
	}

	private void dropTable(String name){
		try {
			stmt.execute("DROP TABLE " + name);
		} catch (SQLException exc) {                      // przechwycenie wyjątku:
			System.out.println("SQL except.: " + exc.getMessage());
			System.out.println("SQL state  : " + exc.getSQLState());
			System.out.println("Vendor errc: " + exc.getErrorCode());
			System.exit(1);
		}
	}
}