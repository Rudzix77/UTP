package zad1b;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Buffer {


	public static void main(String[] args){

		Buffer b = new Buffer(3);

		ExecutorService ex = Executors.newFixedThreadPool(2);

		ex.submit(new Producer(b));
		ex.submit(new Consumer(b));
	}
	
	public Buffer(int capacity){
		this.queue = new ArrayBlockingQueue(capacity);
	}

	private BlockingQueue<Integer> queue;

	public void put(int num) {

		if(!queue.add(num)) {
			System.out.println(Thread.currentThread().getName() + " : Buffer is full");
		}else{
			System.out.printf("%s added %d%n", Thread.currentThread().getName(), num);
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
