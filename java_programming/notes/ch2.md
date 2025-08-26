# Chapter 2: Variable Assignments and Opterations

---

```java
int i = 0;
float f = 4.2;
char c = 'a';
String s = "Hello World"
```

## Scan for Text Input
```java
import java.util.Scanner;

public class Hello {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("What is your name? ");
        String name = scnr.next();

        System.out.println("Type a short description of yourself: ");
        String desc = scnr.nextLine();
    }
}
```