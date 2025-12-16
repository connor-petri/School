package src;
import java.util.*;


/**
 * Driver Class
 * 
 * @author Connor Petri
 * @see ReadData
 * @see TestData
 * @see AnalyzePokemon
 * @see Pokemon
 */
public class MyApp {
    private static Scanner s = new Scanner(System.in);
    private static ReadData readData = new ReadData();
    private static TestData testData = new TestData();
    private static AnalyzePokemon ap = new AnalyzePokemon();

    // Part 3 TreeSets
    private static TreeSet<Pokemon> hpTree = new TreeSet<>();
    private static TreeSet<Pokemon> speedTree = new TreeSet<>();

    private static HashSet<String> hs = new HashSet<>();

    // User Choices
    private static final int cExit = 1;
    private static final int cOpen = 2;
    private static final int cSearch = 3;
    private static final int cSearchAttribute = 4;
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
                4 - Find Pokemon via attributes
                10 - Unit Test
                """);
        System.out.print("(1-4 or 10)? ");
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

        // Build trees
        boolean first = false;
        for (String row : readData.getRawDataList()) {
            if (!first) { 
                first = true;
                continue;
            }
            Pokemon pkmn = new Pokemon(row);

            Pokemon.sortOption = Pokemon.SortOption.HP;
            hpTree.add(pkmn);

            Pokemon.sortOption = Pokemon.SortOption.SPEED;
            speedTree.add(pkmn);
        }
    }

    /**
     * Searches for a pokemon by name. Prints row data if a match is found
     * This function does not use AnalyzePokemon due to needing to print the row data instead of just the name.
     * This Function is from Part 2 of the assignment, before we were instructed to write the pokemon class 
     * thus, it doesn't use it.
     */
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

    private static void searchByExactHP() {
        System.out.print("Enter HP value to search for: ");
        int hpQuery = s.nextInt();
        
        for (Pokemon pokemon : hpTree) {
            if (pokemon.getStats().hp == hpQuery) {
                System.out.println("Pokemon found with HP " + hpQuery + ":");
                System.out.println(pokemon.getName());
                return;
            }
        }
        
        System.out.println("No Pokemon found with HP value: " + hpQuery);
    }


    /**
     * Prints the name of all pokemon with an hp stat within a specified range
     */
    private static void searchByRangeHP() {
        System.out.print("Enter minimum HP value: ");
        int min = s.nextInt();
        System.out.print("Enter maximum HP value: ");
        int max = s.nextInt();
        
        boolean found = false;
        System.out.println("Pokemon found with HP between " + min + " and " + max + ":");
        
        for (Pokemon pokemon : hpTree) {
            int hp = pokemon.getStats().hp;
            if (hp >= min && hp <= max) {
                System.out.print(pokemon.getName() + " ");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No Pokemon found with HP values in range " + min + " - " + max);
        }

        System.out.println("");
    }

    /**
     * Prints the name of the Pokemon with the lowest hp stat.
     */
    private static void getLowestHP() {
        System.out.println("Lowest HP pokemon: " + hpTree.first().getName());
    }

    /**
     * Prints the name of the Pokemon with the highest hp stat.
     */
    private static void getHighestHP() {
        System.out.println("Highest HP pokemon: " + hpTree.last().getName() + " has " + hpTree.last().getStats().hp + " hp");
    }

    /**
     * Sub menu for searching for a pokemon by HP value. For use by the menuing system only
     */
    private static void searchByHP() {
        int userChoice;

        do {
            System.out.println("""
                    1. Find a character with a specific hit point value
                    2. Find characters withing a specific range of hit point values
                    3. Find the character with the lowest hit point value
                    4. Find the character with the highest hit point value
                    5. Return to previous menu
                    """);
            userChoice = s.nextInt();
        } while (userChoice < 1 || userChoice > 5);

        switch (userChoice) {
            case 1:
                searchByExactHP();
                break;
            case 2:
                searchByRangeHP();
                break;
            case 3:
                getLowestHP();
                break;
            case 4:
                getHighestHP();
                break;
            default:
                System.out.println("Returning...");
                break;
        }

        return;
    }

    /**
     * Prints the name of the 3 Pokemon with the highest speed stats.
     */
    private static void getPokemonTop3Speeds() {
        int l = 0, m = 0, h = 0;
        for (Pokemon p : speedTree) {
            if (p.getStats().speed > h) {
                l = m;
                m = h;
                h = p.getStats().speed;
            }
        }

        System.out.println("Pokemon with the top 3 speeds:");
        for (Pokemon p : speedTree) {
            if (p.getStats().speed == l || p.getStats().speed == m || p.getStats().speed == h) {
                System.out.print(p.getName() + " ");
            }
        }
        System.out.println("");
    }

    /**
     * Prints the name of the 3 Pokemon with the lowest speed values
     */
    private static void getPokemonBottom3Speeds() {
        int l = Integer.MAX_VALUE, m = Integer.MAX_VALUE, h = Integer.MAX_VALUE;
        
        for (Pokemon p : speedTree) {
            int speed = p.getStats().speed;
            if (speed < l) {
                h = m;
                m = l;
                l = speed;
            } else if (speed < m && speed != l) {
                h = m;
                m = speed;
            } else if (speed < h && speed != m && speed != l) {
                h = speed;
            }
        }

        System.out.println("Pokemon with the bottom 3 speeds:");
        for (Pokemon p : speedTree) {
            if (p.getStats().speed == l || p.getStats().speed == m || p.getStats().speed == h) {
                System.out.print(p.getName() + " ");
            }
        }

        System.out.println("");
    }

    /**
     * Prints the names of Pokemon with a speed stat within a specified range
     */
    private static void getPokemonInSpeedRange() {
        System.out.print("Enter minimum speed value: ");
        int min = s.nextInt();
        System.out.print("Enter maximum speed value: ");
        int max = s.nextInt();
        
        boolean found = false;
        System.out.println("Pokemon found with speed between " + min + " and " + max + ":");
        
        for (Pokemon pokemon : speedTree) {
            int speed = pokemon.getStats().speed;
            if (speed >= min && speed <= max) {
                System.out.print(pokemon.getName() + " ");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No Pokemon found with HP values in range " + min + " - " + max);
        }
    }

    /**
     * Prints the 3 Speed values with the highest number of Pokemon associated with them.
     */
    private static void getTop3SpeedGroups() {
        // Get the 3 highest unique speeds
        Set<Integer> uniqueSpeeds = new TreeSet<>();
        for (Pokemon p : speedTree) {
            uniqueSpeeds.add(p.getStats().speed);
        }
        
        List<Integer> sortedSpeeds = new ArrayList<>(uniqueSpeeds);
        Collections.sort(sortedSpeeds, Collections.reverseOrder());
        
        // Get top 3 speeds
        List<Integer> top3Speeds = new ArrayList<>();
        for (int i = 0; i < Math.min(3, sortedSpeeds.size()); i++) {
            top3Speeds.add(sortedSpeeds.get(i));
        }
        
        // Group Pokemon by these speeds
        Map<Integer, List<Pokemon>> speedGroups = new HashMap<>();
        for (int speed : top3Speeds) {
            speedGroups.put(speed, new ArrayList<>());
        }
        
        for (Pokemon p : speedTree) {
            if (top3Speeds.contains(p.getStats().speed)) {
                speedGroups.get(p.getStats().speed).add(p);
            }
        }
        
        // Print results
        String[] groupNames = {"first", "second", "third"};
        for (int i = 0; i < top3Speeds.size(); i++) {
            int speed = top3Speeds.get(i);
            System.out.println("Pokemon in " + groupNames[i] + " group (speed " + speed + "): ");
            for (Pokemon p : speedGroups.get(speed)) {
                System.out.print(p.getName() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Print the names of all Pokemon in the most populous set with equal speed values
     */
    private static void getPokemonInLargestSpeedGroup() {
        int currentSpeed = -1;
        int currentCount = 0;
        int maxCount = 0;

        for (Pokemon p : speedTree) {
            if (p.getStats().speed > currentSpeed) {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                }
                currentCount = 0;
                currentSpeed = p.getStats().speed;
            } else {
                currentCount++;
            }
        }

        System.out.println("Pokemon in largest speed group: ");

        for (Pokemon p : speedTree) {
            if (p.getStats().speed == currentSpeed) {
                System.out.print(p.getName() + " ");
            }
        }
    }

    /**
     * Submenu for searching by speed. For menuing system use only
     */
    private static void searchBySpeed() {
        int userChoice;
        do {
            System.out.println("""
                    1. Which pokemon has the fastest speed
                    2. Which pokemon has the slowest speed
                    3. Which pokemon are part of the top 3 fastest speeds
                    4. Which pokemon are part of the 3 slowest speeds
                    5. Which pokemon are part of a specific ranges of speeds
                    6. What are the top 3 speed groups
                    7. Which pokemon represent the largest speed group
                    8. Return to previous menu
                    """);
            userChoice = s.nextInt();
        } while (userChoice < 1 || userChoice > 8);

        switch (userChoice) {
            case 1:
                System.out.println("Highest Speed Pokemon: " + speedTree.last().getName() + " has a speed of " + speedTree.last().getStats().speed);
                break;
            case 2:
                System.out.println("Lowest speed pokemon: " + speedTree.first().getName() + " has a speed of " + speedTree.first().getStats().speed);
                break;
            case 3:
                getPokemonTop3Speeds();
                break;
            case 4:
                getPokemonBottom3Speeds();
                break;
            case 5:
                getPokemonInSpeedRange();
                break;
            case 6:
                getTop3SpeedGroups();
                break;
            case 7:
                getPokemonInLargestSpeedGroup();
                break;
            default:
                System.out.println("Returning...");
        }
    }

    /**
     * Sub menu for searching by attribute. For menuing system use only.
     */
    private static void searchByAttribute() {
        int userChoice;
        System.out.println("""
                1. Get all Pokemon with a specific hit point value
                2. Get all Pokemon with a specific speed value
                """);
        userChoice = s.nextInt();
        if (userChoice == 1) {
            searchByHP();
        } else if (userChoice == 2) {
            searchBySpeed();
        } else {
            System.out.println("Returning");
        }
        return;
    }

    /**
     * Runs all unit tests
     */
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
                case cSearchAttribute:
                    searchByAttribute();
                    break;
                case cTest:
                    unitTest();
                    break;
            }
        }
    }
}
