import java.util.List;

/**
 * Strategy interface for sorting a list of photos.
 *
 * @see Photo
 * @see SortByName
 * @see SortByDate
 * @see SortBySize
 */
public interface ISortingStrategy {

    /**
     * Sorts the given list of photos according to the implementing strategy.
     *
     * Precondition: photos != null.
     * Postcondition: The list is reordered in place and returned.
     *
     * @input photos the list of photos to sort
     * @return the sorted list of photos
     * @see Photo
     */
    List<Photo> sort(List<Photo> photos);
}