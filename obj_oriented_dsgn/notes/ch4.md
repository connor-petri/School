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

```java
import javax.swing.*;
import java.awt.*;

public class FrameTester {
    public static void main(String[] args) {
        // Create and set up new JFrame
        JFrame frame = new JFrame();
        final int WIDTH = 300;
        final int HEIGHT = 200;
        frame.setSize(WIDTH, HEIGHT);

        // Create components
        JButton helloButton = new JButton("Say Hello");
        final int FIELD_WIDTH = 20;
        JTextField textField = new JTextField(FIELD_WIDTH);
        textField.setText("Click a button!");

        // Add components to the frame
        frame.setLayout(new FlowLayout());
        frame.add(helloButton);
        frame.add(textField);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // Adjusts the frame size to fit the components
        frame.setVisible(true); // Renders the frame on the screen
    }
}
```

### Customization
Using inheritance, we can create custom components by extending existing Swing components and overriding their methods to provide specific behavior. For example, we can create a custom panel that displays a message by extending `JPanel` and overriding the `paintComponent` method to draw the message on the panel.
```java
class HelloFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;

    public HelloFrame() {
        setTitle("Hello Swing");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        HelloPanel panel = new HelloPanel();
        add(panel);
    }
}

class HelloPanel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawString("Hello, World!", 75, 100);
    }
}
```

#### PaintComponent
- JComponent supplies the paintComponent. Each time a window needs to be redrawn, the paintComponent methods of all JComponents will be executed.
- You should *never* call paintComponent directly. Instead, you should call the repaint method, which will schedule a call to paintComponent at the appropriate time. This allows the Swing framework to manage the painting process efficiently and ensures that your component is redrawn correctly when necessary.
- paintComponent will be called when
    - the window is displayed for the first time
    - the user resizes the window
    - The user opens another window which covers the existing window.

### Shape Primitives
- The shape primitives are contained almost entirely in the java.awt.geom package. The most commonly used shape primitives are Line2D, Rectangle2D, Ellipse2D, and Arc2D. These classes provide methods for creating and manipulating basic geometric shapes, which can be drawn on a component using the Graphics2D class. By using these shape primitives, you can create complex graphics and custom components in your Swing applications.

#### Path2D
- The Path2D class allows you to create complex shapes by defining a sequence of points and connecting them with lines or curves. You can use methods like moveTo, lineTo, quadTo, and curveTo to define the path of the shape. Once you have defined the path, you can draw it on a component using the Graphics2D class. This is useful for creating custom shapes and graphics that cannot be easily represented using the basic shape primitives.
- Implements the PathIterator interface, which allows you to iterate over the segments of the path. This is useful for performing operations on the path, such as calculating its length or determining if a point is contained within the path.

#### Rectangle2D
- The Rectangle2D class is abstract and has two concrete subclasses: Rectangle2D.Float and Rectangle2D.Double. The Float subclass uses single-precision floating-point numbers for the coordinates and dimensions of the rectangle, while the Double subclass uses double-precision floating-point numbers. The choice between the two depends on the level of precision required for your application. If you need higher precision, you should use the Double subclass; if you are working with less precise data or want to save memory, you can use the Float subclass.

#### Line Segments
- The Line2D class represents a line segment defined by two endpoints. It has two concrete subclasses: Line2D.Float and Line2D.Double, which use single-precision and double-precision floating-point numbers, respectively. The Line2D class provides methods for calculating the length of the line segment, determining if a point is on the line segment, and finding the intersection of two line segments. This makes it a useful class for working with geometric shapes and performing calculations related to lines in your Swing applications.

### Strings
- The Graphics2D class provides methods for drawing strings on a component. You can use the drawString method to draw a string at a specified location on the component. The appearance of the string can be customized using the setFont and setColor methods of the Graphics2D class. You can also use the FontMetrics class to measure the dimensions of the string, which can be useful for positioning the string accurately on the component. By using these methods, you can create custom text-based graphics and components in your Swing applications.

---

## Event Handling
### ActionListener Interface
```java
public interface ActionListener {
    public void actionPerformed(ActionEvent event);
}
```

### Listener objects
A listener object is an instance of a class that implements the ActionListener interface.
```java
ActionListener listener = new ActionListener() {
    public void actionPerformed(ActionEvent event) {
        textField.setText("Hello World!");
    }
}
```

### Event Source
An event source is an object that generates events. In Swing, components such as buttons, text fields, and menu items can be event sources. When a user interacts with an event source (e.g., clicks a button), it generates an event that is sent to the registered listeners. Event listeners must be attached to the event source to receive events. 

```java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ActionTester {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        final int FIELD_WIDTH = 20;
        JTextField textField = new JTextField(FIELD_WIDTH);
        textField.setText("Click a button!");

        JButton helloButton = new JButton("Say Hello");
        JButton goodbyeButton = new JButton("Say Goodbye");

        helloButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                textField.setText("Hello, World!");
            }
        }

        goodbyeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                textField.setText("Goodbye, World!");
            }
        }

        frame.setLayout(new FlowLayout());
        frame.add(helloButton);
        frame.add(goodbyeButton);
        frame.add(textField);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
```

---

## Nested Classes
Nested classes are classes that are defined within another class. They can be used to logically group classes that are only used in one place, which can help to improve the readability and maintainability of your code. There are four types of nested classes in Java: static nested classes, non-static nested classes (also known as inner classes), local classes, and anonymous classes. Each type of nested class has its own specific use case and can be used to encapsulate functionality that is closely related to the enclosing class.

### Static Nested Classes
A static nested class is a nested class that is declared static. It can be accessed without an instance of the enclosing class and can only access static members of the enclosing class.

### Non-Static Nested Classes (Inner Classes)
An inner class is a non-static nested class. It can access all members of the enclosing class, including private members, and is associated with an instance of the enclosing class. To create an instance of an inner class, you need to have an instance of the enclosing class.
```java
public class OuterClass {
    private int outerField;

    public class InnerClass {
        public void accessOuter() {
            System.out.println("Outer field: " + outerField);
        }
    }
}
```
To create an instance of the InnerClass, you would do the following:
```java
OuterClass outer = new OuterClass();
OuterClass.InnerClass inner = outer.new InnerClass();
inner.accessOuter();
```

### Local Inner Classes
A local inner class is a class that is defined within a method of the enclosing class. It can access the final or effectively final local variables of the method, as well as the members of the enclosing class. Local inner classes are typically used for event handling or to define a small helper class that is only needed within the scope of a method.
```java
public class OuterClass {
    public void someMethod() {
        final int localVariable = 10;   
        LocalInnerClass localInner = new LocalInnerClass() {
            public void accessLocal() {
                System.out.println("Local variable: " + localVariable);
            }
        };
        localInner.accessLocal();
    }
}
```