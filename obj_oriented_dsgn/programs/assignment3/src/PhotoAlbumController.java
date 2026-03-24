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
        model.addPhoto(new Photo(name, filePath));
        it = model.iterator();
    }

    public void deleteSelectedPhoto() {
        model.deletePhoto(it.current());
        it = model.iterator();
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
        it =  model.iterator();
    }

    public void sortByDate() {
        model.sortByDate();
        it =  model.iterator();
    }

    public void sortBySize() {
        model.sortBySize();
        it =  model.iterator();
    }
}
