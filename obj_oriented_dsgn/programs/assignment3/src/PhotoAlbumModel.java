import java.util.*;

/**
 * Model in the MVC architecture that manages the photo collection and sorting state.
 *
 * @see PhotoAlbumController
 * @see Photo
 * @see ISortingStrategy
 */
public class PhotoAlbumModel {

    /**
     * Inner iterator class that provides sequential traversal over the photo list.
     *
     * Precondition: The enclosing PhotoAlbumModel's photo list must be initialized.
     * Postcondition: Allows forward and backward navigation through the photo list.
     *
     * @see IPhotoAlbumIterator
     * @see Photo
     */
    public class Iterator implements IPhotoAlbumIterator {
        private Photo current;

        /**
         * Constructs an Iterator positioned at the first photo in the album.
         *
         * Precondition: None.
         * Postcondition: current is set to the first photo, or null if the album is empty.
         *
         * @see PhotoAlbumModel
         */
        public Iterator() {
            if (!photos.isEmpty()) {
                current = photos.getFirst();
            }
        }

        /**
         * Checks whether there is a next photo after the current position.
         *
         * Precondition: None.
         * Postcondition: No state is modified.
         *
         * @input none
         * @return true if a next photo exists, false otherwise
         * @see IPhotoAlbumIterator
         */
        public boolean hasNext() {
            if (photos.isEmpty()) { return false; }

            if (current == null) { return true; }

            int i = photos.indexOf(current);
            return i < photos.size() - 1; // Check if i is larger than the index
        }

        /**
         * Checks whether there is a previous photo before the current position.
         *
         * Precondition: None.
         * Postcondition: No state is modified.
         *
         * @input none
         * @return true if a previous photo exists, false otherwise
         * @see IPhotoAlbumIterator
         */
        public boolean hasPrevious() {
            if (current == null) { return false; }
            return photos.indexOf(current) > 0;
        }

        /**
         * Advances the iterator and returns the next photo.
         *
         * Precondition: hasNext() returns true.
         * Postcondition: current is updated to the next photo in the list.
         *
         * @input none
         * @return the next Photo, or null if no next photo exists
         * @see Photo
         */
        public Photo next() {
            if (!hasNext()) { return null; }

            if (current == null) {
                current = photos.getFirst();
            } else {
                current = photos.get(photos.indexOf(current) + 1);
            }
            return current;
        }

        /**
         * Moves the iterator backward and returns the previous photo.
         *
         * Precondition: hasPrevious() returns true.
         * Postcondition: current is updated to the previous photo in the list.
         *
         * @input none
         * @return the previous Photo
         * @see Photo
         */
        public Photo previous() {
            current = photos.get(photos.indexOf(current) - 1);
            return current;
        }

        /**
         * Returns the photo at the current iterator position.
         *
         * Precondition: None.
         * Postcondition: No state is modified.
         *
         * @input none
         * @return the current Photo, or null if the album is empty
         * @see Photo
         */
        public Photo current() {
            return current;
        }
    }

    private final ArrayList<Photo> photos = new ArrayList<>();
    private ISortingStrategy sortingStrat = new SortByDate();

    /**
     * Adds a photo to the album and re-sorts using the current sorting strategy.
     *
     * Precondition: p != null.
     * Postcondition: The photo is added and the list is re-sorted.
     *
     * @input p the Photo to add
     * @return none
     * @see Photo
     * @see ISortingStrategy
     */
    public void addPhoto(Photo p) {
        photos.add(p);
        sortingStrat.sort(photos);
    }

    /**
     * Removes a photo from the album.
     *
     * Precondition: p != null.
     * Postcondition: The photo is removed from the list if present.
     *
     * @input p the Photo to delete
     * @return true if the photo was found and removed, false otherwise
     * @see Photo
     */
    public boolean deletePhoto(Photo p) {
        return photos.remove(p);
    }

    /**
     * Constructs an empty PhotoAlbumModel with a default sort-by-date strategy.
     *
     * Precondition: None.
     * Postcondition: The model is initialized with an empty photo list and SortByDate strategy.
     *
     * @see SortByDate
     */
    public PhotoAlbumModel() {}

    /**
     * Creates and returns a new iterator over the photo list.
     *
     * Precondition: None.
     * Postcondition: A fresh Iterator is returned, positioned at the first photo.
     *
     * @input none
     * @return a new Iterator for the photo list
     * @see Iterator
     */
    public Iterator iterator() { return new Iterator(); }

    /**
     * Returns the internal list of photos.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the list of all photos in the album
     * @see Photo
     */
    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    /**
     * Sorts the album alphabetically by photo name.
     *
     * Precondition: None.
     * Postcondition: The sorting strategy is set to SortByName and the photo list is re-sorted.
     *
     * @input none
     * @return the sorted list of photos
     * @see SortByName
     */
    public ArrayList<Photo> sortByName() {
        sortingStrat = new SortByName();
        sortingStrat.sort(photos);
        return photos;
    }

    /**
     * Sorts the album by file size.
     *
     * Precondition: None.
     * Postcondition: The sorting strategy is set to SortBySize and the photo list is re-sorted.
     *
     * @input none
     * @return the sorted list of photos
     * @see SortBySize
     */
    public ArrayList<Photo> sortBySize() {
        sortingStrat = new SortBySize();
        sortingStrat.sort(photos);
        return photos;
    }

    /**
     * Sorts the album by date added.
     *
     * Precondition: None.
     * Postcondition: The sorting strategy is set to SortByDate and the photo list is re-sorted.
     *
     * @input none
     * @return the sorted list of photos
     * @see SortByDate
     */
    public ArrayList<Photo> sortByDate() {
        sortingStrat = new SortByDate();
        sortingStrat.sort(photos);
        return photos;
    }


}
