import java.util.ArrayList;

/**
 * Controller in the MVC architecture that mediates between the model and view.
 *
 * @see PhotoAlbumModel
 * @see PhotoAlbumView
 * @see IPhotoAlbumIterator
 */
public class PhotoAlbumController {
    private PhotoAlbumModel model = new PhotoAlbumModel();
    private IPhotoAlbumIterator it = model.iterator();

    /**
     * Constructs a PhotoAlbumController with a default model and iterator.
     *
     * Precondition: None.
     * Postcondition: The model and iterator are initialized and ready for use.
     *
     * @see PhotoAlbumModel
     */
    public PhotoAlbumController() {};

    /**
     * Returns all photos currently in the album.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the list of all photos in the model
     * @see PhotoAlbumModel
     */
    public ArrayList<Photo> getPhotos() {
        return model.getPhotos();
    }

    /**
     * Returns the photo at the current iterator position.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the currently selected Photo, or null if the album is empty
     * @see IPhotoAlbumIterator
     */
    public Photo getSelectedPhoto() {
        return it.current();
    }

    /**
     * Adds a new photo to the album and resets the iterator.
     *
     * Precondition: name != null and filePath points to a valid image file.
     * Postcondition: A new Photo is added to the model and the iterator is refreshed.
     *
     * @input name display name for the photo
     * @input filePath absolute file path to the image
     * @return none
     * @see Photo
     * @see PhotoAlbumModel
     */
    public void addPhoto(String name, String filePath) {
        model.addPhoto(new Photo(name, filePath));
        it = model.iterator();
    }

    /**
     * Deletes the currently selected photo and resets the iterator.
     *
     * Precondition: A photo is currently selected (it.current() != null).
     * Postcondition: The selected photo is removed from the model and the iterator is refreshed.
     *
     * @input none
     * @return none
     * @see PhotoAlbumModel
     * @see IPhotoAlbumIterator
     */
    public void deleteSelectedPhoto() {
        model.deletePhoto(it.current());
        it = model.iterator();
    }

    /**
     * Advances the iterator and returns the next photo.
     *
     * Precondition: None.
     * Postcondition: The iterator is advanced if a next photo exists.
     *
     * @input none
     * @return the next Photo, or null if no next photo exists
     * @see IPhotoAlbumIterator
     */
    public Photo nextPhoto() {
        if (!it.hasNext()) { return null; }
        return it.next();
    }

    /**
     * Moves the iterator backward and returns the previous photo.
     *
     * Precondition: None.
     * Postcondition: The iterator is moved back if a previous photo exists.
     *
     * @input none
     * @return the previous Photo, or null if no previous photo exists
     * @see IPhotoAlbumIterator
     */
    public Photo prevPhoto() {
        if (!it.hasPrevious()) { return null; }
        return it.previous();
    }

    /**
     * Sorts the album by photo name and resets the iterator.
     *
     * Precondition: None.
     * Postcondition: Photos are sorted alphabetically by name and the iterator is refreshed.
     *
     * @input none
     * @return none
     * @see SortByName
     * @see PhotoAlbumModel
     */
    public void sortByName() {
        model.sortByName();
        it =  model.iterator();
    }

    /**
     * Sorts the album by date added and resets the iterator.
     *
     * Precondition: None.
     * Postcondition: Photos are sorted by date added and the iterator is refreshed.
     *
     * @input none
     * @return none
     * @see SortByDate
     * @see PhotoAlbumModel
     */
    public void sortByDate() {
        model.sortByDate();
        it =  model.iterator();
    }

    /**
     * Sorts the album by file size and resets the iterator.
     *
     * Precondition: None.
     * Postcondition: Photos are sorted by file size and the iterator is refreshed.
     *
     * @input none
     * @return none
     * @see SortBySize
     * @see PhotoAlbumModel
     */
    public void sortBySize() {
        model.sortBySize();
        it =  model.iterator();
    }
}
