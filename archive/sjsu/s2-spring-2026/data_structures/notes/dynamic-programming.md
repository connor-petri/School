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

## Floyd-Warshall Pairwise Shortest Path Algorithm
- Given: A weighted, directed or undirected graph with $n$ vertices, represented as an adjacency matrix $W$ where $W[i][j]$ is the weight of the edge from vertex $i$ to vertex $j$ (or $Integer.MAX_VALUE$ if there is no edge).
- Goal: $\forall i, j$, find length of the shortest path from $i$ to $j$ + a way to quickly reconstruct any given path.
- Idea: For the $i$ to $j$ path, only allow intermediate vertices numbered 1 to $k$
    - $D^k[i][j]$: shortest $i$ to $j$ path using only vertices from 1 to $k$ as intermediate vertices
    - If we dont use vertex $k$, then $D^k[i][j] = D^{k-1}[i][j]$
    - If we do use vertex $k$, then $D^k[i][j] = D^{k-1}[i][k] + D^{k-1}[k][j]$
- Base case: $D^0[i][j] = W[i][j]$

### Recursive version
```java
void floydWarshall(int i, int j, int k, int[][] W) {
    if (k == 0) {
        if (i == j) {
            return 0;
        }
        return W[i][j];
    }
    return Math.min(floydWarshall(i, j, k - 1, W), floydWarshall(i, k, k - 1, W) + floydWarshall(k, j, k - 1, W));
}
```

### Momoized Version
- Allocate a 2D table $D$ to store the results of subproblems. The first index of $D$ corresponds to the source vertex, the second index corresponds to the destination vertex, and the value stored at $D[i][j]$ corresponds to the shortest path from vertex $i$ to vertex $j$ using only intermediate vertices from 1 to $k$.
```java
void floydWarshall(int[][] W) {
    int n = W.length;
    int[][] D = new int[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            D[i][j] = W[i][j];
        }
        D[i][i] = 0;
    }

    for (int k = 0; k <= n; k++) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (D[i][j] + D[k][j] < D[i][j]) {
                    D[i][j] = D[i][k] + D[k][j];
                }
            }
        }
    }
}
```

### Path Reconstruction
- Maintain a 2D table $\Phi$ where $\Phi[i][j]$ is the vertex that comes after $i$ on the shortest path from $i$ to $j$
- Maintain a 2D table $\Pi$ where $\Pi[i][j]$ is the vertex that comes before $j$ on the shortest path from $i$ to $j$
- When we update $D[i][j]$ using vertex $k$, we also update $\Phi[i][j]$ and $\Pi[i][j]$ to reflect the new path
```java
void floydWarshall(int[][] W) {
    int n = W.length;
    int[][] D = new int[n][n];
    int[][] Phi = new int[n][n];
    int[][] Pi = new int[n][n];

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            D[i][j] = W[i][j];
            if (W[i][j] != Integer.MAX_VALUE && i != j) {
                Phi[i][j] = j;
                Pi[i][j] = i;
            }
        }
        D[i][i] = 0;
    }

    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (D[i][k] + D[k][j] < D[i][j]) {
                    D[i][j] = D[i][k] + D[k][j];
                    Phi[i][j] = k;
                    Pi[i][j] = Pi[k][j];
                }
            }
        }
    }
}
```

---

# P = NP
- P: Class of problems that can be solved in polynomial time
    - If a decision (yes/no) problem of input size $N$ can be solved in $O(N^k)$ time for some constant $k$, then it is in P
- NP: Class of problems for which a solution can be verified in polynomial time
- NP-complete: A problem is NP-complete if it is in NP and every problem in NP can be reduced to it in polynomial time
- co-NP: Class of problems for which a solution can be verified in polynomial time for the "no" instances (the complement of NP)
- NP-hard: A problem is NP-hard if every problem in NP can be reduced to it in polynomial time, but it is not necessarily in NP (it may not even be a decision problem)

## Clique Problem
- A *complete graph* is a graph where every pair of distinct vertices is connected by a unique edge.
- A *clique* is a complete graph which is a subgraph of some other graph.
- The *clique problem* is the problem of determining whether a given graph contains a clique of size $k$.

Given graph $G = (V,E)$ and $C \subseteq V$, is $C$ a clique?
- $O(|C|^2)$ time to check if $C$ is a clique using an adjacency matrix
- $O(|C| \cdot |E|)$ time to check if $C$ is a clique using an adjacency list
- Consider either case $O(|V|^2)$ time to check if $C$ is a clique

### A harder question
Given graph $G = (V,E)$ and integer $k$, does $G$ contain a clique of size $k$?
- We can solve this problem by checking all subsets of $V$ of size $k$
- This takes $O(\binom{|V|}{k} \cdot |V|^2)$ time, which is not polynomial in the size of the input (since $k$ can be as large as $|V|$)
- This means this problem is an NP class problem, since we can verify a solution (a subset of vertices) in polynomial time, but we do not know of a polynomial time algorithm to solve it.

## NP Reductions
### Independent Set Problem
- An *independent set* is a set of vertices in a graph, no two of which are adjacent.
- Given graph $G = (V,E)$ and integer $k$, does $G$ contain an independent set of size $k$?
- It turns out that G has an independent set of size $k$ if and only if the complement of $G$ has a clique of size $k$. Therefore, we can reduce the independent set problem to the clique problem in polynomial time by taking the complement of the graph. This shows that the independent set problem is also NP-complete.
- This is called a polynomial time reduction, and it is a common technique for showing that problems are NP-complete. If we can reduce a known NP-complete problem to another problem in polynomial time, then the second problem is also NP-complete.

### Vertex Cover Problem
- A *vertex cover* is a set of vertices in a graph such that every edge is incident to at least one vertex in the set.
- Given graph $G = (V,E)$ and integer $k$, does $G$ contain a vertex cover of size $k$?
- It turns out that G has a vertex cover of size $k$ if and only if G has an independent set of size $|V| - k$. Therefore, we can reduce the vertex cover problem to the independent set problem in polynomial time because polynomial reductions are transitive. This shows that the vertex cover problem is also NP -Hard and NP-complete.

### Dominating Set Problem
- A *dominating set* is a set of vertices in a graph such that every vertex is either in the set or adjacent to a vertex in the set.
- Given graph $G = (V,E)$ and integer $k$, does $G$ contain a dominating set of size $k$?
- It turns out that G has a dominating set of size $k$ if and only if G has a vertex cover of size $k$. Therefore, we can reduce the dominating set problem to the vertex cover problem in polynomial time because polynomial reductions are transitive. This shows that the dominating set problem is also NP -Hard and NP-complete.
- Note that this does not hold in the other direction unlike the previous two problems, since a vertex cover is not necessarily a dominating set.

## Subset Sum Problem
- Given a set $S[1, \ldots, n]$ of integers and an integer $K$, does there exist a subset of $S$ that sums to $K$?
- We can solve this problem using dynamic programming in $O(nK)$ time, which is polynomial in the size of the input (since $K$ can be at most the sum of all elements in $S$). Therefore, the subset sum problem is in P.
- To do this we allocate an array `ans` of size `K + 1` where `ans[i]` will store the index of the last element in the subset that sums to `i`, or -1 if no such subset exists. We initialize `ans[0]` to 0 (since the empty set sums to 0) and all other entries to -1. We then iterate through each element in `S` and update the `ans` array accordingly. Finally, if `ans[K]` is greater than 0, we can reconstruct the subset by backtracking through the `ans` array.
  
```java
List<Integer> subsetSum(int[] S, int K) {
    int ans[] = new int[K + 1];
    ans[0] = 0;
    for (int i = 1; i <= K; i++) {
        ans[i] = -1;
    }
    for (int i = 1; i<= S.length; i++) {
        for (int j = K; j >= S[i]; j--) {
            if (ans[j] < 0 && ans[j-S[i]] >= 0) {
                ans[j] = i;
            }
        }
    }

    if (ans[K] > 0) {
        List<Integer> result = new ArrayList<>();
        int remainder = K;
        while (remainder > 0) {
            result.add(ans[remainder]);
            remainder -= S[ans[remainder]];
        }
        return result;
    }
    return null;
}
```