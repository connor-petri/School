import java.util.*;

public class PhotoAlbumModel {
    public class Iterator implements IPhotoAlbumIterator {
        private Photo current;

        public boolean hasNext() {
            if (photos.isEmpty()) { return false; }

            if (current == null) { return true; }

            int i = photos.indexOf(current);
            return i < photos.size() - 1; // Check if i is larger than the index
        }

        public boolean hasPrevious() {
            if (current == null) { return false; }
            return photos.indexOf(current) > 0;
        }

        public Photo next() {
            if (current == null) {
                current = photos.getFirst();
            } else {
                current = photos.get(photos.indexOf(current) + 1);
            }
            return current;
        }

        public Photo previous() {
            current = photos.get(photos.indexOf(current) - 1);
            return current;
        }

        public Photo current() {
            return current;
        }
    }

    private final ArrayList<Photo> photos = new ArrayList<>();
    private ISortingStrategy sortingStrat = new SortByName();

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
