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
