# Week 6

---

## Inheritance
- When we initialize an object, we always must use the ```new``` keyword. This means that all objects in Java are allocated to the heap.
- If an inherited class doesn't define a constructor, ```super()``` is called automatically.
- Where a superclass is expected, a subclass is accepted. This is not reversible, so assigning an instance of ```super``` to a variable of type ```child```, things will break.

```java
class Star {...};
class RedGiant extends Star {...};
class BlackHole extends Star {...};

class Galaxy {
    private ArrayList<Star> starts;

    Galaxy() { stars = new ArrayList<Star>(); }

    void addStar(Star s) { starts.add(s); }

    double getAverageMass() {
        double totalMass = 0;
        for Star s: stars) {
            totalMass += s.getMass();
        }
        return totalMass / stars.size();
    }
}

Galaxy g = new Galaxy();
Star s = new Star();
RedGiant rg = new RedGiant();
BlackHole bh = new BlackHole();

// All of these are valid, but Galaxy will only see them as Stars.
// If data from child class is required, you must type cast it within Galaxy to access it.
g.addStar(s);
g.addStar(rg);
g.addStar(bh);
```

## The ```Object``` Class
The Object class is the parent of all classes in Java.

### About the ```wait()``` method
The ```wait()``` function is an ```Object``` class method. It is used to pause the execution of a thread until another thread calls ```wait()``` on an object, at which time it releases the lock on the object.

## Polymorphism
Polymorphism is the practice of overriding a base class method in a derived class.
```java
class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println(this.name + " speaks.");
    }
}

class Dog extends Animal {
    protected String owner;

    public Dog(String name, String owner) {
        super(name);
        this.owner = owner;
    }

    // Override
    public void speak() {
        System.out.print(this.owner + "'s dog " + this.name + " barks.");
    }
}
```

## Memory Regions
- References go on the stack.
- Objects go on the heap.
- After creation, the class of an object does not change.
- The object’s class is known by the ctor that was called.
- An object may be referred to by references of various types:
    - Actual class of the object
    - Any superclass of the object
    - Any interface the class inherits from.
