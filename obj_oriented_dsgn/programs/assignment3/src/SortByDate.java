import java.util.*;

/**
 * Sorting strategy that orders photos by date added in descending order (newest first).
 *
 * @see ISortingStrategy
 * @see Photo
 */
public class SortByDate implements ISortingStrategy {

    /**
     * Sorts the given list of photos by date added in descending order.
     *
     * Precondition: photos != null.
     * Postcondition: The list is reordered in place with the most recently added photo first.
     *
     * @input photos the list of photos to sort
     * @return the sorted list of photos
     * @see Photo
     */
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, (p1, p2) -> p2.getDateAdded().compareTo(p1.getDateAdded()));
        return photos;
    }
}
