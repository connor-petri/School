# Week 10

---

## Writing to Files
- ```FileWriter``` is analogous to the ```FileReader```, but for writing out to files. 
- ```PrintWriter``` writes to file using the same syntax as ```System.out.println```


## Make a custom exception
```java
public class PNGException extends Exception {
    PNGException() {
        super();
    }

    PNGException(String message) {
        super(message);
    }

    PNGException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "Error reading PNG file: " + this.message;
    }
}
```

### ```createNewFile()```
- Returns a boolean related to the success of creating the file.
```java
try {
    File f = new File("filename.txt");
    if (f.createNewFile()) {
        System.out.println("File created: " + f.getName())
    } else {
        System.out.println("File already exists.");
    }
} catch (IOException e) {
    System.out.println("An error occurred.");
    e.printStackTrace();
}
```

## Log Files
- Use ```java.util.logging.*```
```java
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

class LoggerExample {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("MyLogger");
        FileHandler fh;
        
        try {
            // Create file handler that writes to mylog.txt
            fh = new FileHandler("mylog.txt");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            
            // Set logging level
            logger.setLevel(Level.INFO);
            
            // Log messages at different levels
            logger.info("This is an info message");
            logger.warning("This is a warning message");
            logger.severe("This is a severe error message");
            
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```