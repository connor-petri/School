# Week 5

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