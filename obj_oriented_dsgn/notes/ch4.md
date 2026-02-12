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

### ```Comparable``` vs ```Comparator```
- `Comperable`: Defines a natural ordering for objects of a class. The class itself implements the `compareTo` method. We override the `compareTo` method in the class to define how objects of that class should be compared.
- `Comparator`: An interface that defines a custom ordering for objects of a class. It allows us to create multiple comparators for different sorting criteria without modifying the class itself. We implement the `compare` method in a separate class that implements the `Comparator` interface to define how objects should be compared based on specific criteria.
- We do this because we can pass the comparator to sorting methods to sort objects based on different criteria without changing the class definition, providing flexibility and separation of concerns.
```java
public class AgeComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return Integer.compare(p1.getAge(), p2.getAge());
    }
```

## Annonymous Classes
Anonymous classes are a way to define and instantiate a class at the same time, without giving it a name. They are often used to implement interfaces or extend classes in a concise way, especially when the implementation is only needed in a single place. Anonymous classes can be used to create instances of interfaces or abstract classes without having to create a separate named class for the implementation.
```java
Comparator<Country> comp = new
    Comparator<Country>() {
        public int compare(Country country1, Country country2) {
            return country1.getName().compareTo(country2.getName());
        }
    };
```

### Factory Method
- If we want to create multiple instances of an anonymous class, we can use a factory method to encapsulate the creation logic. A factory method is a static method that returns an instance of a class based on some input parameters. This allows us to reuse the anonymous class implementation without having to duplicate the code for each instance.
```java
public class ComparatorFactory {
    public static Comparator<Country> createNameComparator() {
        return new Comparator<Country>() {
            public int compare(Country country1, Country country2) {
                return country1.getName().compareTo(country2.getName());
            }
        };
    }
}
```

---

### Graphics Programming (Swing)
#### AWT
- A class library for basic GUI programming. Swing has some dependency on AWT for event handling and some components, but it provides a more flexible and powerful set of GUI components compared to AWT.
#### Swing
- A more advanced GUI toolkit that provides a richer set of components and a more flexible architecture for building graphical user interfaces. Swing components are lightweight and can be customized more easily than AWT components.

