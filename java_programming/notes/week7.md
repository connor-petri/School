# Week 7: Data Structures

---

## Superinterfaces and Subinterfaces
Interfaces can extend other interfaces.
```java
interface Superface {
    boolean xxx();
    int yyy();
}

interface Subface extends Superface {
    double zzz();
}

class Class1 implements Superface {
    boolean xxx() { ... }
    int yyy() { ... }
}

class Class2 implements Subface {
    boolean xxx() { ... }
    int yyy() { ... }
    double zzz() { ... }
}
```

## Frameworks
### A framework is:
- Response to a broad need.
- Interfaces
- Classes that implements the interfaces.

### How to use a framework
- Use the classes if you can.
- Else extend the classes if you can.
- Else implement the interfaces.

### The Collections Framework
- A framework that has classes and interfaces related to aggregation and retrieval.

#### Example of extending and implementing from the Collections Framework:
```java
public class TreeFilmArchive extends TreeSet<Movie> {
    ...
}

public class ProfessorCollection implements Collection {
    // Functions required by Collection interface
    public void add() { ... }
    public void remove() { ... }
}
```

### What do ArrayList, HashSet, and TreeSet have in common?
- In package ```java.util```
- Generic (```HashSet<type>```)
- Methods
    - ```public boolean add()```
    - ```public boolean remove()```
    - ```public boolean contains()```
- Range-based for loop
    - ```for (Movie m : myFilmArchive)```


## What is a Map?
- Each value within a map can be mapped to a key.
- Python dict and hashtables from C++ are examples of maps.

#### Example of TreeMap usage
```java
TreeMap<Star, Astronomer> starToDiscoverer = new TreeMap<Star, Asreonomer>();
// This is also acceptable
TreeMap<Star, Astronomer> starToDiscoverer = new TreeMap<>();

Star s1 = new Star("Star 1 Name");
Star s2 = new Star("Star 2 Name");

starToDiscoverer.put(s1, new Astronomer("Brahe"));
starToDiscoverer.put(s2, new Astronomer("Kepler"));

for (Star s : starToDiscoverer.KeySet()) {
    System.out.println(s + " " + starToDiscoverer.get(s));
}
```
In order for a class to be a key in a ```TreeMap```, it must implement ```Comparable```. This does not apply to ```HashMap```.


## ```java.util.Stack<T>```
- L.I.F.O. - Last in first out.
- ```public T push(T pushMe)```
    - Inserts ```pushme```
    - Returns ```pushme``` (rarely needed).
- ```public T pop()```
    - Returns the most recently pushed object.
    - Removes that object from the stack.
- ```public T peek()```
    - Same as pop without removing it.
