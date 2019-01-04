package zad3;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Locale;

public class AccountLimitator implements VetoableChangeListener {

	private double limit;

	public AccountLimitator(double limit) {
		this.limit = limit;
	}

	@Override
	public void vetoableChange(PropertyChangeEvent e) throws PropertyVetoException {
		double val = (double) e.getNewValue();

		if (val < limit) {
			throw new PropertyVetoException(String.format(Locale.US, "Unacceptable value change: %.1f", val), e);
		}

	}
}
