package zad1;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.*;


public class Main {

	static ArrayList<XButton> taskButtons = new ArrayList();

	static JTextArea area = new JTextArea();


	public static void main(String[] args){
		SwingUtilities.invokeLater(() -> {
			JFrame f = new JFrame("Thread pool");
				f.setSize(new Dimension(350, 500));


			DefaultCaret caret = (DefaultCaret) area.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

			f.add(new JScrollPane(area), BorderLayout.CENTER);
			area.setEnabled(false);


			JPanel panel = new JPanel(new GridLayout(1, 0));
			f.add(panel, BorderLayout.PAGE_END);

			JPanel menu = new JPanel(new GridLayout(2, 0));

			f.add(menu, BorderLayout.PAGE_START);


			JButton newTask = new JButton("Create New");

				newTask.addActionListener(a -> {
						XButton b = new XButton();

						panel.add(b);
						panel.updateUI();
						taskButtons.add(b);
				});

				JButton stop = new JButton("Stop");

				stop.addActionListener(a ->{
					taskButtons.forEach(e -> {
						e.stopTask();
					});
				});

			menu.add(newTask);
			menu.add(stop);


			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.setVisible(true);

		});
	}
}

