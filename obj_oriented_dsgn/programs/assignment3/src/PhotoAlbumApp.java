/**
 * Entry point for the Photo Album application.
 *
 * @see PhotoAlbumView
 */
public class PhotoAlbumApp {

    /**
     * Launches the Photo Album application.
     *
     * Precondition: None.
     * Postcondition: A new PhotoAlbumView is created and running.
     *
     * @input args command-line arguments (unused)
     * @return none
     * @see PhotoAlbumView
     */
    public static void main(String[] args) {
        new PhotoAlbumView().run();
    }
}
