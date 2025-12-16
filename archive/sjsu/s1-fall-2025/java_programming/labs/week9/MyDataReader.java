package week9;


/**
 * BufferedReader reads from a file line-by-line
 */
import java.io.BufferedReader;

/**
 * FileReader reads the file character-by-character
 */
import java.io.FileReader;

/**
 * IOException is used for problems involving FileIO
 */
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class MyDataReader {
    public static void main(String[] args) throws IOException {
        File dataFile = new File("/home/cpetri/School/java_programming/labs/week9", "data.txt");
        ArrayList<String> lines = new ArrayList<String>();

        try {
            FileReader fr = new FileReader(dataFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            boolean done = false;

            while (!done) {
                line = br.readLine();
                if (line == null) { done = true; }
                else { lines.add(line); }
            }

            System.out.println("# of rows: " + lines.size());
            for (String l : lines) {
                System.out.println(l);
            }

            for (int i = 0; i < lines.size(); i++) {
                int numbers = 0;
                for (char c : lines.get(i).toCharArray()) {
                    numbers += Character.isDigit(c) ? 1 : 0;
                }
                if (numbers > 0) {
                    System.out.println("Line " + (i+1) + " has " + numbers + " numbers.");
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }    
}
