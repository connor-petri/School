import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class Photo {
    private String name;
    private String path;
    private LocalDateTime dateAdded;
    private long size;
    private Image img;
    private Image thumbnail;

    public Photo(String name, String path) {
        this.name = name;
        this.path = path;
        img = new ImageIcon(path).getImage();
        thumbnail = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        this.dateAdded = LocalDateTime.now();
    }

    public String getName() { return name; }
    public String getPath() { return path; }
    public LocalDateTime getDateAdded() { return dateAdded; }
    public long getSize() { return size; }

    public Image getImage() { return img; }
    public Image getThumbnail() { return thumbnail; }
}
