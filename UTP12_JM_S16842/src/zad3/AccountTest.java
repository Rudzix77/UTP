package zad3;

import java.beans.*;
import java.beans.PropertyVetoException;

class AccountTest {

	public static void main(String[] args)
	{
		Account acc1 = null, acc2 = null;

		try {
			acc1 = new Account(100);	// stan początkowy konta = 100
			acc2 = new Account();		// stan początkowy konta = 0

			// ustalamy dla kont acc1, acc2 nieprzekraczalny limit debetu na -200 za pomocą AccountLimitator
			AccountLimitator acclim = new AccountLimitator(-200);

			// Rejestrujemy słuchaczy zmian
			acc1.addVetoableChangeListener(acclim);
			acc2.addVetoableChangeListener(acclim);

			// ustawiamy dla kont acc1, acc2 obsługę reakcji na zmianę stanu konta za pomocą AccountChange
			AccountChange accountChange = new AccountChange();

			// Rejestrujemy słuchaczy zmian
			acc1.addPropertyChangeListener(accountChange);
			acc2.addPropertyChangeListener(accountChange);

			System.out.println(acc1);
			System.out.println(acc2);
			System.out.println();

			acc2.deposit(1000);
			System.out.println(acc1);
			System.out.println(acc2);
			System.out.println();

			acc1.withdraw(250);
			System.out.println(acc1);
			System.out.println(acc2);
			System.out.println();

			acc2.transfer(acc1, 1200);

			System.out.println(acc1);
			System.out.println(acc2);
			System.out.println();

			acc2.transfer(acc1, 1);
			System.out.println(acc1);
			System.out.println(acc2);

		} catch (PropertyVetoException e) {
			System.out.println(e.getMessage());
			System.out.println(acc1);
			System.out.println(acc2);
		}
	}
}

