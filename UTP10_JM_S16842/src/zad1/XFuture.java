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
			XButton b = Main.taskButtons.stream().filter(e -> e.getId() == id).findFirst().orElse(null);

			if (b != null) {
				b.doneTask();
			}
		}

	}
}
