# Chapter 6: Inheritance

---

Inherited classes get all instance variables and methods from their parents. Inherited classes can only directly access public and protected fields.

## ```super()```
The ```super``` keyword refers to the parent class of the current object. For example, we can call the super classes constructor rather than re-implementing the parent's constructor.

```java
public class Parent {
    private String name;
    public Parent(String name) {
        this.name = name;
    }
}

public class Child extends Parent {
    private int id;
    public Child(String name, int id) {
        super(name);
        this.id = id;
    }
}
```

---

## Mouse Events
- ```MouseListener``` listens for mouse button presses
- ```MouseMotionListener``` listens for mouse motion
- Attached using ```addMouseLIstener()``` and ```addMouseMotionListener()``` respectivly
- ```MouseActionListener``` is a class the implements both 

---

## Abstract Class
- A class that cannot be instantiated, but can be subclassed
- Used to define a common interface for a group of subclasses
- Can contain abstract methods (methods without a body) that must be implemented by subclasses
- Can also contain concrete methods (methods with a body) that can be inherited by subclasses
- Declared using the ```abstract``` keyword

## Final Classes
- A class that cannot be subclassed
- Used to prevent inheritance and ensure that the class's behavior cannot be modified by subclasses
- Declared using the ```final``` keyword
- Not very common, but useful for security reasons or to prevent unintended consequences of subclassing

## Template Method Pattern
- A design pattern that defines the skeleton of an algorithm in a method, deferring some steps to subclasses
- The template method is defined in the parent class and calls abstract methods that are implemented by subclasses
- Allows for code reuse and flexibility while still enforcing a common structure for the algorithm

