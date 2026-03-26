import java.util.*;

/**
 * Sorting strategy that orders photos by file size in ascending order (smallest first).
 *
 * @see ISortingStrategy
 * @see Photo
 */
public class SortBySize implements ISortingStrategy {

    /**
     * Sorts the given list of photos by file size in ascending order.
     *
     * Precondition: photos != null.
     * Postcondition: The list is reordered in place with the smallest file first.
     *
     * @input photos the list of photos to sort
     * @return the sorted list of photos
     * @see Photo
     */
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, (p1, p2) -> Long.compare(p1.getSizeBytes(), p2.getSizeBytes()));
        return photos;
    }
}
