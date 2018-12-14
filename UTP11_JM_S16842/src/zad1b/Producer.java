package zad1b;

import java.util.Random;

public class Producer extends Thread{

	Buffer buffer;
	final Random random = new Random();

	public Producer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {

		Thread.currentThread().setName("Producer");

		try {
			while(true){
				buffer.put(random.nextInt());
				Thread.sleep(Math.abs(random.nextInt(2)) * 1000);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
