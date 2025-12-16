# Chapter 1 Notes

Java was made by a group of engineers at Sun Microsystems lead by James Gosling. SM was bought by Oracle in 2010. Java was created to solve a lot of the problems C++ had, such as memory safety and memory leaks. 

Java is both a compiled and an interpreted language. The source code (.java) Is turned into bytecode (.class), which is then run by the JVM. This was done to allow programs to run on any OS that has a JVM developed for it.

### Helpful Vocab
- IO Devices
- Storage
- RAM/VRAM
- Provessor
- Clock
- Transistors
- Integrated Circuits (IC)
- Driver File - The file that containes the program entry point.

### HelloWorld.java
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
```
Every file in java will contain a class. The file name must match the class name exactly.

### JavaDocs
JavaDocs is a documentation generation tool that uses comment tags to pull info from your code comments into a document.