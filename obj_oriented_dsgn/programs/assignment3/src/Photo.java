import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;

public class Photo {
    private String name;
    private String path;
    private LocalDateTime dateAdded;
    private long size_bytes;
    private Image img;
    private Image thumbnail;

    public Photo(String name, String path) {
        this.name = name;
        this.path = path;
        dateAdded = LocalDateTime.now();
        size_bytes = new File(path).length();
        img = new ImageIcon(path).getImage();
        thumbnail = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    }

    public String getName() { return name; }
    public String getPath() { return path; }
    public LocalDateTime getDateAdded() { return dateAdded; }
    public long getSizeBytes() { return size_bytes; }

    public Image getImage() { return img; }
    public Image getThumbnail() { return thumbnail; }
}
