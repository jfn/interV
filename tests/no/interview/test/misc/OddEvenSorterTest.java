package no.interview.test.misc;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import no.interview.misc.OddEvenSorter;

public class OddEvenSorterTest {
	private final OddEvenSorter oddEvenSorter = new OddEvenSorter();

	@Test
	public void testEmpty() {
		oddEvenSorter.sort(new int[0]);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull() {
		oddEvenSorter.sort(null);
	}

	@Test
	public void testSingleton() {
		final int[] numbers = { 1 };
		oddEvenSorter.sort(numbers);

		if (!isEvenlySorted(numbers)) {
			fail("Wrong sort for singleton");
		}
	}

	@Test
	public void testRandom_100() {
		final Random random = new Random();
		final int[] numbers = new int[100];

		for (int index = 0; index < numbers.length; index++) {
			numbers[index] = random.nextInt(100);
		}
		oddEvenSorter.sort(numbers);

		if (!isEvenlySorted(numbers)) {
			fail("Wrong sort for random 100 elements: " + Arrays.toString(numbers));
		}
	}

	@Test
	public void testNegative() {
		final int[] numbers = new int[10];

		for (int index = 0; index < numbers.length; index++) {
			numbers[index] = -1 - index;
		}
		oddEvenSorter.sort(numbers);

		if (!isEvenlySorted(numbers)) {
			fail("Wrong sort for negative: " + Arrays.toString(numbers));
		}
	}

	@Test
	public void testMixed() {
		final int[] numbers = new int[] { -100, 20, 21, 1024, 31, -127 };

		for (int index = 0; index < numbers.length; index++) {
			numbers[index] = index;
		}
		oddEvenSorter.sort(numbers);

		if (!isEvenlySorted(numbers)) {
			fail("Wrong sort for negative: " + Arrays.toString(numbers));
		}
	}

	private static boolean isEvenlySorted(int[] numbers) {
		/* 
		 * @formatter:off
		 * 
		 * State 0 - not initialized yet,
		 * State 1 - even-numbers so far
		 * State 2 - odd numbers after even numbers
		 * 
		 * @formatter:on
		 */
		byte state = 0;

		for (int i : numbers) {
			if (i % 2 == 0) {
				if (state == 0 || state == 1) {
					state = 1;
				} else {
					return false;
				}
			} else {
				state = 2;
			}
		}

		return true;
	}
}
