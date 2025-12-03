package src;
import java.util.*;

/**
 * An interface for classes that need to analyze Pokemon data.
 * 
 * @author Connor Petri
 */
public interface IAnalyzePokemon {
    /**
     * A function to gather all pokemon names into a HashSet
     * @param list of pokemon data
     * @return HashSet of Pokemon Names
     */
    HashSet<String> getAllCharacterNames(ArrayList<String> list);
}
