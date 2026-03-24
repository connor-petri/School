import java.util.*;

public class PhotoAlbumModel {
    public class Iterator implements IPhotoAlbumIterator {
        private int index = -1;

        public boolean hasNext() {
            try {
                photos.get(index + 1);
                return true;
            } catch (IndexOutOfBoundsException e) {
                return false;
            }
        }

        public boolean hasPrevious() {
            return index > 0;
        }

        public Photo next() {
            return photos.get(++index);
        }

        public Photo previous() {
            return photos.get(--index);
        }

        public Photo current() {
            if (photos.isEmpty()) { return null; }
            return photos.get(index);
        }
    }

    private final ArrayList<Photo> photos = new ArrayList<>();
    private ISortingStrategy sortingStrat = new SortBySize();

    public PhotoAlbumModel() {}

    public Iterator iterator() { return new Iterator(); }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo p) {
        photos.add(p);
        sortingStrat.sort(photos);
    }

    public boolean deletePhoto(Photo p) {
        return photos.remove(p);
    }

    public ArrayList<Photo> sortByName() {
        sortingStrat = new SortByName();
        sortingStrat.sort(photos);
        return photos;
    }

    public ArrayList<Photo> sortBySize() {
        sortingStrat = new SortBySize();
        sortingStrat.sort(photos);
        return photos;
    }

    public ArrayList<Photo> sortByDate() {
        sortingStrat = new SortByDate();
        sortingStrat.sort(photos);
        return photos;
    }


}
