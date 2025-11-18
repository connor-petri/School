# Week 14 - Patterns and GUI Programming

---

## JavaFX Overview
JavaFX is a set of graphics and media packages that enables developers to design, create, test, debug, and deploy rich client applications that operate consistently across diverse platforms. It is the successor to Swing for building graphical user interfaces (GUIs) in Java.

### Key Features of JavaFX
- ```JCanvas```: A node that can be used for drawing shapes, images, and text.
- ```JFrame``` and ```JPanel```: Basic building blocks for creating GUI applications.
- ```Scene``` and ```Stage```: Core components of JavaFX applications.
- ```FXML```: An XML-based language for defining the user interface.
- ```CSS```: Used for styling JavaFX applications.
- ```Event Handling```: Mechanism to handle user interactions.

### Layout Manager Interface
```java
public interface LayoutManager {
    Dimension minimumLayoutsize(Contrtainer parent);
    Dimension preferredLayoutSize(Container parent);
    void layoutContainer(Container parent);
    void addLayoutComponent(String name, Component comp);
    void removeLayoutComponent(Component comp);
}
```

## The Strategy Pattern
The Strategy Pattern is a behavioral design pattern that enables selecting an algorithm's behavior at runtime. It defines a family of algorithms, encapsulates each one, and makes them interchangeable. This pattern allows the algorithm to vary independently from clients that use it. In the context of GUI programming, it can be used to change the layout strategy of a container dynamically.

## The Composite Pattrern
The Composite Pattern is a structural design pattern that allows you to compose objects into tree structures to represent part-whole hierarchies. It lets clients treat individual objects and compositions of objects uniformly. In GUI programming, this pattern is often used to manage complex user interfaces where components can contain other components.

## The Decorator Pattern
The Decorator Pattern is a structural design pattern that allows behavior to be added to individual objects, either statically or dynamically, without affecting the behavior of other objects from the same class. In GUI programming, decorators can be used to add functionalities to GUI components, such as adding borders, scrollbars, or other visual enhancements.

### Stream Decorators
Another example for the decorator pattern is the set of stream filters in the I/O library.
    - The Reader class supports basic input operations.
    - The FileReader subclass implements these methods, reading characters from a file. However, a FileReader has no method for reading a line of input.
    - The BufferedReader class is a decorator that adds buffering and line-reading capabilities to any Reader object.
    - You can create a BufferedReader that wraps a FileReader to read lines from a file.

