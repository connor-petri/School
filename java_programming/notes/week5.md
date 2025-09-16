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