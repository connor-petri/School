# Chapter 5: Design Patterns

---

## Introduction to Design Patterns
Design patterns are reusable solutions to common software design problems. They provide a way to structure code in a way that is efficient, maintainable, and scalable. Design patterns are not specific to any programming language, but they can be implemented in various languages, including Java.

## Iterator Pattern
The Iterator pattern provides a collection with external iterators that support imultaneus accesses to the collection.

### Interator Interface
The Iterator interface defines the methods that an iterator must implement. These methods include `hasNext()`, `next()`, and `remove()`. The `hasNext()` method checks if there are more elements in the collection, while the `next()` method returns the next element in the collection. Iterators are external classes that are separate from the collection they iterate over.

## Observer Pattern
The Observer pattern defines a one-to-many dependency between objects, where a change in one object (the subject) triggers updates in all dependent objects (the observers). This pattern is useful for implementing event handling systems, where multiple components need to be notified of changes in a central object.

### MVC
The Model-View-Controller (MVC) pattern is a specific implementation of the Observer pattern. It separates an application into three main components: the Model, which represents the data and business logic; the View, which is responsible for displaying the data; and the Controller, which handles user input and updates the Model and View accordingly. This separation of concerns allows for more modular and maintainable code.

#### Parts of a Model
1. Data structure to hold data
2. Data structure to hold view
3. Accessor
4. Mutator
5. attatch


```java
public class Invoice {
    private ArrayList<LineItem> items;
    private ArrayList<ChangeListener> listeners;

    public void attach(ChangeLIstener listener) {
        listener.add(listener);
    }

    public String format() { // Accessor
        // returns string representation of invoice
    }

    public void addItem(LineItem item) { // Mutator
        items.add(item);

        for (ChangeEvent listener : listeners) {
            listener.stateChanged(event);
        }
    }
}
```

#### View Components
1. UI Elements
2. Listener attached to the model

---

## Strategey Pattern
- A way to define a family of algorithms
- Encapsulate each one as an object
- Make them interchangeable usually via passing an object with relevent methods


## Composite Pattern
The composite pattern is a recursive way to compose objects into tree structures to represent parts. It is often done to represent heirarchical data structures.

### Context
1. Primitive objects can be combined into composite objects
2. Clients treat a composite object and primitive object in the same way (i.e. .paint())


---

## Decorator Pattern
The decorator pattern allows us to attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to sub-class leasing for extending functionality. Decorations is the functional or visual enhancement of an object. Decorating is dynamic as opposed to inheritance which is static.