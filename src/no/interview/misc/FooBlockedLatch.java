package no.interview.misc;

import java.util.concurrent.CountDownLatch;

public class FooBlockedLatch {
	private final CountDownLatch firstCompleted = new CountDownLatch(1);
	private final CountDownLatch secondCompleted = new CountDownLatch(1);

	void first() {
		// ... some code ...
		firstCompleted.countDown();
	}

	void second() throws InterruptedException {
		firstCompleted.await();
		// ... some code ...
		secondCompleted.countDown();
	}

	void third() throws InterruptedException {
		secondCompleted.await();
		// ... some code ...
	}

	public static void main(String[] args) throws InterruptedException {
		final FooBlockedLatch fooBlocked = new FooBlockedLatch();

		Thread t1 = new Thread(() -> {
			fooBlocked.first();
		});

		Thread t2 = new Thread(() -> {
			try {
				fooBlocked.second();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Thread t3 = new Thread(() -> {
			try {
				fooBlocked.third();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		t3.start();
		t2.start();
		t1.start();

		t1.join();
		t2.join();
		t3.join();
	}
}
