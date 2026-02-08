# Week 3 - Graphs, Trees, and Heaps

---

## Graphs
A graph is a collection of nodes (vertices) connected by edges. Graphs can be directed or undirected, weighted or unweighted.

### Types of Graphs
#### Directed Graphs (Digraphs) 
- Edges have a direction.
- A digraph $G$ is defined as $G = (V, E)$ where $V$ is a set of vertices and $E$ is a set of ordered pairs of vertices, with the first element the *from* vertex and the second the *to* vertex.

#### Undirected Graphs
- Edges do not have a direction.
- An undirected graph $G$ is defined as $G = (V, E)$ where $V$ is a set of vertices and $E$ is a set of unordered pairs of vertices.

### Terminology
- Graph $G$ is defined as $G = (V, E)$ where:
  - $V$ is a set of vertices (nodes).
  - $E$ is a set of edges (connections between nodes).
- The edges are represented in the form $(u, v)$ where $u, v \in V \land u \neq v$.
    - An edge $(u, v)$ is said to be in *incident form*.
    - We say vertex $u$ is *incident* to edge $(u, v)$.
    - We say vertex $v$ is *adjacent* to vertex $u$.
- The degree of a vertex is the number of edges connected to it.
- In directed graphs:
  - **In-degree**: Number of incoming edges to a vertex.
  - **Out-degree**: Number of outgoing edges from a vertex.

### Weighted Graphs
- Edges have weights (costs, distances, etc.).
- A weighted graph $G$ is defined as $G = (V, E, w)$ where $w: E \rightarrow \mathbb{R}$ assigns a weight to each edge.

#### Paths
- A ***path*** of ***length*** $k$ from a vertex $u$ to a vertex $u'$ in a graph $G = (V,E)$ is a sequence of vertices $v_0, v_1, v_2, \ldots, v_k$ such that:
    - $u = v_0$ 
    - $u' = v_k$
    - $\forall i \in \{1, 2, \ldots, k\}, (v_{i-1}, v_i) \in E$.
- A **subpath** of path $p = \langle v_0, v_1, \ldots, v_k \rangle$ is a congiguous subsequence of its verticies. That is, for any $i, j$ such that $0 \leq i < j \leq k$, the sequence $\langle v_i, v_{i+1}, \ldots, v_j \rangle$ is a subpath of $p$.
- In a directed graph, a path forms a **cycle** if $V_0 = v_k$ and $k \geq 1$.
    - The cycle is said to be a **simple cycle** if no vertices are repeated except for the first and last vertices.
    - A graph is said to be **acyclic** if it contains no cycles.
- An undirected graph is said to be **connected** if there is a path between every pair of vertices.
- A directed graph is said to be **strongly connected** if there is a path from every vertex to every other vertex.

---

## Trees
A tree is a special type of graph that is connected and acyclic. Trees have a hierarchical structure with a root node and child nodes.

### Types of Trees
#### Free Trees
- A **free tree** is a connected, acyclic, undirected graph.
    - When talking about graphs, the term "tree" typically refers to a free tree.
- Let $G = (V, E)$ be an undirected graph. The following statements are equivalent:
    - $G$ is a free tree.
    - Any two vertices in $G$ are connected by a unique simple path.
    - $G$ is connected, but if any edge is removed from $E$, the resulting graph is disconnected.
    - $G$ is connected, and $|E| = |V| - 1$.
    - $G$ is acyclic, and $|E| = |V| - 1$.
    - $G$ is acyclic, but if any edge is added to $E$, the resulting graph contains a cycle.

#### Rooted Trees
- A **rooted tree** is a free tree in which one of the vertices is distinguished from the others and is called the *root*.
- Vertices in a rooted tree are often called *nodes*.
- The **parent** of a node is the node directly connected to it on the path to the root.
- The **children** of a node are the nodes directly connected to it that are not its parent.
- The **depth** of a node is the length of the path from the root to that node.
- The **height** of a tree is the maximum depth of any node in the tree.
- A **leaf** is a node with no children.
- A **level** tree is a rooted tree where all leaves are at the same depth.
- An **ordered tree** is a rooted tree where the children of each node are ordered.

#### Binary and Positional Trees
##### Binary Tree
- A **binary tree** is a rooted tree where each node has at most two children, often referred to as the *left* and *right* child.
- A binary tree with no nodes is called an **empty tree** or **null tree**.
- A **subtree** of a node is the tree consisting of that node as the root and all its descendants.
- A **full binary tree** is a binary tree where every node has either 0 or 2 children. No nodes will have only one child.

##### Positional Tree
- In a **positional tree**, the children of a node are labeled with distinct positive integers starting from 1.
- A **k-ary tree** is a positional tree where each node has at most $k$ children.
- A **complete k-ary tree** is a k-ary tree where all levels are fully filled except possibly the last level, which is filled from left to right.

---

## Heaps
- Heaps are rooted binary trees that satisfy the following properties:
    - **Heap Property**: For a max-heap, the value of each node is greater than or equal to the values of its children. For a min-heap, the value of each node is less than or equal to the values of its children.
    - **Complete Tree Property**: The tree is a complete binary tree, meaning all levels are fully filled except possibly the last level, which is filled from left to right.
- Every non leaf has exactly 2 children.
- Every level of the heap has twice the number of nodes as the previous level, except possibly the last level.

### The Heap Property
- In a **max-heap**, for every node $n$, the value of $n$ is greater than or equal to the values of its children.
- In a **min-heap**, for every node $n$, the value of $n$ is less than or equal to the values of its children.

### Array Representation of Heaps
- Heaps can be efficiently represented using arrays.
- For a node at index $i$:
    - The left child is at index $2i + 1$.
    - The right child is at index $2i + 2$.
    - The parent is at index $\lfloor (i - 1) / 2 \rfloor$.

### Heap Operations
#### Insertion
- Add the new element at the end of the array (aka after the rightmost leaf).
- Compare the added element with its parent; if it violates the heap property, swap it with the parent.
- Repeat until the heap property is restored.
- The height of the heap for $n$ elements is $\lfloor \log_2 n \rfloor$, so the time complexity for traversing the height is $O(\log n)$.
- Each level new value moves up takes constant time $O(1)$, so the overall time complexity for insertion is $O(\log n)$.

#### Deletion (Removing the Root)
- Replace the root element with the last element in the array (rightmost leaf).
- Remove the last element from the array.
- Compare the new root with its children; if it violates the heap property, swap it with the larger (for max-heap) or smaller (for min-heap) child.
- Repeat until the heap property is restored.
- Displaced leaf moves up or down. Each level takes constant time $O(1)$, and the height is $\lfloor \log_2 n \rfloor$, so the overall time complexity for deletion is $O(\log n)$.

---
## In class notes - Optimizing with Stacks

Given an array of values, the first value is $-\infty$, all other values are not. $A = [-\infty, 9, 3, 7, 16, 2, 5, 1, \cdots]$
For each $A[i], i \geq 0$, find $A[j]$ such that $-j < i, A[j] < A[i]$, and $j$ is maximized.

```java
int[] A = { (int)Float.NEGATIVE_INFINITY, 9, 3, 7 ,16, 2, 5, 1 };
int[] B = new int[8];

for (int i = 1; i < n, i++) {
    B[i] = A[i];
    for (int j = -; j < i; j++) {
        if (A[j] < A[i]) {
            B[i] = A[j];
        }
    }
}

return B;
```

We can optimize this by rewriting the inner loop to start from the last found index. This makes the algorithm go from a constant $O(n^2)$ to $O(n^2)$ in the worst case, but $O(n)$ in the best case.

```java
int[] A = { (int)Float.NEGATIVE_INFINITY, 9, 3, 7 ,16, 2, 5, 1 };
int[] B = new int[8];

for (int i = 1; i < n, i++) {
    B[i] = A[i];
    for (int j = i - 1; j >= 0; j--) {
        if (A[j] < A[i]) {
            B[i] = A[j];
            break;
        }
    }
}
```
The presence of a ```break;``` statement in an if condition means the inner loop is basically a while loop. We can rewrite the code to use a while loop instead. This makes it clearer when the loop will terminate and gets rid of the if condition.

```java
int[] A = { (int)Float.NEGATIVE_INFINITY, 9, 3, 7 ,16, 2, 5, 1 };
int[] B = new int[8];
for (int i = 1; i < n, i++) {
    B[i] = A[i];
    int j = i - 1;
    while (j >= 0 && A[j] >= A[i]) {
        j--;
    }
    B[i] = A[j];
}
```

We can use a stack to store the elements in decreasing order. This way, we can pop elements from the stack until we find an element that is less than the current element. In this way, the stack holds all the potential candidates for the answer, and we can efficiently find the correct one. Once we discard an element, it cannot be a solution for any future elements, so we never need to push it back onto the stack. 

This runs in $O(n)$ time, as both the outer and inner loops run in total $O(n)$ time. Linear + linear = linear.

```java
import java.util.Stack;

int[] A = { (int)Float.NEGATIVE_INFINITY, 9, 3, 7 ,16, 2, 5, 1 };
int[] B = new int[8];
Stack<Integer> stack = new Stack<>();

for (int i = 0; i < 8; i++) {
    while (!stack.isEmpty() && stack.peek() >= A[i]) {
        stack.pop();
    }
    B[i] = stack.isEmpty() ? A[0] : stack.peek();
    stack.push(A[i]);   
}
```

---

## Loop Invariants and Program Proofs




Analyzing and proving algorithms is easy for simple prgrams, but gets more complex as we add complications such as loops, conditionals, and recursion.:
```java
int max(int x, int y) {
    int tmp = x;      // tmp == x
    if (y > tmp) {    // If y > tmp (y > tmp)
        tmp = y       // then tmp == y
    }
    return tmp;       // returns the maximum of x and y
}
```

### Loop Notation
When describing the state of variables in a loop, we can use the notation $x_i$ to represent the value of variable $x$ at the end of the $i$-th iteration of the loop. For example, if we have a loop that iterates from 0 to n-1, we can denote the value of a variable $sum$ at the end of the $i$-th iteration as $sum_i$.

```java
float avg(float[] A) {
    int sum = 0;                   // sum == 0
    for (int i = 0; i < 3; i++) {  // i_2 == 2
        sum += A[i];               // sum_2 == A[0] + A[1] + A[2]
    }
    return sum / 3.0f;
}
```

#### Variable Sized Arrays

We can analyze the following code the same way, except using summation notation: $sum_i = \sum_{j=0}^{i} A[j]$.

```java
float avg(float[] A, int n) {
    int sum = 0;                   // sum == 0
    for (int i = 0; i < n; i++) {  // i_n == n - 1
        sum += A[i];               // sum_i ==\sum_{j=0}^{i} A[j]
    }

    return sum / (float)n; // returns sum_{i=n} / n
}
```

We can prove this by induction on $n$. Assume $sum_i = \sum_{j=0}^i A[j]$. If we can prove that $sum_{i+1} = sum_i + A[i+1]$, then we can conclude that $sum_{i+1} = \sum_{j=0}^{i+1} A[j]$, and that this is a correct algorithm for computing the average of the first $n$ elements of the array. We call these types of proofs **loop invariants**.

### Loop Invariants
A loop invariant is a condition that holds true before and after each iteration of a loop. It is used to reason about the correctness of an algorithm. To prove that an algorithm is correct, we can use the following steps:
1. **Initialization**: Show that the invariant holds before the first iteration of the loop.
2. **Maintenance**: Show that if the invariant holds before an iteration of the loop, it also holds after that iteration.
3. **Termination**: Show that when the loop terminates, the invariant gives us a useful property that helps us conclude the correctness of the algorithm.

#### Proving the Average Algorithm
1. **Initialization**: Before the first iteration, $sum_0 = 0$, which is equal to $\sum_{j=0}^{-1} A[j]$ (an empty sum), so the invariant holds.
2. **Maintenance**: Assume that $sum_i = \sum_{j=0}^i A[j]$ holds for some $i < n$. During the $(i+1)$-th iteration, we update $sum$ to $sum_{i+1} = sum_i + A[i+1]$. By the inductive hypothesis, we have $sum_i = \sum_{j=0}^i A[j]$, so we can substitute this into the equation to get $sum_{i+1} = \sum_{j=0}^i A[j] + A[i+1] = \sum_{j=0}^{i+1} A[j]$. Thus, the invariant holds after the $(i+1)$-th iteration.
3. **Termination**: When the loop terminates, we have $i = n$, so $sum_n = \sum_{j=0}^{n-1} A[j]$. The algorithm then returns $sum_n / n$, which is the average of the first $n$ elements of the array. Therefore, the algorithm is correct.

