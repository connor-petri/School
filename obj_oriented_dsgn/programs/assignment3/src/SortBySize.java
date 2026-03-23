import java.util.*;

public class SortBySize implements SortingStrategy {
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, (p1, p2) -> Long.compare(p1.getSize(), p2.getSize()));
        return photos;
    }
}
