# Chapter 4: Interfaces and Polymorphism

---

## Interfaces
Interfaces define a contract that classes can implement. They specify a set of methods (and sometimes static data members) that must be implemented by any class that implements the interface. This allows for a consistent way to interact with different classes that share the same interface. Interfaces prevents the problems of multiple inheritance by allowing a class to implement multiple interfaces without inheriting from multiple classes.

### Example
```java
public interface IInteractable {
    public boolean use(Actor user);
}

public interface ILockable {
    public boolean lock(Actor user);
    public boolean unlock(Actor user);
}

public class Chair implements IInteractable {
    private boolean isOccupied = false;

    public boolean use(Actor user) {
        if (isOccupied) {
            return false;
        }
        user.sit(this);
        return true;
    }
}

public class Door implements IInteractable, ILockable {
    private boolean isLocked = false;

    public boolean use(Actor user) {
        if (isLocked) {
            return false;
        }
        user.open(this);
        return true;
    }

    public boolean lock(Actor user) {
        if (isLocked) {
            return false;
        }
        isLocked = true;
        return true;
    }

    public boolean unlock(Actor user) {
        if (!isLocked) {
            return false;
        }
        isLocked = false;
        return true;
    }
}
```

Classes that implement the same interface can be treated as the same type, allowing for polymorphism. For example, collection classes can store objects of any class that implements a common interface, enabling flexible and reusable code. We can also cast objects to their interface type to call the methods defined in the interface, regardless of the actual class of the object.

```java
public class Main {
    public static void main(String[] args) {
        ArrayList<IInteractable> interactables = new ArrayList<>();
        interactables.add(new Chair());
        interactables.add(new Door());

        Actor actor = new Actor();

        for (IInteractable interactable : interactables) {
            interactable.use(actor);
        }

        Door door = new Door();

        (IInteractable) door.use(actor);
        (ILockable) door.lock(actor);
        (ILockable) door.unlock(actor);
    }
}
```

## Polymorphism
Polymorphism allows objects to change their behavior based on their actual class type, even when accessed through a reference of a common interface or superclass. This is achieved through method overriding, where a subclass provides a specific implementation of a method that is already defined in its superclass or interface. When a method is called on an object, the actual method that gets executed is determined at runtime based on the object's class, allowing for dynamic behavior.

### Method Signature
A method signature consists of the method's name and its parameter types. When a subclass overrides a method from its superclass, it must have the same method signature. This ensures that the overridden method can be called through a reference of the superclass or interface, allowing for polymorphic behavior.

### Binding
1. Early Binding:
    - The method to be called is determined at compile time based on the reference type.
    - This is typically used for static methods, private methods, and final methods.
2. Late Binding:
    - The method to be called is determined at runtime based on the actual class of the object.
    - This is used for instance methods that can be overridden in subclasses, allowing for polymorphism

### Overloading vs Overriding
- Overloading: Multiple methods with the same name but different parameter lists within the same class.
- Overriding: A subclass provides a specific implementation of a method that is already defined in its superclass or interface, with the same method signature.

### Ambiguity Problem
When a class implements multiple interfaces that have methods with the same signature, it can lead to ambiguity. The class must provide an implementation for the method, but it may not be clear which interface's method is being implemented. To resolve this, the class can use explicit interface implementation (in languages that support it) or provide a single implementation that satisfies both interfaces.

## Comparable Interface
The `Comparable` interface is used to define a natural ordering for objects of a class. It requires the implementation of the `compareTo` method, which compares the current object with another object of the same type and returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
```java
public class Person implements Comparable<Person> {
    private String name;
    private int age;  

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age);
    }
}
```

