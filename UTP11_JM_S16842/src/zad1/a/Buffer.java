package zad1.a;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {


	public static void main(String[] args){

		Buffer b = new Buffer(3);

		ExecutorService ex = Executors.newCachedThreadPool();

		ex.submit(new Producer(b));
		ex.submit(new Consumer(b));

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
		this.capacity = capacity;
	}

	private int capacity;
	private Queue<Integer> queue = new LinkedList();

	private Lock lock = new ReentrantLock();
	private Condition bufferNotFull = lock.newCondition();
	private Condition bufferNotEmpty = lock.newCondition();

	public void put(int num) throws InterruptedException {
		lock.lock();
		try {

			while (queue.size() == capacity) {
				System.out.println(Thread.currentThread().getName() + " : Buffer is full");
				bufferNotEmpty.await();
			}

			queue.add(num);

			System.out.printf("%s added %d%n", Thread.currentThread().getName(), num);
			bufferNotFull.signalAll();

		} finally {
			lock.unlock();
		}
	}

	public void get() throws InterruptedException {
		lock.lock();

		try {
			while (queue.size() == 0) {
				System.out.println(Thread.currentThread().getName() + ": Buffer is empty");
				bufferNotFull.await();
			}

			Integer e = queue.poll();

			if (e != null) {
				System.out.printf("%s consumed %d%n", Thread.currentThread().getName(), e);
				bufferNotEmpty.signalAll();
			}

		} finally {
			lock.unlock();
		}
	}
}
