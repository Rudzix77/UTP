package zad3;

import java.beans.*;
import java.io.Serializable;
import java.util.Locale;

public class Account implements Serializable {

	private static int sid = 1;
	private int id = sid++;

	private double balance;

	private PropertyChangeSupport chg = new PropertyChangeSupport(this);
	private VetoableChangeSupport vetos = new VetoableChangeSupport(this);

	Account() throws PropertyVetoException {
		this(0);
	}

	Account(double balance) throws PropertyVetoException {
		setBalance(balance);
	}

	void deposit(double n) throws PropertyVetoException {
		setBalance(getBalance() + n);
	}

	void withdraw(double n) throws PropertyVetoException {
		setBalance(getBalance() - n);
	}

	void transfer(Account acc, double n) throws PropertyVetoException {
		setBalance(getBalance() - n);
		acc.setBalance(acc.getBalance() + n);
	}

	private double getBalance() {
		return balance;
	}

	private synchronized void setBalance(double balance) throws PropertyVetoException {
		vetos.fireVetoableChange(String.valueOf(id), this.balance, balance);
		chg.firePropertyChange(String.valueOf(id), this.balance, balance);

		this.balance = balance;
	}

	synchronized void addPropertyChangeListener(PropertyChangeListener l) {
		chg.addPropertyChangeListener(l);
	}

	synchronized void addVetoableChangeListener(VetoableChangeListener l) {
		vetos.addVetoableChangeListener(l);
	}

	@Override
	public String toString() {
		return String.format(Locale.US,"Acc %d: %.1f", id, balance);
	}
}
