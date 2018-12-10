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


			JPanel menu = new JPanel(new GridLayout(2, 0));


			f.add(menu, BorderLayout.PAGE_START);


			JPanel panel = new JPanel(new GridLayout(1, 0));

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


			f.add(new JScrollPane(area), BorderLayout.CENTER);
			area.setEnabled(false);

			f.add(panel, BorderLayout.PAGE_END);

			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.setVisible(true);

		});
	}
}

class XButton extends JButton implements ActionListener, KeyListener {

	private static int idCounter;

	private int id;
	private FutureTask task;
	private XCall call;

	private boolean cClicked;

	public XButton() {
		this.id = ++idCounter;
		setText("T" + id);


		addKeyListener(this);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (task == null) {

			call = new XCall(id);
			task = new XFuture(call, id);

			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.execute(task);
			executor.shutdown();

			this.setText("Suspend T" + id);
		} else {

			if(cClicked){
				cancelTask();
			}else{
				if (call.isCancelled()) {
					call.resume();
					this.setText("Suspend T" + id);
				} else {
					suspendTask();
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_C) {
			cClicked = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_C) {
			cClicked = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	public void suspendTask(){
		call.suspend();
		setText("Continue T" + id);
	}

	public void doneTask(){
		setText("T" + id + " done!");
		setEnabled(false);

		JPanel panel = (JPanel) getParent();

		Executors.newScheduledThreadPool(1).schedule(() -> panel.remove(this), 3, TimeUnit.SECONDS);

		panel.updateUI();

		Main.area.append(String.format("Thread %d: Done \n", id));
	}

	public void cancelTask(){
		task.cancel(true);
		setEnabled(false);
		setText("T" + id + " cancelled!");

		Main.area.append(String.format("Thread %d: Cancelled \n", id));
	}

	public void stopTask(){
		if(!task.isCancelled() && !task.isDone()){
			setText("T" + id + " done!");
			task.cancel(true);
			setEnabled(false);
		}
	}


	public int getId(){
		return id;
	}
}

class XFuture extends FutureTask{

	private int id;

	public XFuture(Callable callable, int id) {
		super(callable);

		this.id = id;
	}

	@Override
	protected void done() {
		super.done();

		if(!isCancelled()){
			XButton b = Main.taskButtons.stream().filter(e -> e.getId() == id).findFirst().orElse(null);

			if (b != null) {
				b.doneTask();
			}
		}

	}
}

class XCall implements Callable<Integer> {

	private int id;

	private Integer sum;
	private Integer limit;

	Boolean isCancelled = false;

	public XCall(int id) {
		this.id = id;
		this.sum = 0;
		this.limit = id * 1000;
	}

	@Override
	public synchronized Integer call() throws Exception{

		while (sum < limit) {

			while (isCancelled) {
				wait();
			}

			int num = (int) (Math.random() * 100);

			sum += num;

			Main.area.append(String.format("Thread %d (limit = %d): %d, sum = %d \n", id, limit, num, sum));

			Thread.sleep(1000);
		}


		return sum;

	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public synchronized void resume() {
		this.isCancelled = false;
		notify();
	}

	public void suspend() {
		this.isCancelled = true;
	}
}
