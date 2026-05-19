# Week 2 - Lists, Stacks, and Queues

---

## Lists
- Some stuff in some order.
- An **abstract data type**

### List Methods
- Create
- Retrieve
- Insert (by location?)
- Delete (by key or location?)
- Size

### Abstract Data Type (ADT)
- Technical name for "stuff"
- The list structures and methods are *almost* independent of what is stored in the list, making it an ADT

### Iterators
- A "bookmark" for an outsider to walk through your list while not breaking the abstraction barrier

#### Iterator Operations
- Next
- Previous?
- Insert?
- Delete?

## Linked Lists
- A list consisting of *nodes* where each node keeps track of some data and a pointer to the next node in the list
- Allows adding and popping from list without moving list elements
    - Only the pointers are changed, which is a faster operation than moving array elements.
- Most linked lists are **doubly linked**, meaning each node also points to the previous node in the list
- The *head* node and the *tail* node are the only 2 easily accessible. All other retrieval must be done via an iterator.

### Sentinel Node
- A node in a *doubly linked list* that store the head in it's .next parameter and the tail in it's .prev parameter
- Also known as a dummy node or base node

### Misuse of Linked Lists
- Direct array access has bad performance
- Use iterator to traverse linked list in a while loop
- Binary search on a linked list makes it go from $O(log(n))$ to $O(n log(n))$


```java
public class LinkedList<T> {
    
    private static class Node<E> {
        public E value;
        public Node<E> prev;
        public Node<E> next;

        // For empty LL initialization
        public Node() {
            this.next = this;
            this.prev = this;
        }

        // Used for insertion only
        public Node(E value) {
            this.value = value;
        }

        public void insert(E value) {
            Node<E> newNode = new Node<>(value);
            newNode.next = this.next;
            newNode.prev = this;
            this.next.prev = newNode;
            this.next = newNode;
        }

        public void delete() {
            this.next.prev = this.prev;
            this.prev.next = this.next;
        }
    }

    private Node<T> base = new Node<>();
    private int size = 0;

    public int getSize() { return size; }

    public void add(T data) {
        base.prev.insert(data);
        size++;
    }

    public void delete(Node<T> node) {
        if (node != base) {
            node.delete();
            size--;
        }
    }

    public void append(LinkedList<T> list2) {
        // L1 tail's next to L2 head;
        this.base.prev.next = list2.base.next;
        // L2 head's previous to L1 tail
        list2.base.next.prev = this.base.prev;
        
        // L2 tail to L1 base (forming new tail of combined list)
        list2.base.prev.next = this.base;

        // L1 base's prev to L2 tail
        this.base.prev = list2.base.prev;

        // Append size
        this.size += list2.size;
    }
}
```

## Dynamic Arrays (ArrayList)
- Arrays are usually static
- Programmers encounter situations where the number of elements needed is dynamic
- Dynamic arrays abstract the reallocation and rearranging assosiated with array operations
- Optimizes the resizing, storage, and retrieval of array data
- Uses a *growth factor* to decide how big to make the array on each expansion
    - Growth factor 2 is used in the below example
- Array also shrinks when enough elements are remove
    - When to shrink depends on growth factor, for example ```if (numElements < size / 4 + 1)```
    - Patterns such as "shrink by 1/3 when under 1/2 full" usually work well

### Operations
- Insert
- Delete
- Access (by index)
- Iterate (without insertion or deletion)
    - next
    - prev

### Amortized Analysis
- Consider a swquence of operations
- Find an upper bound on the runtime for the swquence
- Provides upper bound on average operation runtime
- Useful for when some operations take longer than others, such as insertion to  the end of a Dynamic Array

#### Accounting Method for Amortized Analysis:
- For each below-capacity insertions, count its cost plus *prepay* to copy it and a friend at a later time, to a new array
- For a full capacity insertion, cost of copying has already been counted

### Misuse of Dynamic Arrays
- Iterating over the list and making constant insertions and deletions is costly
- Use a linked list or just make a new Dynamic Array instead

```java
public class DynamicArray<T> {
    private T[] arr;
    private int size;
    private int numElements;

    public DynamicArray() {
        size = 50;
        arr = new T[size];
        numElements = 0;
    }

    public void insert(T value) {
        if (size == numElements) {
            size *= 2;
            T[] newArr = new T[size];
            for (int i = 0; i < numElements; i++) {
                newArr[i] = arr[i]
            }
            arr = newArr;
        }
        arr[numElements] = value;
        numElements++;
    }
}
```

## Stacks, Queues, and Deques
### Stacks
- LIFO
- Think "stack of plates" where you can only take from and insert on top.
- Dynamic array is a good structure to use
- Operations:
    - Push
    - Pop
    - Size
    - Peek
    - isEmpty

### Queue
- FIFO
- Think of a line of people
- Circular array is good for this
- Operations:
    - enqueue
    - dequeue
    - peek
    - isEmpty
    - size

### Deque
- EIEO
- Think a deck of cards where you can only access the top and bottom of the deck
- Linked list is a good structure for this
- Operations:
    - pushLeft
    - pushRight
    - popLeft
    - popRight
    - peek
    - size
    - isEmpty

---

$\sum_{k=1}^n (2k-1) = 2 \sum_{k=1}^n k - \sum_{k=1}^n 1 = n(n+1) - n = n^2$

$log(\prod_{k=1}^n \frac{4^k}{2}) = \sum_{k=1}^n log(4^k) - \sum_{k=1}^n log(2) = \sum_{k=1}^n 2k - \sum_{k=1}^n 1 = n(n+1) - n = n^2$

### Identities
- $\sum_{k=1}^n k = \frac{n(n+1)}{2}$
- $\prod_{k=1}^n k = n!$

---

Consider the following algorithm:

```java
int maxElement(int[] A, int n) {
    int max = A[0];

    for (int i = 1; i < n; i++) {
        if (A[i] > max) {
            max = A[i];
        }
    }
    return max;
}
```

There are \( n - 1 \) comparisons made in the loop, so the time complexity is \( O(n) \).

The probability that the branch is taken is given by \( \frac{1}{i} \) for the \( i^{th} \) iteration, leading to an expected number of times the branch is taken as:
\[\sum_{i=1}^{n} \frac{1}{i} = H_n \approx \ln(n) + \gamma\]
where \( H_n \) is the \( n^{th} \) harmonic number and \( \gamma \) is the Euler-Mascheroni constant.

---

```java
int secondLargest(int[] A, int n) {
    int max, second;
    if (A[0] >= A[1]) { // 1 comparison
        max = A[0];
        second = A[1];
    } else {
        max = A[1];
        second = A[0];
    }
    for (int i = 2; i < n; i++) { // n-2 comparisons
        if (A[i] > max) {
            second = max;
            max = A[i];
        } else if (A[i] > second) {
            second = A[i];
        }
    }
    return second;
}

int secondLargest
```