package src;
import java.io.*;
import java.util.*;

/**
 * Provides methods for reading Pokemon csv data
 * @author Connor Petri
 * @see IReadData
 */
public class ReadData implements IReadData {
    private ArrayList<String> rows = new ArrayList<String>();

    private File f;
    private FileReader fr;
    private BufferedReader br;

    /**
     * Prompts user to enter data file name
     * if that file exists, load the data from it into static rows ArrayList
     * else prompt the user again
     * @return void
     */
    public boolean openDataFile(String filename) {
        try {
            f = new File("/home/cpetri/School/java_programming/project_pokemon/" + filename);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
                return false;
            }
        return true;
    }

    public boolean readDataFile() {
        boolean eof = false;
        String line;

        try {
            do {
                line = br.readLine();
                if (line == null)
                    eof = true;
                else
                    rows.add(line);
            } while (!eof);

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<String> getRawDataList() {
        return rows;
    }
}
