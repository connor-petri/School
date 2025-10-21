# Week 9:

---

## Project Information
- There is a file (.csv) containing data on 802 pokemon.
- We are going to use it in some way.

---

## File IO
### IO Streams
- Low level streams read characters from the world and deliver them to other objects.
    - Usually those other objects are high-level streams.
- High-level streams read characters from low-level streams and assembles them into useful objects (i.e. Strings).

### ```FileReader```, ```BufferReader```, and ```URLReader```
- ```FileReader``` reads from filestream one character at a time.
- ```BufferReader``` reads from filestream one line at a time.
    - Requires instance of ```FileReader``` at instantiation.
- ```URLReader``` reads data from the internet in the form of web requests.

### Example of Reading From File
```java
TreeSet<String> allBirds = new TreeSet<String>();
File dirf = new File("~/Projects/Birds/data");

for (int i = 1; i <= 100; i++) {
    File listf = new File(dirf, "Data" + i + ".txt");
    try {
        FileReader fr = new FileReader(listf);
        BufferedReader br = new BufferedReader(fr);
        String line;
        boolean done = false;
        while (!done) {
            line = br.readLine();
            if (line == null)
                done = true;
            else
                allBirds.add(line);
        }
        br.close();
        fr.close();
    } catch (FileNotFoundException e) {
        System.out.println("No such file: " + listf);
    } catch (IOException e) {
        System.out.println("IO Failure");
    }
}
```
## Throwables
- ```Throwable``` is a base class for ```Error``` and ```Exception```.
- Contains a ```String``` message and a stack trace.
    - ```public String getMessage()``` and ```public void printStackTrace()```
- Subclass names convey more specific information about that type of throwable.

### Passing the exception up
Instead of putting a try-catch block inside of a function, you can specify that a specific set of exceptions are to be handled by the caller of the function.

```java
public static String readFile() throws IOException { ... }
```