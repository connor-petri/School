import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * View in the MVC architecture that provides the Swing-based GUI for the Photo Album.
 *
 * @see PhotoAlbumController
 * @see PhotoAlbumModel
 */
public class PhotoAlbumView {
    private static final int THUMB_SIZE = 30;
    private static final int IMG_WIDTH = 1200;
    private static final int IMG_HEIGHT = 800;
    private static final int ROW_HEIGHT = 35;
    private static final int PADDING = 5;

    private final PhotoAlbumController controller = new PhotoAlbumController();

    /**
     * Creates a GridBagConstraints helper for positioning components.
     *
     * Precondition: x >= 0 and y >= 0.
     * Postcondition: No state is modified.
     *
     * @input x the column position
     * @input y the row position
     * @return a configured GridBagConstraints instance
     * @see PhotoAlbumView
     */
    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    /**
     * Constructs a PhotoAlbumView with a default controller.
     *
     * Precondition: None.
     * Postcondition: The view is initialized and ready to be run.
     *
     * @see PhotoAlbumController
     */
    public PhotoAlbumView() {}

    /**
     * Main application frame containing the photo list, image viewer, and action buttons.
     *
     * Precondition: PhotoAlbumView's controller is initialized.
     * Postcondition: The frame is laid out with a ListPanel, ImageViewPanel, and button bar.
     *
     * @see ListPanel
     * @see ImageViewPanel
     * @see AddPhotoDialog
     */
    private class PhotoAlbumFrame extends JFrame {
        private static final int  FRAME_WIDTH = 1400;
        private static final int FRAME_HEIGHT = 900;

        /**
         * Constructs the main frame with all UI components and action listeners.
         *
         * Precondition: None.
         * Postcondition: The frame is fully assembled with panels and buttons wired to controller actions.
         *
         * @see PhotoAlbumController
         */
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
            JButton deleteButton = new JButton("Delete Photo");
            deleteButton.addActionListener(e -> {
                controller.deleteSelectedPhoto();
                repaint();
            });
            JButton prevButton = new JButton("Previous");
            prevButton.addActionListener(e -> {
                controller.prevPhoto();
                repaint();
            });
            JButton nextButton = new JButton("Next");
            nextButton.addActionListener(e -> {
                controller.nextPhoto();
                repaint();
            });
            JButton sortNameButton = new JButton("Sort by Name");
            sortNameButton.addActionListener(e -> {
                controller.sortByName();
                repaint();
            });
            JButton sortDateButton = new JButton("Sort by Date");
            sortDateButton.addActionListener(e -> {
                controller.sortByDate();
                repaint();
            });
            JButton sortSizeButton = new JButton("Sort by Size");
            sortSizeButton.addActionListener(e -> {
                controller.sortBySize();
                repaint();
            });

            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(prevButton);
            buttonPanel.add(nextButton);
            buttonPanel.add(sortNameButton);
            buttonPanel.add(sortDateButton);
            buttonPanel.add(sortSizeButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    /**
     * Side panel that displays a scrollable list of photo thumbnails and names.
     *
     * Precondition: Controller is initialized with a valid model.
     * Postcondition: The currently selected photo is highlighted in blue.
     *
     * @see Photo
     * @see PhotoAlbumController
     */
    private class ListPanel extends JPanel {

        /**
         * Returns the preferred size for the list panel.
         *
         * Precondition: None.
         * Postcondition: No state is modified.
         *
         * @input none
         * @return a Dimension of 150x300
         * @see ListPanel
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 300);
        }

        /**
         * Paints the photo list with thumbnails, names, and selection highlighting.
         *
         * Precondition: Graphics context is valid.
         * Postcondition: The photo list is rendered to the panel with the selected photo highlighted.
         *
         * @input g the Graphics context for painting
         * @return none
         * @see Photo
         */
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

    /**
     * Modal dialog for adding a new photo to the album via name entry and file browsing.
     *
     * Precondition: A parent Frame is provided for modality.
     * Postcondition: On confirmation, a new photo is added to the album through the controller.
     *
     * @see PhotoAlbumController
     * @see Photo
     */
    private class AddPhotoDialog extends JDialog {
        private JTextField nameField;
        private JTextField filePathField;

        /**
         * Constructs the Add Photo dialog with name/path fields, browse, OK, and cancel buttons.
         *
         * Precondition: parent != null.
         * Postcondition: The dialog is laid out and centered relative to the parent frame.
         *
         * @input parent the parent Frame for dialog modality
         * @see PhotoAlbumController
         */
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
                    nameField.setText(fc.getSelectedFile().getName().split("\\.")[0]);
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

    /**
     * Panel that displays the full-size image of the currently selected photo.
     *
     * Precondition: Controller is initialized with a valid model.
     * Postcondition: The selected photo's full-size image is rendered when available.
     *
     * @see Photo
     * @see PhotoAlbumController
     */
    private class ImageViewPanel extends JPanel {

        /**
         * Returns the preferred size for the image view panel.
         *
         * Precondition: None.
         * Postcondition: No state is modified.
         *
         * @input none
         * @return a Dimension of 150x150
         * @see ImageViewPanel
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 150);
        }

        /**
         * Paints the full-size image of the currently selected photo.
         *
         * Precondition: Graphics context is valid.
         * Postcondition: The selected photo's image is rendered, or nothing if no photo is selected.
         *
         * @input g the Graphics context for painting
         * @return none
         * @see Photo
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Photo selectedPhoto = controller.getSelectedPhoto();
            if (selectedPhoto == null) { return; }
            g2d.drawImage(selectedPhoto.getImage(), PADDING, PADDING, IMG_WIDTH, IMG_HEIGHT, this);
        }
    }

    /**
     * Launches the photo album GUI by creating and displaying the main frame.
     *
     * Precondition: None.
     * Postcondition: The PhotoAlbumFrame is visible and the application is running.
     *
     * @input none
     * @return none
     * @see PhotoAlbumFrame
     */
    public void run() {
        PhotoAlbumFrame frame = new PhotoAlbumFrame();
        frame.setVisible(true);
    }
}
