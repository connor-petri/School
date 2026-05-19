import java.util.*;

public class MergeSort {

		private static int[] mergeSort(int[] nums) {
				int n = nums.length;
				if (n <= 1) {
						return nums;
				}

				int[] left = mergeSort(Arrays.copyOfRange(nums, 0, n / 2));
				int[] right = mergeSort(Arrays.copyOfRange(nums, n / 2, n));
				
				int[] result = new int[left.length + right.length];
				int l = 0;
				int r = 0;
				int i = 0;
				// merge
				for (; l < left.length && r < right.length; i++) {
						if (left[l] <= right[r]) {
								result[i] = left[l++];
						} else {
								result[i] = right[r++];
						}
				}

				for (; l < left.length; i++) {
						result[i] = left[l++];
				}

				for (; r < right.length; i++) {
						result[i] = right[r++];
				}

				return result;
		}

		public static void main(String[] args) {
				int[] nums = { 5, -2, 4, -7, 10, 11, 15, -23, -1, 0, 4, 8, -8, 20 };
				System.out.println(Arrays.toString(mergeSort(nums)));
		}
}
