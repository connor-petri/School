package src;
import java.util.*;

/**
 * A class to hold data for each Pokemon
 * @author Connor Petri
 * @see StatBlock
 */
public class Pokemon implements Comparable<Pokemon>{

    /**
     * Enum for sorting options for compareTo
     */
    public enum SortOption {
        NAME, HP, SPEED
    }

    /**
     * Enum containing all Pokemon types and NONE
     */
    public enum Type {
        NONE, FIRE, WATER, GRASS, ELECTRIC, PSYCHIC, ICE, DRAGON, DARK, FIGHTING, 
        POISON, GROUND, FLYING, BUG, ROCK, GHOST, STEEL, NORMAL, FAIRY
    }

    public static SortOption sortOption = SortOption.NAME;

    private String name;
    private String japaneseName;
    private Type type1;
    private Type type2;
    private int pokedexNum;

    private StatBlock stats;

    private ArrayList<String> abilities = new ArrayList<>();
    private HashMap<Type, Float> multipliers = new HashMap<>();

    private int baseEggSteps;
    private int baseHappiness;
    private int base_total;
    private float captureRate;
    private String classification;
    private float xpGrowth;
    private float height_m;
    private float percentageMale;
    private float weight_kg;
    private int generation;
    private boolean isLegendary;

    /**
     * Constructs a Pokemon from a row of csv data
     * @param csvRow
     * 
     * @see StatBlock
     */
    public Pokemon(String csvRow) {
        // Split the row into columns
        String[] columns = csvRow.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        // Abilities
        String[] ab = columns[0].replace("\"", "")
                                .replace("[", "")
                                .replace("]", "")
                                .replace("'", "")
                                .split(",");
        for (String ability : ab) {
            abilities.add(ability);
        }

        // Types
        type1 = stringToType(columns[36]);
        type2 = stringToType(columns[37]);

        // Multiplier Table
        multipliers.put(Type.BUG, Float.parseFloat(columns[1]));
        multipliers.put(Type.DARK, Float.parseFloat(columns[2]));
        multipliers.put(Type.DRAGON, Float.parseFloat(columns[3]));
        multipliers.put(Type.ELECTRIC, Float.parseFloat(columns[4]));
        multipliers.put(Type.FAIRY, Float.parseFloat(columns[5]));
        multipliers.put(Type.FIGHTING, Float.parseFloat(columns[6]));
        multipliers.put(Type.FIRE, Float.parseFloat(columns[7]));
        multipliers.put(Type.FLYING, Float.parseFloat(columns[8]));
        multipliers.put(Type.GHOST, Float.parseFloat(columns[9]));
        multipliers.put(Type.GRASS, Float.parseFloat(columns[10]));
        multipliers.put(Type.GROUND, Float.parseFloat(columns[11]));
        multipliers.put(Type.ICE, Float.parseFloat(columns[12]));
        multipliers.put(Type.NORMAL, Float.parseFloat(columns[13]));
        multipliers.put(Type.POISON, Float.parseFloat(columns[14]));
        multipliers.put(Type.PSYCHIC, Float.parseFloat(columns[15]));
        multipliers.put(Type.ROCK, Float.parseFloat(columns[16]));
        multipliers.put(Type.STEEL, Float.parseFloat(columns[17]));
        multipliers.put(Type.WATER, Float.parseFloat(columns[18]));

        // Statblock (hp, attack, defense, spAttack, spDefense, Speed)
        stats = new StatBlock(Integer.parseInt(columns[28]),
                            Integer.parseInt(columns[19]),
                            Integer.parseInt(columns[25]),
                            Integer.parseInt(columns[33]),
                            Integer.parseInt(columns[34]),
                            Integer.parseInt(columns[35]));

        // baseEggSteps = Integer.parseInt(columns[20]);
        // baseHappiness = Integer.parseInt(columns[21]);
        // base_total = Integer.parseInt(columns[22]);
        // captureRate = Float.parseFloat(columns[23]);
        // classification = columns[24];
        // xpGrowth = Float.parseFloat(columns[26]);
        // height_m = Float.parseFloat(columns[27]);
        // japaneseName = columns[29];
        name = columns[30];
        // percentageMale = Float.parseFloat(columns[31]);
        // pokedexNum = Integer.parseInt(columns[32]);
        // weight_kg = Float.parseFloat(columns[38]);
        // generation = Integer.parseInt(columns[39]);
        // if (Integer.parseInt(columns[40]) == 1) {
        //     isLegendary = true;
        // } else {
        //     isLegendary = false;
        // }
    }

    @Override
    /**
     * Override of .compareTo to enable sorting. Sort priority is set by user
     * - NAME uses a string comparison on the names of each pokemon
     * - HP compares by hp stat
     * - SPEED compares by speed stat
     * 
     * @param other pokemon to compare against
     * @return negative if this < other, positive if this > other, 0 if they are equal
     */
    public int compareTo(Pokemon other) {
        switch (sortOption) {
            case SortOption.NAME:
                return name.compareTo(other.getName());
            case SortOption.HP:
                return stats.hp - other.getStats().hp != 0 ? (stats.hp - other.getStats().hp) / Math.abs(stats.hp - other.getStats().hp) : 0;
            case SortOption.SPEED:
                return stats.speed - other.getStats().speed != 0 ? (stats.speed - other.getStats().speed) / Math.abs(stats.speed - other.getStats().speed) : 0;
            default:
                System.out.println("WARNING: Pokemon.sortOption SET TO INVALID VALUE");
                return 0;
        }
    }


    // Accessors -----------------------------
    public String getName() {
        return name;
    }

    public String getJapaneseName() {
        return japaneseName;
    }

    public Type getType1() {
        return type1;
    }

    public Type getType2() {
        return type2;
    }

    public int getPokedexNum() {
        return pokedexNum;
    }

    public StatBlock getStats() {
        return stats;
    }

    public ArrayList<String> getAbilities() {
        return abilities;
    }

    public HashMap<Type, Float> getMultipliers() {
        return multipliers;
    }

    public int getBaseEggSteps() {
        return baseEggSteps;
    }

    public int getBaseHappiness() {
        return baseHappiness;
    }

    public int getBaseTotal() {
        return base_total;
    }

    public float getCaptureRate() {
        return captureRate;
    }

    public String getClassification() {
        return classification;
    }

    public float getXpGrowth() {
        return xpGrowth;
    }

    public float getHeightM() {
        return height_m;
    }

    public float getPercentageMale() {
        return percentageMale;
    }

    public float getWeightKg() {
        return weight_kg;
    }

    public int getGeneration() {
        return generation;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    /**
     * Converts a type string into enum Type
     * @param s input string
     * @return Instance of Type enum corresponding to the input string s
     * 
     * @see Type
     */
    private Type stringToType(String s) {
        switch (s.toLowerCase()) {
            case "fire":
                return Type.FIRE;
            case "water":
                return Type.WATER;
            case "grass":
                return Type.GRASS;
            case "electric":
                return Type.ELECTRIC;
            case "psychic":
                return Type.PSYCHIC;
            case "ice":
                return Type.ICE;
            case "dragon":
                return Type.DRAGON;
            case "dark":
                return Type.DARK;
            case "fighting":
                return Type.FIGHTING;
            case "poison":
                return Type.POISON;
            case "ground":
                return Type.GROUND;
            case "flying":
                return Type.FLYING;
            case "bug":
                return Type.BUG;
            case "rock":
                return Type.ROCK;
            case "ghost":
                return Type.GHOST;
            case "steel":
                return Type.STEEL;
            case "normal":
                return Type.NORMAL;
            case "fairy":
                return Type.FAIRY;
            default:
                return Type.NONE;
        }
    }
}
