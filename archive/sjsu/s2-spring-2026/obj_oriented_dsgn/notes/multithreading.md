# Concurrent Programming

---

## Thread Basics
- A *process* is an idependent executing unit that contains its own state information and uses its own address space.
- A single process might contain multiple *threads*; all threads withing a process share the same address space and state information.

### Threads and Runnable Interface
- In Java, a thread can be created by implementing the `Runnable` interface and overriding the `run()` method.
- The `run()` method contains the code that will be executed when the thread is started.
```java
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        // Code to be executed in the thread
    }
}

public class Main {
    public static void main(String[] args) {
        int processorCount = Runtime.getRuntime().availableProcessors();
        ExecutorService services = Executors.newFixedThreadPool(processorCount);
        services.execute(new MyRunnable()); // Use an existing thread from the pool to execute the task if available, otherwise create a new thread
        services.shutdown(); // Shutdown the thread pool after use
    }
}
``` 

### Thread Synchronization
- When multiple threads access shared resources, it can lead to *race conditions*, where the outcome depends on the timing of the threads' execution.
- To prevent race conditions, we can use synchronization mechanisms such as `synchronized` blocks or methods, which ensure that only one thread can access the critical section of code at a time.

### Locks
- A *lock* is a synchronization mechanism that allows threads to have exclusive access to a resource. In Java, the `ReentrantLock` class provides a more flexible locking mechanism than the `synchronized` keyword.
```java
private Lock queueLock = new ReentrantLock();
public void add (E newVal) {
    queueLock.lock(); // Acquire the lock before accessing the shared resource
    try {
        // Code to add newVal to the queue
    } finally { // Holy shit an actual use of finally???? crazy
        queueLock.unlock(); // Ensure that the lock is released even if an exception occurs
    }
}
```

#### Primitive lock
- A *primitive lock* is a basic synchronization mechanism that can be used to control access to a shared resource. In Java, the `synchronized` keyword can be used to create a primitive lock.  

## Bounded Queues and Circular Buffers
- A *bounded queue* is a data structure that has a fixed capacity and can hold a limited number of elements. It is often used in producer-consumer scenarios to manage the flow of data between threads.
- A *circular buffer* is a type of bounded queue that uses a fixed-size array to store elements. When the buffer is full, new elements overwrite the oldest ones, creating a circular structure.

```java
public class CircularBuffer {
    private final int[] buffer;
    private int head;
    private int tail;
    private int count;

    public CircularBuffer(int capacity) {
        buffer = new int[capacity];
        head = 0;
        tail = 0;
        count = 0;
    }

    public synchronized void put(int value) throws InterruptedException {
        while (count == buffer.length) {
            wait(); // Wait until there is space in the buffer
        }
        buffer[tail] = value;
        tail = (tail + 1) % buffer.length; // Move tail pointer
        count++;
        notifyAll(); // Notify waiting threads that an item has been added
    }

    public synchronized int take() throws InterruptedException {
        while (count == 0) {
            wait(); // Wait until there is an item to take
        }
        int value = buffer[head];
        head = (head + 1) % buffer.length; // Move head pointer
        count--;
        notifyAll(); // Notify waiting threads that an item has been removed
        return value;
    }
}
```

### Conditions
- In Java, the `Condition` interface provides a more flexible way to manage thread synchronization compared to using `wait()` and `notify()`. It allows threads to wait for specific conditions to be met before proceeding, and it can be used in conjunction with locks to provide more fine-grained control over thread synchronization.

### Thread Safe Queues
- Java provides thread-safe queue implementations such as `ConcurrentLinkedQueue` and `LinkedBlockingQueue` that can be used in concurrent programming scenarios without the need for explicit synchronization.
```java
import java.util.concurrent.ConcurrentLinkedQueue;
public class ThreadSafeQueue {
    private ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

    public void add(int value) {
        queue.add(value); // Thread-safe addition to the queue
    }

    public Integer take() {
        return queue.poll(); // Thread-safe retrieval from the queue
    }
}
```