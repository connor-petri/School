package src;
import java.util.*;

/**
 * Provides methods for analyzing Pokemon data
 * @author Connor Petri
 * @see Pokemon
 * @see ReadData
 */
public class AnalyzePokemon implements IAnalyzePokemon {
    /**
     * Retrieves the names from each pokemon given a 2 dimensional ArrayList of pokemon data
     * @param list The 2d ArrayList<String> of pokemon data
     * @return HashSet<String> of Pokemon Names in order of retrieval from list
     * 
     * @see ArrayList
     * @see Pokemon
     * @see ReadData
     */
    public HashSet<String> getAllCharacterNames(ArrayList<String> list) {
        HashSet<String> set = new HashSet<String>();
        for (String row : list) {
            // Skip header row
            if (row.startsWith("abilities")) {
                continue;
            }
                        
            // Uses same regex as searchByName()
            String[] columns = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                        
            // Ensure column exists
            if (columns.length > 30) {
                String pokemonName = columns[30].trim();

                // Remove quotes
                pokemonName = pokemonName.replaceAll("\"", "");

                set.add(pokemonName);
            }
        }

        return set;
    }
}
