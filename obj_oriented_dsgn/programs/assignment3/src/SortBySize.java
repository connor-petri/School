import java.util.*;

public class SortBySize implements ISortingStrategy {
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, (p1, p2) -> Long.compare(p1.getSizeBytes(), p2.getSizeBytes()));
        return photos;
    }
}
