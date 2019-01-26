import java.sql.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Ins1 {

	static public void main(String[] args) {
		new Ins1();
	}

	Statement stmt;

	Ins1()  {
		Connection con = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();

			con = DriverManager.getConnection("jdbc:derby://localhost:1527//Users/yaneek/tmp2/ksidb");

			stmt = con.createStatement();
		} catch (Exception exc) {
			System.out.println(exc);
			System.exit(1);
		}
		// nazwy wydawców do wpisywania do tabeli
		String[] wyd =  { "PWN", "PWE", "Czytelnik", "Amber", "HELION", "MIKOM" };

		// pierwszy numer wydawcy do wpisywania do tabeli: PWN ma numer 15, PWE ma 16, ...
		int beginKey = 15;

		AtomicInteger n = new AtomicInteger(beginKey - 1);

		String[] ins = Arrays.stream(wyd).map(e -> "INSERT INTO Wydawca VALUES (" +n.incrementAndGet() + ",'"+ e +"')").toArray(String[]::new);

		int insCount = 0;   // ile rekordów wpisano
		try  {
			for (int i=0; i < ins.length; i++){
				stmt.execute(ins[i]);
				insCount++;
			}

		} catch (SQLException exc) {
			System.out.println("SQL except.: " + exc.getMessage());
			System.out.println("SQL state  : " + exc.getSQLState());
			System.out.println("Vendor errc: " + exc.getErrorCode());
		}

		System.out.printf("Wpisano %s rekordów", insCount);


		beginKey = 15;
		int delCount =  0;
		try  {
			// przygotowanie instrukcji prekompilowanej
			PreparedStatement pS = con.prepareStatement("DELETE FROM Wydawca WHERE Name = ? AND Wydid = ?");	// usunięcie z tabeli WYDAWCA rekordu o podanej nazwie wydawcy z tablicy wyd lub o podanym numerze wydawcy zaczynającym się od beginKey
			for (int i=0; i < wyd.length; i++)   {
				pS.setString(1, wyd[i]);
				pS.setInt(2, beginKey++);
				pS.execute();
			}

			con.close();
		} catch(SQLException exc)  {
			System.out.println(exc);
		}

	}




} 