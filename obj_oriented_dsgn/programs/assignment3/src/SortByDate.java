import java.util.*;

public class SortByDate implements ISortingStrategy {
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, (p1, p2) -> p2.getDateAdded().compareTo(p1.getDateAdded()));
        return photos;
    }
}
