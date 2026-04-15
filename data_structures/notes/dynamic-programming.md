# Dynamic Programming

---

Dynamic programming is a method for solving complex problems by breaking them down into simpler subproblems and storing the results of these subproblems to avoid redundant computations.

There are two main approaches to dynamic programming:

1. **Top-down (Memoization)**: Solve the problem recursively and store the results of subproblems in a table (usually a hash map or array) so that the same subproblem is not recomputed.
2. **Bottom-up (Tabulation)**: Solve all the subproblems iteratively starting from the smallest subproblems and use the results to build up the solution to the original problem.

## Example 

Consider the classic Fibonacci sequence problem, where we want to compute the nth Fibonacci number.

**Top-down (Memoization) approach:**

```java
import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
    private static Map<Integer, Integer> memo = new HashMap<>();

    public static int fib(int n) {
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        if (n <= 1) {
            return n;
        }
        int result = fib(n - 1) + fib(n - 2);
        memo.put(n, result);
        return result;
    }
}
```

**Bottom-up (Tabulation) approach:**

```java
public class FibonacciTab {
    public static int fib(int n) {
        if (n <= 1) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
```

## Dynamic Programming Template
1. Get recursive solution
2. Parameter analysis
    - How many distinct parameter combinations are there?
    - Are there few enough to store answers for each combination of parameters?
3. Memoize
    - Allocate a table to hold stored answers
    - Before running recursive calls, check the table to see if the answer for the current parameters has already been computed
4. Move to iterative version
    - For a given answer, what answers does it depend on?
    - Figure out order for indices to fill in answers after things they depend upon
5. Garnish
    - Can you reuse space? Optimize for space
    - Do you need to store extra information for a constructive answer?

## Rod Cutting Problem
The rod cutting problem is a classic dynamic programming problem where we are given a rod of length `n` and a table of prices that contains prices of all pieces of size smaller than `n`. The goal is to determine the maximum value obtainable by cutting up the rod and selling the pieces.

1. Define the recursive solution
```java
int rodCutting(length, int[] prices) {
    if (length == 0) {
        return 0;
    }
    int maxVal = Integer.MIN_VALUE;
    for (int i = 1; i <= length; i++) {
        maxVal = Math.max(maxVal, prices[i - 1] + rodCutting(length - i, prices));
    }
    return maxVal;
}
```

2. Parameter analysis
- The only changing parameter is `length`, so the number of distinct subproblems is `length + 1` (from 0 to `length`).

3. Memoize
- Allocate a table or array of size `length + 1` to store the maximum value for each rod
```java
int rodCutting(int length, int[] prices, int[] memo) {
    if (memo[length] != Integer.MIN_VALUE) {
        return memo[length];
    }
    if (length == 0) {
        return 0;
    }
    int maxVal = Integer.MIN_VALUE;
    for (int i = 1; i <= length; i++) {
        maxVal = Math.max(maxVal, prices[i - 1] + rodCutting(length - i, prices, memo));
    }
    memo[length] = maxVal;
    return maxVal;
}
```
4. Move to iterative version
- Fill the dp table iteratively from the smallest subproblem (length 0) up to the target length
```java
int rodCuttingTab(int length, int[] prices) {
    int[] dp = new int[length + 1];
    dp[0] = 0;
    for (int l = 1; l <= length; l++) {
        int maxVal = Integer.MIN_VALUE;
        for (int i = 1; i <= l; i++) {
            maxVal = Math.max(maxVal, prices[i - 1] + dp[l - i]);
        }
        dp[l] = maxVal;
    }
    return dp[length];
}
```