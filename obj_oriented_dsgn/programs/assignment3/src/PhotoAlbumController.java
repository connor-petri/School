import java.util.ArrayList;

public class PhotoAlbumController {
    private PhotoAlbumModel model = new PhotoAlbumModel();
    private IPhotoAlbumIterator it = model.iterator();

    public PhotoAlbumController() {};

    public ArrayList<Photo> getPhotos() {
        return model.getPhotos();
    }

    public Photo getSelectedPhoto() {
        return it.current();
    }

    public void addPhoto(String name, String filePath) {
        boolean empty = !it.hasNext();
        model.addPhoto(new Photo(name, filePath));
        if (empty) {
            it.next();
        }
    }

    public void deleteSelectedPhoto() {
        model.deletePhoto(it.current());
    }

    public Photo nextPhoto() {
        if (!it.hasNext()) { return null; }
        return it.next();
    }

    public Photo prevPhoto() {
        if (!it.hasPrevious()) { return null; }
        return it.previous();
    }

    public void sortByName() {
        model.sortByName();
    }

    public void sortByDate() {
        model.sortByDate();
    }

    public void sortBySize() {
        model.sortBySize();
    }
}
