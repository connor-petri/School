import java.util.*;

/**
 * Sorting strategy that orders photos alphabetically by name in ascending order.
 *
 * @see ISortingStrategy
 * @see Photo
 */
public class SortByName implements ISortingStrategy {

    /**
     * Sorts the given list of photos alphabetically by name in ascending order.
     *
     * Precondition: photos != null.
     * Postcondition: The list is reordered in place alphabetically by photo name.
     *
     * @input photos the list of photos to sort
     * @return the sorted list of photos
     * @see Photo
     */
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, (p1, p2) -> p1.getName().compareTo(p2.getName()));
        return photos;
    }
}
