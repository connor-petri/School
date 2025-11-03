import java.util.*;
import java.io.*;

public class TestData implements ITestData {
    /**
     * Prints the first 7 and last 7 lines of the data to confirm proper loading
     */
    public boolean testPrintFirstLastLines(ArrayList<String> list) {
        if (list.isEmpty()) { return false; }

        for (int i = 0; i < 7; i++) {
            System.out.println(list.get(i));
        }

        for (int i = list.size() - 7; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        return true;
    }

    public boolean testWriteSet(HashSet<String> set, String fileName) {
        try {
            File f = new File("./java_programming/project_pokemon", fileName);
            PrintStream ps = new PrintStream(f);

            for (String pokemon : set) {
                ps.println(pokemon);
            }
            ps.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
