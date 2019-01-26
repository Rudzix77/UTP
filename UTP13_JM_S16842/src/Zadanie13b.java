import java.sql.*;

public class Zadanie13b {

	public static void main(String[] args){
		new Zadanie13b();
	}

	private Statement stmt;

	public Zadanie13b() {
		Connection con = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();

			con = DriverManager.getConnection("jdbc:derby://localhost:1527//Users/yaneek/tmp2/ksidb");

			stmt = con.createStatement();
		} catch (Exception exc) {
			System.out.println(exc);
			System.exit(1);
		}


		String sel = "SELECT * FROM Pozycje";
		try  {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = stmt.executeQuery(sel);
			ResultSetMetaData rsmd = rs.getMetaData();

			int cc = rsmd.getColumnCount();

			for (int i = 1; i <= cc; i++)
				System.out.print(rsmd.getColumnLabel(i) + "     ");

			System.out.println("\n------------------------------ przewijanie do góry");

			rs.afterLast();

			while(rs.previous()){
				for (int i = 1; i <= cc; i++) System.out.print(rs.getString(i) + ", ");
				System.out.println("");
			}

			System.out.println("\n----------------------------- pozycjonowanie abs.");
			int[] poz =  { 3, 7, 9  };
			for (int p = 0; p < poz.length; p++)  {
				System.out.print("[ " + poz[p] + " ] ");

				rs.absolute(poz[p]);

				for (int i = 1; i <= cc; i++) System.out.print(rs.getString(i) + ", ");
				System.out.println("");
			}
			stmt.close();
			con.close();
		} catch (SQLException exc)  {
			System.out.println(exc.getMessage());
		}
	}
}