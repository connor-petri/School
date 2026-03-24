import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PhotoAlbumView {
    private static final int THUMB_SIZE = 30;
    private static final int IMG_WIDTH = 1200;
    private static final int IMG_HEIGHT = 800;
    private static final int ROW_HEIGHT = 35;
    private static final int PADDING = 5;

    private final PhotoAlbumController controller = new PhotoAlbumController();

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    public PhotoAlbumView() {}

    private class PhotoAlbumFrame extends JFrame {
        private static final int  FRAME_WIDTH = 1400;
        private static final int FRAME_HEIGHT = 900;

        public PhotoAlbumFrame() {
            setTitle("Photo Album");
            setSize(FRAME_WIDTH, FRAME_HEIGHT);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            add(new ListPanel(), BorderLayout.WEST);
            add(new ImageViewPanel(), BorderLayout.CENTER);

            // Buttons
            JPanel buttonPanel = new JPanel();

            JButton addButton = new JButton("Add Photo");
            addButton.addActionListener(e -> {
                AddPhotoDialog addPhotoDialog = new AddPhotoDialog(this);
                addPhotoDialog.setVisible(true);
            });
            buttonPanel.add(addButton);

            JButton deleteButton = new JButton("Delete Photo");
            deleteButton.addActionListener(e -> {
                controller.deleteSelectedPhoto();
                repaint();
            });
            buttonPanel.add(deleteButton);

            JButton prevButton = new JButton("Previous");
            prevButton.addActionListener(e -> {
                controller.prevPhoto();
                repaint();
            });
            buttonPanel.add(prevButton);

            JButton nextButton = new JButton("Next");
            nextButton.addActionListener(e -> {
                controller.nextPhoto();
                repaint();
            });
            buttonPanel.add(nextButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    private class ListPanel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 300);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Photo selectedPhoto = controller.getSelectedPhoto();

            int y = 20;
            for (Photo p : controller.getPhotos()) {
                if (p.getPath().equals(selectedPhoto.getPath())) {
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect(0, y - 15, getWidth(), ROW_HEIGHT);
                    g2d.setColor(Color.WHITE);
                } else {
                    g2d.setColor(Color.BLACK);
                }

                g2d.drawImage(p.getThumbnail(), PADDING, y - 12, THUMB_SIZE, THUMB_SIZE, this);
                int textX = PADDING + THUMB_SIZE + PADDING;
                int textY = y + (ROW_HEIGHT / 2) - 10;
                g2d.drawString(p.getName(), textX, textY);
                y += ROW_HEIGHT + PADDING;
            }
        }
    }

    private class AddPhotoDialog extends JDialog {
        private JTextField nameField;
        private JTextField filePathField;

        public AddPhotoDialog(Frame parent) {
            super(parent, "Add Photo", true);
            setSize(300, 200);
            nameField = new JTextField(20);
            filePathField = new JTextField(20);

            JButton browseButton = new JButton("Browse");
            browseButton.addActionListener(e -> {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    filePathField.setText(fc.getSelectedFile().getAbsolutePath());
                }
            });

            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                if (!nameField.getText().isEmpty() && !filePathField.getText().isEmpty()) {
                    controller.addPhoto(nameField.getText(), filePathField.getText());
                    parent.repaint();
                    dispose();
                }
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dispose());

            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridBagLayout());
            fieldPanel.add(nameField, gbc(0, 0));
            fieldPanel.add(filePathField,  gbc(0, 1));
            fieldPanel.add(browseButton, gbc(1, 1));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            setLayout(new BorderLayout());
            add(fieldPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
            pack();
            setLocationRelativeTo(parent);
        }
    }

    private class ImageViewPanel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 150);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Photo selectedPhoto = controller.getSelectedPhoto();
            if (selectedPhoto == null) { return; }
            g2d.drawImage(selectedPhoto.getImage(), PADDING, PADDING, IMG_WIDTH, IMG_HEIGHT, this);
        }
    }

    public void run() {
        PhotoAlbumFrame frame = new PhotoAlbumFrame();
        frame.setVisible(true);
    }
}
