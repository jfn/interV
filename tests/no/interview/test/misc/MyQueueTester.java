package no.interview.test.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Test;

import no.interview.misc.MyQueue;

public class MyQueueTester {

	final Random random = new Random();

	final Integer[] randomNumbers;

	public MyQueueTester() {
		randomNumbers = new Integer[20];
		for (int i = 0; i < randomNumbers.length; i++) {
			randomNumbers[i] = random.nextInt(100);
		}
	}

	@Test
	public void testSimple() {
		final MyQueue<Integer> myQueue = new MyQueue<>();
		final Integer[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		for (int i : numbers) {
			myQueue.offer(i);
		}

		int index = 0;

		Integer element;
		while ((element = myQueue.poll()) != null) {
			assertEquals(numbers[index++], element);
		}
	}

	@Test
	public void testAltenating() {
		final MyQueue<String> myQueue = new MyQueue<>();

		final String[] testStrings = { "first", "second", "third", "fourth" };

		for (String s : testStrings) {
			assertTrue(myQueue.offer(s));
			assertEquals(s, myQueue.peek());
			assertEquals(s, myQueue.poll());
		}

		for (String s : testStrings) {
			assertTrue(myQueue.add(s));
			assertEquals(s, myQueue.element());
			assertEquals(s, myQueue.remove());
		}

		for (String s : testStrings) {
			assertTrue(myQueue.offer(s));
			assertEquals(s, myQueue.peek());
			assertEquals(s, myQueue.remove());
		}

		for (String s : testStrings) {
			assertTrue(myQueue.add(s));
			assertEquals(s, myQueue.element());
			assertEquals(s, myQueue.poll());
		}
	}

	@Test
	public void testEmpty() {
		final MyQueue<String> myQueue = new MyQueue<>();
		assertEquals(null, myQueue.poll());

		assertTrue(myQueue.offer(""));
		assertEquals("", myQueue.poll());
		assertEquals(null, myQueue.poll());

	}

	@Test
	public void testIterator() {
		final Random random = new Random();

		MyQueue<Integer> myQueue = new MyQueue<>();
		for (Integer _ : myQueue) {
			fail("Iterating over empty queue???");
		}

		for (Integer n : randomNumbers) {
			myQueue.offer(n);
		}

		int index = 0;
		for (Integer n : myQueue) {
			assertEquals(randomNumbers[index++], n);
		}
	}

	@Test
	public void testToArray() {
		final Random random = new Random();

		MyQueue<Integer> myQueue = new MyQueue<>();
		for (Integer n : randomNumbers) {
			myQueue.offer(n);
		}

		assertEquals(randomNumbers, myQueue.toArray());
	}

	@Test
	public void testContainsAll() {

		final MyQueue<Integer> myQueue = new MyQueue<>();
		final LinkedList<Integer> list = new LinkedList<>();

		for (Integer n : randomNumbers) {
			list.add(n);
		}
		myQueue.addAll(list);

		assertTrue(myQueue.contains(randomNumbers[0]));
		assertTrue(myQueue.containsAll(list));

		list.set(0, -3);

		assertTrue(myQueue.contains(randomNumbers[0]));
		assertFalse(myQueue.containsAll(list));
	}

	@Test(expected = NoSuchElementException.class)
	public void testRemoveException() {
		final MyQueue<String> myQueue = new MyQueue<>();
		myQueue.remove();
	}

}
