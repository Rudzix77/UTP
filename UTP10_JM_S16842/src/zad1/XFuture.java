package zad1;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class XFuture extends FutureTask {

	private int id;

	public XFuture(Callable callable, int id) {
		super(callable);

		this.id = id;
	}

	@Override
	protected void done() {
		super.done();

		if(!isCancelled()){
			try{
				Main.getFrame().find(e -> e.getId() == id).doneTask();
			}catch (Exception e){
				System.err.println("Task not found");
			}
		}

	}
}
