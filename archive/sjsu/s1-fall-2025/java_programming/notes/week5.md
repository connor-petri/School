# Week 5
---

## Output and Input Streams
### OutputStream
```OutputStream``` is a class that typically takes bytes as an input and streams them for output. ```System.out``` is a predefined ```OutputStream``` object that is connected to the standard character output buffer, a.k.a. ```std::cout``` in C++.

### InputStream
```InputStream``` is used to receive input from input devices, such as a mouse, keyboard, or other devices. ```System.in``` is a predefined ```InputStream``` object, specifically a ```ByteStream```, that reads from the systems standard input device, usually a keyboard.

Note that when using an ```InputStream```, you must tell the function it is in that it can throw an ```IOException```.
```java
import java.util.Scanner;
import java.io.IOException;

public class Reader {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        System.out(s.nextLine());
    }
}
```

## Recursion
Recursion is when a function calls itself, which causes a loop. Without a ```return``` condition, the function will continue to call itself until a Stack Overflow occurs.

```java
import java.util.Scanner;
import java.io.IOException;

public class Fib {
    public int fib(n) {
        if (n <= 1) { return n; }

        return fib(n-1) + fib(n-2);
    }

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        System.out.println(fib(n));
    }
}
```

## Exceptions
Exceptions provide a mechanism for cleanly reporting and handling error without using specific return values. Exceptions allow us to clearly separate error paths from the main code branch. There are 4 kinds of problems in Java:
- Nobody's fault (solar flare knocking out comms).
- Your fault (this idiot divided by 0).
- Java's fault (for just existing tbh).
- Problems revealed by assertions during testing.

### Exception Handling
We use ```try``` and ```catch``` blocks to handle exceptions.
```java
File dirf = new File(path);
File logf = new File(dirf, "log.txt");

try {
    logf.createNewFile();
    System.out.println("Ok");
} catch (IOExceptions e) {
    System.out.println("YOU FUCKED UP DIPSHIT: " + e.getMessage());
}
System.out.println("Done");
```

