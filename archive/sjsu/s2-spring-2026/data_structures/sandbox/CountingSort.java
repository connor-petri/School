import java.util.*;

public class CountingSort {
		private static int[] countingSort(int[] nums) {
				int max = nums[0];
				for (int i = 1; i < nums.length; i++) {
						if (nums[i] > max) {
								max = nums[i];
						}
				}

				int[] counts = new int[max + 1];
				for (int i = 0; i < nums.length; i++) {
						counts[nums[i]]++;
				}

				int[] sorted = new int[nums.length];
				int j = 0;
				for (int i = 0; i < counts.length; i++) {
						while (counts[i]-- != 0) {
								sorted[j++] = i;
						}
				}

				return sorted;
		}


		public static void main(String[] args) throws InterruptedException {
				System.out.println("Building array...");
				int[] arr = { 3, 2, 5, 15, 2, 22, 2, 3, 0, 1, 14, 1000, 9999, 9999, 0, 22, 77, 1, 14 };

				int[] nums = new int[Integer.parseInt(args[0])];
				Random r = new Random();

				for (int i = 0; i < nums.length; i++) {
						nums[i] = r.nextInt(1000000);
				}
				
				System.out.println("Sorting...");
				long start = System.nanoTime();
				nums = countingSort(nums);
				long end = System.nanoTime();
				System.out.println("Sorted in " + Double.toString((double)(end - start) * Math.pow(10, -9)) + "s");

				// Thread.sleep(2000);

				// System.out.println(Arrays.toString(nums));
		}
}
