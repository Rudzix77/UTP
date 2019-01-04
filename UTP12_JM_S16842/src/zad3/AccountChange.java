package zad3;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;

public class AccountChange implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		double val = (double) e.getNewValue();
		System.out.println(String.format(Locale.US,"%s: Value changed from %.1f to %.1f" + (val < 0 ? ", balance < 0!" : ""), e.getPropertyName(), e.getOldValue(), e.getNewValue()));
	}
}
