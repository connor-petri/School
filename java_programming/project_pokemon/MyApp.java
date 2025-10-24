import java.util.*;
import java.io.*;

public class MyApp {
    private static Scanner s = new Scanner(System.in);
    private static ArrayList<String> rows = new ArrayList<String>();

    /**
     * Prompts user to enter data file name
     * if that file exists, load the data from it into static rows ArrayList
     * else prompt the user again
     * @return void
     */
    public static void openDataFile() {
        boolean done = false;
        s.nextLine();
        do {
            try {
                System.out.print("Enter file name: ");
                File f = new File("/home/cpetri/School/java_programming/project_pokemon/" + s.nextLine());
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                boolean eof = false;
                String line;

                do {
                    line = br.readLine();
                    if (line == null)
                        eof = true;
                    else
                        rows.add(line);
                } while (!eof);

                br.close();
                fr.close();
                done = true;
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!done);
    }

    /**
     * Prints CLI UI and prompts the user for their choice
     * @return int representing the user's choice
     */
    private static int showMenu() {
        System.out.println("""
                1 - Exit
                2 - Open Data File
                3 - [Other Stuff Later]
                """);
        System.out.print("(1-2)? ");
        return s.nextInt();
    }

    /**
     * Exit program with thank you message
     * @return void
     */
    private static void exit() {
        System.out.println("Thanks for using this application.");
        System.exit(0);
    }

    /**
     * Prints the first 7 and last 7 lines of the data to confirm proper loading
     */
    private static void Test() {
        for (int i = 0; i < 7; i++) {
            System.out.println(rows.get(i));
        }

        for (int i = rows.size() - 7; i < rows.size(); i++) {
            System.out.println(rows.get(i));
        }
    }

    /**
     * Program entry point
     */
    public static void main(String[] args) {
        int userChoice = 0;
        for (;;) {
            userChoice = showMenu();

            switch (userChoice) {
                case 1:
                    exit();
                    break;
                case 2:
                    openDataFile();
                    Test();
                    break;
            }
        }
    }
}
