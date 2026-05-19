# Week 5

---

## Master Method
The Master Method is a powerful tool for solving recurrence relations of the form:
$$T(n) = aT\left(\frac{n}{b}\right) + f(n)$$
where $a \geq 1$ and $b > 1$ are constants, and $f(n)$ is an asymptotically positive function. The Master Method provides a way to determine the asymptotic behavior of $T(n)$ based on the relationship between $f(n)$ and $n^{\log_b a}$.

The Master Method consists of three cases. For $T(n) = aT(n/b) + f(n)$ and $\exists \epsilon > 0$:
1. $f(n) \in O(n^{\log_b a - \epsilon}) \implies T(n) = \Theta(n^{\log_b a})$
2. $f(n) \in \Theta(n^{\log_b a}) \implies T(n) = \Theta(n^{\log_b a} \log_2 n)$
3. $f(n) \in \Omega(n^{\log_b a + \epsilon}) \implies T(n) = \Theta(f(n))$

### Example
Consider $T(n) = 2T(n/2) + n$. Here, $a = 2$, $b = 2$, and $f(n) = n$. We calculate $\log_b a = \log_2 2 = 1$. Since $f(n) \in \Theta(n^{\log_b a})$, we are in case 2 of the Master Method, and thus $T(n) = \Theta(n \log_2 n)$.

---

## Merge Sort
Merge Sort is a divide-and-conquer algorithm that sorts an array by recursively dividing it into two halves, sorting each half, and then merging the sorted halves back together. 

---

## In class problem
Given a max heap in implicit array form and an x and k value, is the kth largest value in the heap greater than or equal to x?

1. Call extract max $k$ times, check last value againt x.
    - $O(k \log n)$
    - $\Omega(min(k, n))$
2. Count elements in array $\geq x$, return true if $\geq k$.
    - $O(n)$
    - $\Omega(n)$
    - $\Theta(n)$
3. Search through heap using breadth first search, only searching nodes $\geq x$.
    - $O(k \log k)$
    - $\Omega(k)$

```java
int bigEnough(int k, int x, int i) {
    if (i >= heap.length || heap[i] < x) {
        return 0;
    }
    int left = bigEnough(k - 1, x, 2*i + 1);
    int right = bigEnough(k - 1 - left, x, 2*i + 2);
    return 1 + left + right;
}

boolean kthLargest(int k, int x) {
    return bigEnough(k, x, 0) >= k;
}
```

---

## In-class problem
Given a set of numbers, how many pairs of numbers have the smallest number on the right? How can we count inversions in an array?
1. Brute force: Check all pairs of numbers, count how many pairs have the smaller number on the right.
    - $O(n^2)$
2. Insertion sort: Count how many times we have to shift numbers to the right when inserting a number into the sorted portion of the array.
    - $O(n^2)$
    - $\Omega(n + k)$ where $k$ is the number of inversions.
3. Merge sort: Count how many times we have to move numbers from the right half of the array to the left half when merging two halves of the array.
    - $O(n \log n)$
    - $\Omega(n + k)$ where $k$ is the number of inversions.