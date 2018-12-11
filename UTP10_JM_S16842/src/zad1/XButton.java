package zad1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

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

		Executors.newScheduledThreadPool(1).schedule(() -> Main.getFrame().removeXButton(this), 2, TimeUnit.SECONDS);

		Main.getFrame().log(String.format("Thread %d: Done", id));
	}

	public void cancelTask(){
		task.cancel(true);
		setEnabled(false);
		setText("T" + id + " cancelled!");

		Main.getFrame().log(String.format("Thread %d: Cancelled", id));
	}

	public void stopTask(){
		if(task != null){
			if(!task.isCancelled() && !task.isDone()){
				setText("T" + id + " done!");
				task.cancel(true);
				setEnabled(false);
			}
		}
	}


	public int getId(){
		return id;
	}
}
