package zad1b;

import java.util.concurrent.*;

public class Buffer {


	public static void main(String[] args){

		Buffer b = new Buffer(3);

		ExecutorService ex = Executors.newFixedThreadPool(2);

		ex.execute(new Producer(b));
		ex.execute(new Consumer(b));

		ex.execute(() -> {
			try {
				TimeUnit.SECONDS.sleep(15);
				System.exit(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	public Buffer(int capacity){
		this.queue = new ArrayBlockingQueue(capacity);
	}

	private BlockingQueue<Integer> queue;

	public void put(int num) {

		try{
			queue.add(num);
			
			System.out.printf("%s added %d%n", Thread.currentThread().getName(), num);
		}catch (IllegalStateException e){
			System.out.println(Thread.currentThread().getName() + " : Buffer is full");
		}

	}

	public void get() {

		if (queue.size() == 0) {
			System.out.println(Thread.currentThread().getName() + ": Buffer is empty");
		}

		Integer e = queue.poll();

		if (e != null) {
			System.out.printf("%s consumed %d%n", Thread.currentThread().getName(), e);
		}
	}
}
