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