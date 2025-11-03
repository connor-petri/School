import java.util.*;

public class MyApp {
    private static Scanner s = new Scanner(System.in);
    private static ReadData readData = new ReadData();
    private static TestData testData = new TestData();
    private static AnalyzePokemon ap = new AnalyzePokemon();

    private static HashSet<String> hs = new HashSet<>();

    // User Choices
    private static final int cExit = 1;
    private static final int cOpen = 2;
    private static final int cSearch = 3;
    private static final int cTest = 10;

    private static final int sTestPrintLines = 1;
    private static final int sWriteToFile = 2;

    /**
     * Prints CLI UI and prompts the user for their choice
     * @return int representing the user's choice
     */
    private static int showMenu() {
        System.out.println("""
                1 - Exit
                2 - Open and Read Data File
                3 - Search by Name
                10 - Unit Test
                """);
        System.out.print("(1-3 or 10)? ");
        return s.nextInt();
    }

    private static int showSubMenu() {
        System.out.println("""
                1 - Print the first and last 7 lines
                2 - Write character names to file
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

    private static void openAndReadFile() {
        for (int i = 0; i < 2; i++) {
            System.out.print("File name: ");
            s.nextLine(); // Flush buffer
            
            if (readData.openDataFile(s.nextLine().trim())) {
                readData.readDataFile();
                break;
            } else if (i == 0) {
                System.out.println("Try Again");
            } else {
                System.out.println("Aborting");
            }
        }
    }

    // This function does not use AnalyzePokemon due to needing to print the row data instead of just the name
    private static void searchByName() {
        System.out.print("Search by Name: ");
        s.nextLine(); // Flush buffer
        String searchName = s.nextLine().trim();
        
        ArrayList<String> rawData = readData.getRawDataList();
        boolean found = false;
        
        for (String row : rawData) {
            // Skip header
            if (row.startsWith("abilities")) {
                continue;
            }
            
            // Regex created with the help of one of many regex websites
            // Handles fields in quotes as .split was failing when called with just ","
            String[] columns = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            
            // Check for column count
            if (columns.length > 30) {
                String pokemonName = columns[30].trim().replaceAll("\"", "");
            
                if (pokemonName.equalsIgnoreCase(searchName)) {
                    System.out.println("Pokemon found:");
                    System.out.println(row);
                    found = true;
                    break;
                }
            }
        }
        
        if (!found) {
            System.out.println("Pokemon '" + searchName + "' not found.");
        }
    }

    private static void unitTest() {
        int userChoice = showSubMenu();
    

        switch (userChoice) {
            case sTestPrintLines:
                testData.testPrintFirstLastLines(readData.getRawDataList());
                break;
            case sWriteToFile:
                hs.clear();
    
                ArrayList<String> rawData = readData.getRawDataList();
                hs = ap.getAllCharacterNames(rawData);

                testData.testWriteSet(hs, "names.txt");
                break;
            default:
                System.out.println("Aborting...");
        }
    }

    /**
     * Program entry point
     */
    public static void main(String[] args) {
        int userChoice;

        for (;;) {
            userChoice = showMenu();

            switch (userChoice) {
                case cExit:
                    exit();
                    break;
                case cOpen:
                    openAndReadFile();
                    break;
                case cSearch:
                    searchByName();
                    break;
                case cTest:
                    unitTest();
                    break;
            }
        }
    }
}
