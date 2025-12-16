package src;
import java.util.ArrayList;

/**
 * An interface for classes that need to read data from a csv file
 * @author Connor Petri
 */
public interface IReadData {
    /**
     * A function to open a specified data file
     * @param filename
     * @return boolean representing success or failure
     */
    public boolean openDataFile(String filename);

    /**
     * A function to read the data from an opened file.
     * @return boolean representing success or failure.
     */
    public boolean readDataFile();

    /**
     * A function to pack all csv row data into an ArrayList.
     * @return ArrayList containing csv row data.
     */
    public ArrayList<String> getRawDataList();
}
