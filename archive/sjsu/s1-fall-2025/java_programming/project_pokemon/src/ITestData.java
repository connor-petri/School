package src;
import java.util.*;

/**
 * An interface for classes wishing to test FileIO methods
 * @author Connor Petri
 */
public interface ITestData {
    /**
     * A function to print the first and last n lines to test successful file read..
     * @param list ArrayList of csv row data.
     * @return boolean representing success or failure.
     */
    public boolean testPrintFirstLastLines(ArrayList<String> list);

    /**
     * A function to test file output
     * @param list HashSet of data to write to file.
     * @param fileName Name of the file to write to.
     * @return boolean representing success or failure.
     */
    public boolean testWriteSet(HashSet<String> list, String fileName);
}
