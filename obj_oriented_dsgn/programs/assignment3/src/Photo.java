import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;

/**
 * Represents a photo with its metadata, full-size image, and thumbnail.
 *
 * @see PhotoAlbumModel
 * @see PhotoAlbumController
 */
public class Photo {
    private String name;
    private String path;
    private LocalDateTime dateAdded;
    private long size_bytes;
    private Image img;
    private Image thumbnail;

    /**
     * Constructs a Photo from a name and file path, loading image data and metadata.
     *
     * Precondition: name != null and path points to a readable image file.
     * Postcondition: All fields are initialized including a 30x30 scaled thumbnail.
     *
     * @input name display name for the photo
     * @input path absolute file path to the image
     * @see Photo
     */
    public Photo(String name, String path) {
        this.name = name;
        this.path = path;
        dateAdded = LocalDateTime.now();
        size_bytes = new File(path).length();
        img = new ImageIcon(path).getImage();
        thumbnail = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    }

    /**
     * Returns the display name of this photo.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the photo name
     * @see Photo
     */
    public String getName() { return name; }

    /**
     * Returns the file path of this photo.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the absolute file path
     * @see Photo
     */
    public String getPath() { return path; }

    /**
     * Returns the date and time this photo was added to the album.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the date added as a LocalDateTime
     * @see Photo
     */
    public LocalDateTime getDateAdded() { return dateAdded; }

    /**
     * Returns the file size of this photo in bytes.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the file size in bytes
     * @see Photo
     */
    public long getSizeBytes() { return size_bytes; }

    /**
     * Returns the full-size image.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the full-size Image object
     * @see Photo
     */
    public Image getImage() { return img; }

    /**
     * Returns a 30x30 scaled thumbnail of this photo.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return the thumbnail Image object
     * @see Photo
     */
    public Image getThumbnail() { return thumbnail; }
}
