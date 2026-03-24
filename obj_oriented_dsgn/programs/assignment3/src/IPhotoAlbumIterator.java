public interface IPhotoAlbumIterator {
    boolean hasNext();
    boolean hasPrevious();
    Photo current();
    Photo next();
    Photo previous();
}
