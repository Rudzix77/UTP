package zad1.a;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
				TimeUnit.SECONDS.sleep(Math.abs(random.nextInt(2)));
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
