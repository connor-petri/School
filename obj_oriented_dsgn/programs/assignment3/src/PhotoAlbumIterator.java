public interface PhotoAlbumIterator {
    boolean hasNext();
    boolean hasPrevious();
    Photo current();
    Photo next();
    Photo previous();
}
