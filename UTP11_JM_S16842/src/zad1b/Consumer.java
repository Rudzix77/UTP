package zad1b;

import java.util.Random;

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
				Thread.sleep(Math.abs(random.nextInt(2)) * 1000);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
