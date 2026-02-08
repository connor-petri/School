# Week 4

---

## Asymptotic Notation
Asymptotic notation is a way to describe the behavior of functions as their input size grows. It includes Big O notation, big Omega notation, and big Theta notation. 

### Big O Notation
Big O notation is a mathematical notation used to describe the upper bound of a function's growth rate. It is used to analyze the time complexity of algorithms. The formal definition of Big O notation is as follows:
$f(n) = O(g(n)) \iff \forall c > 0, \exists n_0 > 0 \ni \forall n > n_0, 0 \leq f(n) \leq c \cdot g(n)$
In other words, the function $f(n)$ has an upper bound runtime complexity of $O(g(n))$ if there exist positive constants $c$ and $n_0$ such that for all $n > n_0$, the value of $f(n)$ is at most $c$ times the value of $g(n)$. To solve this, we can choose arbitrary values for $c$ and $n_0$ and check if the inequality holds for all $n > n_0$. If we can find such constants, then we can conclude that $f(n) = O(g(n))$.

### Big Omega Notation
Big Omega notation is a mathematical notation used to describe the lower bound of a function's growth rate. It is used to analyze the best-case time complexity of algorithms. The formal definition of Big Omega notation is as follows:
$f(n) = \Omega(g(n)) \iff \forall c > 0, \exists n_0 > 0 \ni \forall n > n_0, 0 \leq c \cdot g(n) \leq f(n)$

### Big Theta Notation
Big Theta notation is a mathematical notation used to describe the tight bound of a function's growth rate. It is used to analyze the average-case time complexity of algorithms. The formal definition of Big Theta notation is as follows:
$f(n) = \Theta(g(n)) \iff f(n) = O(g(n)) \land f(n) = \Omega(g(n))$

Consider the following code:
```java
int replaceMax(int[] A, int n, int val) {
    int index = findMaxIndex(A, n);
    int temp = A[index];
    A[index] = val;
    return temp;
}

int findMaxIndex(int[] A, int n) {
    int tmp = 1;
    for (int i = 1, i < n; i++) {
        if (A[i] > A[tmp]) {
            tmp = i;
        }
    }
    return tmp;
}
```

We know that ```replaceMax``` runs in constant time plus the time it takes to run ```findMaxIndex```. To analyze the time complexity of ```findMaxIndex```, we can use the definition of big O notation. We want to show that there exist constants $c$ and $n_0$ such that for all $n > n_0$, the time complexity of ```findMaxIndex``` is at most $c \cdot n$. Let $T(n)$ be the time complexity of ```findMaxIndex```. We can express $T(n)$ as follows:
$$
c = 7, \quad T(n) \leq c + \sum_{i=1}^{n-1} 9 \leq 9n + 7 \leq 10n(n \geq 7 = n_0)
$$

Thus, we can conclude that $T(n) = O(n)$, and therefore the time complexity of ```replaceMax``` is also $O(n)$.

Consider the following code:
```java

void bubbleSort(int[] A, int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (A[i] > A[j]) {
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
    }
}
```

We model the time complexity as follows:
$$
T(n) \leq \sum_{i=0}^{n-1} \sum_{j=i+1}^{n} c = \sum_{i=0}^{n-1} c(n - i) = cn + c(n - 1) + c(n - 2) + \cdots + c = \frac{cn(n + 1)}{2} \implies O(\frac{n^2}{2}) \implies T(n) = O(n^2)
$$

We can find the lower bound as follows:
$$
T(n) \geq \sum_{i=1}^{n} \sum_{j=i+1}^n c = \sum_{i=1}^{n}c(n - (i + 1) + 1) = \sum_{i=1}^{n} c(n - i) = c(\sum_{i=1}^{n} n - \sum_{i=1}^{n} i) = n^2 - \frac{n(n - 1)}{2} = \frac{n^2 - n}{2} \implies T(n) = \Omega(n^2)
$$

This means that the time complexity of bubble sort is $\Theta(n^2)$, as it is both $O(n^2)$ and $\Omega(n^2)$.


### Usage of Asymptotic Notation
For an algorithm, we can analyze:
1. Best case: Not generally helpful. Search is $\Theta(1)$ best case.
2. Average case: Over what distrubution? Randomized?
3. Worst case: Generally the most useful. Search is $\Theta(n)$ worst case.

Which analysis we use depends on the context.

#### Comparison based sorting algorithms
1. InsertionSort: Worst case $O(n^2)$
2. QuickSort: Average case $O(n \log n)$, worst case $O(n^2)$
3. MergeSort: Worst case $O(n \log n)$

##### In general
1. takes $\Omega(n \log n)$ comparisons in the worst case
2. is accomplished in $O(n \log n)$ comparisons by MergeSort
3. is a $\Theta(n \log n)$ comparisons problem

---

## Iterated Functions and log*

The iterated logarithm function, denoted as $\log^* n$, is defined as the number of times the logarithm function must be applied before the result is less than or equal to 1. Formally, it can be defined as follows:
$$
\log^* n = \begin{cases}
0 & \text{if } n \leq 1 \\
1 + \log^*(\log n) & \text{if } n > 1
\end{cases}
$$