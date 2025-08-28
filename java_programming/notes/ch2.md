# Chapter 2: Variable Assignments and Opterations

---

### Variable Types and How to Assign Them
```java
int i = 0;
float f = 4.2;
char c = 'a';
String s = "Hello World";
byte b = 0xD;
```

### Scan for Text Input
```java
// Equivilant of #include <iostream>
import java.util.Scanner;

public class Hello {
    public static void main(String[] args) {
        // Instantiate Scanner with cin buffer
        Scanner scnr = new Scanner(System.in);

        // Read a single word from cin
        System.out.println("What is your name? ");
        String name = scnr.next();

        // Read a whole line from cin

        System.out.println("Type a short description of yourself: ");
        String desc = scnr.nextLine();
    }
}
```

### Random Number Generation
```java
import java.util.Random;

public class Random {
    public static void main(String[] args) {
        Random rand = new Random(); // A seed can be passed as an arg.

        int x = rand.nextInt(5); // Range 0-4
    }
}
```

### Constants
Instead of the ```const``` keyword, Java used the keyword ```final```

### Control Flow
Mostly the same as C++, however string comparisons work a little differently. Switch statements are also the same.
```java
String s1 = "Hello";
// This compares only if the values are the same
boolean equal = s1.equals("Hello");
// 
boolean comparison = s1.compareTo("Hello");
```
### Loops
for, while, and do/while loops all work the same.

### String Accessors
```java
String s = "abcdefghijk";
for (int i = 0; i < s.length(); i++) {
    System.out.println(s.charAt(i));
}
```

### Enumerations
```java
// Enum starts at 0 by default. '=' can be used to set specific int.
public enum Color {
    RED = 5, 
    GREEN = 6, 
    BLUE = 7
}

final Color favoriteColor = Color.BLUE;

if (favoriteColor == Color.BLUE) {
    System.out.println("Your favorite color is blue!");
}
```

### Arrays
```java
int[] nums = new int[5]; // nums = [0, 0, 0, 0, 0]
for (int i = 0; i < nums.length(); i++) {
    System.out.println(nums[i]);
}

// Initializer list (new keyword not needed).
int[] nums = { 0, 1, 2, 3, 4 };

// 2d arrays
int[][] spreadsheet = new int[5][5]; // 5x5 array
// OR
int[][] spreadsheet = {
    { 0, 1, 2, 3, 4 },
    { 2, 3, 4, 5, 6 }
}

int row_count = spreadsheet.length;
int col_count = spreadsheet[0].length;
```

### Range-based For Loop
Regular array in java can be used in a range-based for loop.
```java
int[] nums = { 0, 1, 2, 3, 4 };

for (int num : nums) {
    System.out.println(num);
}
```

### Functions
Functions work mostly the same as in C++. However, all function definitions must be inside a class. Making a method ```public static``` allows it to be called without creating an instance of the class. Static functions should never access instance variables or methods.