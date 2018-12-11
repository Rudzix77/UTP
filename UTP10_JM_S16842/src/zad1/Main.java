package zad1;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class Main extends JFrame{

	private static Main frame;

	private ArrayList<XButton> taskButtons = new ArrayList();

	private JTextArea area;
	private JPanel panel;

	public Main(){
		super("Thread pool");
		setSize(new Dimension(350, 500));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		area = new JTextArea();
			area.setEnabled(false);
			((DefaultCaret) area.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		add(new JScrollPane(area), BorderLayout.CENTER);


		panel = new JPanel(new GridLayout(1, 0));
			add(panel, BorderLayout.PAGE_END);


		JButton newTask = new JButton("Create New");
			newTask.addActionListener(a -> createXButton());

		JButton stop = new JButton("Stop");
			stop.addActionListener(a -> forEachTask(e -> e.stopTask()));

		JPanel menu = new JPanel(new GridLayout(2, 0));
			menu.add(newTask);
			menu.add(stop);

		add(menu, BorderLayout.PAGE_START);

	}

	public void log(String text){
		area.append(text + "\n");
	}

	public void forEachTask(Consumer<XButton> c){
		taskButtons.forEach(c);
	}

	public XButton find(Predicate<XButton> p) throws Exception{
		Optional<XButton> b = taskButtons.stream().filter(p).findFirst();

		if(b.isPresent()){
			return b.get();
		}

		throw new Exception("Not found");
	}

	private XButton createXButton(){
		XButton b = new XButton();

		panel.add(b);
		panel.updateUI();
		taskButtons.add(b);

		return b;
	}

	public void removeXButton(XButton b){
		panel.remove(b);
		panel.updateUI();
		taskButtons.remove(b);
	}

	public static Main getFrame(){
		if(frame == null){
			frame = new Main();
		}

		return frame;
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(() -> getFrame());
	}
}

