import java.util.*;

public class SortByName implements ISortingStrategy {
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, (p1, p2) -> p1.getName().compareTo(p2.getName()));
        return photos;
    }
}
