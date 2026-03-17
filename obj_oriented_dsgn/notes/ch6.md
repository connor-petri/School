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
