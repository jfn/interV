package no.interview.misc;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 * In the question it was stated to build Queue based on "stack". I assume it
 * was meant java.util.Stack, but I've used Deque interface(based on ArrayDeque)
 * as a Stack(because stack is inconsistent). I only use Deque methods end with
 * "Last"(getLast,removeLast) which give stack semantics. Using ArrayDeque as a
 * implementation as it's more cache aligned than LinkedList.
 * 
 * Also, as it wasn't stated if the implementation should be able to insert NULL
 * values, and taking into account that it's recommended to implement Deque by
 * prohibiting NULL values, it was decided that this implementation doesn't
 * allow NULL values as well.
 * 
 * @param <T>
 * 
 */
public class MyQueue<T> implements Queue<T> {

	private final Deque<T> head = new ArrayDeque<>();
	private final Deque<T> tail = new ArrayDeque<>();

	/*
	 * Time complexity: O(1) - it's constant due to ArrayDeque implementation
	 * 
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		return head.size() + tail.size();
	}

	/*
	 * Time complexity: O(1)
	 * 
	 */
	@Override
	public boolean isEmpty() {
		return size() > 0;
	}

	/*
	 * Time complexity: T(N) = T(Queue#add) = amortized O(1)
	 * 
	 */
	@Override
	public boolean add(T e) {
		tail.addLast(e);
		return true;
	}

	/*
	 * Time complexitiy is the same for #add method
	 */
	@Override
	public boolean offer(T e) {
		return add(e);
	}

	/*
	 * Time complexity: amortized constant time O(1)
	 */
	@Override
	public T poll() {
		if (!head.isEmpty()) {
			return head.pollLast();
		}

		if (tail.isEmpty()) {
			return null;
		}

		moveFromTailToHead();
		return head.pollLast();
	}

	/*
	 * Time complexity: amortized constant time O(1) can be shown with
	 * Credit-Debit method for calculating amortized costs.
	 */
	@Override
	public T element() {
		if (!head.isEmpty()) {
			return head.getLast();
		}

		if (tail.isEmpty()) {
			throw new NoSuchElementException();
		}

		moveFromTailToHead();
		return head.getLast();
	}

	/*
	 * Time complexity: amortized constant time O(1)
	 */
	@Override
	public T peek() {
		if (!head.isEmpty()) {
			return head.peekLast();
		}

		if (tail.isEmpty()) {
			return null;
		}

		moveFromTailToHead();
		return head.peekLast();
	}

	/*
	 * Time complexity: amortized constant time O(1)
	 */
	@Override
	public T remove() {
		if (!head.isEmpty()) {
			return head.removeLast();
		}

		if (tail.isEmpty()) {
			throw new NoSuchElementException();
		}

		moveFromTailToHead();
		return head.removeLast();
	}

	/**
	 * Time complexity: T(N) = O(N), where n is the number of elements in the
	 * queue
	 * 
	 * Memory complexity is the same for {@link #iterator()}, so it's O(N)
	 * N = |head| + |tail|
	 *
	 * It iterates over the elements once, and {@link T#equals(Object)} is
	 * constant relative to N.
	 * 
	 * @see time complexity of answers.MyQueue#iterator()
	 * 
	 */
	@Override
	public boolean contains(Object o) {
		if (o == null) {
			return false;
		}

		for (T e : this) {
			if (e.equals(o)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @formatter: off
	 * 
	 * Time complexity: O(N) - to populate temporary array elements
	 * Memory consumption: Additional O(N) elements to store element in
	 * temporary array
	 * 
	 * @formatter:on
	 */
	@Override
	public Iterator<T> iterator() {

		return new Iterator<T>() {

			int iteratorIndex = 0;
			final T[] iteratorElements = (T[]) toArray();

			@Override
			public T next() {
				if (iteratorElements.length != size()) {
					throw new ConcurrentModificationException("...");
				}

				return iteratorElements[iteratorIndex++];
			}

			@Override
			public boolean hasNext() {
				return iteratorIndex < iteratorElements.length;
			}
		};
	}

	@Override
	public void clear() {
		head.clear();
		tail.clear();
	}

	/**
	 * @formatter:off
	 * 
	 * Time complexity: 
	 * T(N) = T(moveFromHeadToTail) + T(ArrayDequeue.toArray) = O(N) + O(N) = O(N)
	 * 
	 * @formatter:on
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[head.size() + tail.size()];
		copyToArray(result);

		return result;
	}

	/**
	 * 
	 * @see answer.MyQueue#toArray()
	 *
	 */
	@Override
	public <T> T[] toArray(T[] result) {
		if (result == null) {
			throw new NullPointerException("Copy array can't be null");
		}

		final int resultLength = head.size() + tail.size();

		if (result.length < resultLength) {
			result = (T[]) java.lang.reflect.Array.newInstance(result.getClass().getComponentType(), resultLength);
		}

		copyToArray(result);
		return result;
	}

	private void copyToArray(Object[] result) {
		int index = 0;

		while (!head.isEmpty()) {
			result[index++] = head.removeLast();
		}

		int rIndex = result.length - 1;
		while (!tail.isEmpty()) {
			result[rIndex--] = tail.removeLast();
		}

		for (T e : (T[]) result) {
			tail.offer(e);
		}
	}

	/*
	 * T(N) = O(N)
	 * 
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		final Set<T> elements = new HashSet<>();

		for (T e : this) {
			elements.add(e);
		}

		for (Object o : c) {
			if (o == null || !elements.contains(o)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return tail.addAll(c);
	}

	private void moveFromTailToHead() {
		while (!tail.isEmpty()) {
			head.addLast(tail.removeLast());
		}
	}

	@Override
	public boolean remove(Object toRemove) {
		throw new UnsupportedOperationException("Removing is not supported for MyQueue");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Is not required");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Is not required");
	}

}
