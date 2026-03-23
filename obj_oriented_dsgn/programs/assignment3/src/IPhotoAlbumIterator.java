/**
 * Defines an iterator for navigating photos in a photo album.
 *
 * @see Photo
 * @see PhotoAlbumModel
 */
public interface IPhotoAlbumIterator {

    /**
     * Checks whether there is a next photo in the album.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return true if a next photo exists, false otherwise
     * @see IPhotoAlbumIterator
     */
    boolean hasNext();

    /**
     * Checks whether there is a previous photo in the album.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return true if a previous photo exists, false otherwise
     * @see IPhotoAlbumIterator
     */
    boolean hasPrevious();

    /**
     * Returns the photo at the current iterator position.
     *
     * Precondition: The iterator has been initialized.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the current Photo, or null if the album is empty
     * @see Photo
     */
    Photo current();

    /**
     * Advances the iterator and returns the next photo.
     *
     * Precondition: hasNext() returns true.
     * Postcondition: The iterator position is advanced by one.
     *
     * @input none
     * @return the next Photo in the album
     * @see Photo
     */
    Photo next();

    /**
     * Moves the iterator backward and returns the previous photo.
     *
     * Precondition: hasPrevious() returns true.
     * Postcondition: The iterator position is moved back by one.
     *
     * @input none
     * @return the previous Photo in the album
     * @see Photo
     */
    Photo previous();
}
