import java.util.*;

public class AnalyzePokemon implements IAnalyzePokemon {
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
