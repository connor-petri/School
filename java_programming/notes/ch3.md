# Arrays and Classes

## Arrays
Java arrays work like C++ arrays, but they work in range-based for loops.
```java
    int[] myArray = { 1, 2, 3, 4 };
    int[][] myArray2 = new int[10][4];

    System.out.println(myArray[2]); // 3

    // Can be iterated over in a range-based for loop
    for (int num : myArray) {
        System.out.println(num);
    }
```

### Strings
Here are some important string methods:

```java
String s = "Hello";
s[4]; // o
s.charAt(4); // o
s.length();
s.substring(1, 3) // ell
s.concatonate(s, ", World!"); // Hello, World
s.toUpperCase();
s.toLowerCase();
s.trip(); // Removes all spaces
s.toString(); // Most classes have a string representation. This is how it is accessed.
s.equals("Hello"); // Compares strings returns bool
s.equalsIgnoreCase("HeLlO"); // true
s.compareTo("aello"); // returns 1 for 1 character difference.
s.compareToIgnoreCase(...);
s.indexOf("o"); // Gets index of specific character or substring
s.lastIndexOf('l') // Gets last instance of a character
s.replace('E', 1); // Puts character at index
s.hashCode(); // Hashes the string
```
Note that if 2 identical strings are defined, both variables will point to the same memory location. This can be avoided by using the StringBuffer class:
```java
StringBuffer sb = new StringBuffer("Hello World");
String uniqueMemString = new String(sb);
```

## Classes
Member variables and methods can be public, private, or protected. Static methods should not access not static memeber variables or non static methods.
```java
public class Animal {
    private String name;

    // Default Constructor
    public Animal(String name = "Buddy") {
        this.name = name;
    }

    // Copy Constructor
    public Animal(Animal a) {
        this.name = a.getName();
    }

    // Accessor
    public String getName() {
        return this.name;
    }

    // Mutator
    public void setName(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println(this.name + " speaks.");
    }
}
```