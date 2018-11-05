package zad2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@FunctionalInterface
public interface MousePressListener extends MouseListener {
	@Override
	default void mouseEntered(final MouseEvent e) {
	}

	@Override
	default  void mouseExited(final MouseEvent e) {
	}

	@Override
	default  void mousePressed(final MouseEvent e) {
	}

	@Override
	default  void mouseReleased(final MouseEvent e) {
	}
}
