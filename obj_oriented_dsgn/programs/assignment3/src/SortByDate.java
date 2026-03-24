import java.util.*;

public class SortByDate implements ISortingStrategy {
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, (p1, p2) -> p1.getDateAdded().compareTo(p2.getDateAdded()));
        return photos;
    }
}
