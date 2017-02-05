package no.interview.misc;

public class OddEvenSorter {
	public void sort(int[] numbers) {
		if (numbers == null) {
			throw new IllegalArgumentException("Non-null array expected");
		}

		if (numbers.length <= 1) {
			return;
		}

		int even = 0;
		int odd = numbers.length - 1;

		/*
		 * @formatter:off
		 * 
		 * Invariant:
		 * 	numbers[0..even-1] - are even numbers
		 * 	numbers[odd+1..] - are odd numbers
		 * 
		 * @formatter:on	
		 * 
		 */
		while (even < odd) {
			while (even < numbers.length && numbers[even] % 2 == 0) {
				++even;
			}
			while (odd >= 0 && numbers[odd] % 2 != 0) {
				--odd;
			}

			if (even < odd) {
				int t = numbers[even];
				numbers[even] = numbers[odd];
				numbers[odd] = t;

				++even;
				--odd;
			}
		}
	}
}
