import java.util.*;
import java.lang.Exception;

public class RadixSortMSB {
		private static double nanosToSeconds(long nanos) {
				return nanos * Math.pow(10, -9);
		}

		private static int getDigit(int num, int place) throws IllegalArgumentException {
				if (place <= 0) { throw new IllegalArgumentException("place must be positive"); }
				int div = (int)Math.pow(10, place - 1);
				return (num / div) % 10;
		}

		private static int[] radixSort(int[] nums, int place) throws IllegalArgumentException {
				 int[] digits = new int[10];

				 // Count occurences of each digit in [place] place
				 for (int i = 0; i < nums.length; i++) {
						digits[getDigit(nums[i], place)]++;
				 }

				 // Make digits cumulative
				 int total = digits[0];
				 for (int i = 1; i < digits.length; i++) {
						 total += digits[i];
						 digits[i] = total;
				 }

				 // Sort elements by [place] place
				 int[] sorted = new int[nums.length];
				 for (int i = nums.length - 1; i >= 0; i--) {
						sorted[--digits[getDigit(nums[i], place)]] = nums[i]; 
				 }
				
				 if (place == 1) {
						 return sorted;
				 }
				 return radixSort(sorted, place / 10);
		}

		public static void main(String[] args) throws InterruptedException, IllegalArgumentException {
				long start, end;
				int size = Integer.parseInt(args[0]);
				int[] nums = new int[size];
				Random rand = new Random();

				System.out.println("Building array...");
				
				start = System.nanoTime();
				for (int i = 0; i < size; i++) {
						nums[i] = rand.nextInt(100000000);
				}
				end = System.nanoTime();

				System.out.println("Array built in " + String.valueOf(nanosToSeconds(end - start)) + "s");
				Thread.sleep(1000);

				System.out.println("Running MSB Radix Sort...");

				start = System.nanoTime();
				nums = radixSort(nums, 100000000);
				end = System.nanoTime();

				System.out.println("Array sorted in " + String.valueOf(nanosToSeconds(end - start)) + "s");
				
				if (size <= 100) {
						Thread.sleep(1000);
						System.out.println(Arrays.toString(nums));
				}
		}
}
