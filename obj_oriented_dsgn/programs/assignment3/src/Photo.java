import java.util.Date;

public class Photo {
    private String name;
    private String path;
    private Date dateAdded;
    private long size;

    public Photo(String name, String path, Date dateAdded, long size) {
        this.name = name;
        this.path = path;
        this.dateAdded = dateAdded;
        this.size = size;
    }

    public String getName() { return name; }
    public String getPath() { return path; }
    public Date getDateAdded() { return dateAdded; }
    public long getSize() { return size; }

    public void setName(String name) { this.name = name; }
    public void setPath(String path) { this.path = path; }
    public void setDateAdded(Date dateAdded) { this.dateAdded = dateAdded; }
    public void setSize(long size) { this.size = size; }
}
