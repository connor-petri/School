# Week 8

---

## JavaDoc
javaDoc is a tool which allows documentation to be generated from your ```.java``` file. You can invoke javadoc by making a comment that starts with ```/**```. The following is an example of a JavaDoc annotation.

```java
/**
 * getGPSData fetches the readings from a GNSS sensor and outputs it into a struct.
 * @param gps is a reference to a GPS sensor object
 * @return gpsData struct that has each reading from the instrument.
 */
public static gpsData getGPSData(GNSS gps)
```

### Tags
- ```@author [Author Name]``` Documents code author
- ```@version [Version Number]``` Documents code version
- ```@param varName [Info on Param]``` Documents a method parameter
- ```@return [Info on return value]``` Documents a method return value
- ```@throws/@exception``` Documents all exceptions thrown by method
- ```@see``` Creates a link to another class, method, or field.
- ```@since``` Indicates when a feature was added.