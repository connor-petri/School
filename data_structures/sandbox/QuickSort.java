import java.util.*;

public class QuickSort {
		private static ArrayList<Integer> quickSort(ArrayList<Integer> nums) {
				int n = nums.size();
				if (n <= 1) { return nums; }

				int start = nums.get(0);
				int mid = nums.get(n / 2);
				int end = nums.get(n - 1);
				int pivot;

				if ((mid <= start && start <= end) || (end <= start && start <= mid)) {
						pivot = start;
				} else if ((start <= mid && mid <= end) || (end <= mid && mid <= start)) {
						pivot = mid;
				} else {
						pivot = end;
				}

				ArrayList<Integer> left = new ArrayList<>();
				ArrayList<Integer> right = new ArrayList<>();

				for (int num : nums) {
						if (num == pivot) { continue; }
						if (num < pivot) {
								left.add(num);
						} else {
								right.add(num);
						}
				}

				ArrayList<Integer> sorted = new ArrayList<Integer>();
				sorted.addAll(quickSort(left));
				sorted.add(pivot);
				sorted.addAll(quickSort(right));

				return sorted;
		}

		public static void main(String[] args) {
				int[] arr = { 3, -2, 5, 15, 2, 1000, -9999, 9999, 0, 22, -77, -1, -14 };
				ArrayList<Integer> nums = new ArrayList<Integer>();
				for (int i = 0; i < arr.length; i++) {
						nums.add(arr[i]);
				}
				System.out.println(quickSort(nums).toString());
		}
}
