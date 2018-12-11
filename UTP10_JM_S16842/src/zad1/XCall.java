package zad1;

import java.util.concurrent.Callable;

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

			Main.getFrame().log(String.format("Thread %d (limit = %d): %d, sum = %d", id, limit, num, sum));

			Thread.sleep((int) Math.random() * 2000);
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
