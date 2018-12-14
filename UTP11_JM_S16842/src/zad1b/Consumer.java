package zad1b;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Consumer extends Thread{

	Buffer buffer;
	final Random random = new Random();

	public Consumer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {

		Thread.currentThread().setName("Consumer");

		try {
			while(true){
				buffer.get();
				TimeUnit.SECONDS.sleep(Math.abs(random.nextInt(2)));
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
