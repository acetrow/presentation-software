import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;

public class SlideImage extends DraggablePanel {
    private JLabel caption;
    private Image image;
    private JLabel picture;

    private double width;
    private double height;
    private File file;
    
    public double getImageWidth() {
        return width;
    }

    public double getImageHeight() {
        return height;
    }

    public File getFile() {
        return file;
    }

    void refreshImg()
    {
        remove(picture);
        picture = new JLabel(
                new ImageIcon(
                        image.getScaledInstance((int)width, (int)height, 1)
                )
        );
        add(picture);
    }
    public void growX(int x)
    {
        double newWidth = width + x;
        if (newWidth > 50) {
            width = newWidth;
            setSize((int) width, (int) height);
            refreshImg();
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            setBorder(null);
        }
    }

    public void growY(int y)
    {
        double newHeight = height + y;
        if (newHeight > 50) {
            height = newHeight;
            setSize((int) width, (int) height);
            refreshImg();
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            setBorder(null);
        }
    }

    public SlideImage(int newWidth, int newHeight, File path)
    {
        this.file = path;
        try {
            image = ImageIO.read(path);
            
            if (image == null) {
                throw new Exception("Failed to load image from: " + path.getAbsolutePath());
            }

            ImageObserver observer = new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            };

            // Use provided dimensions if valid, otherwise use image's natural size
            if (newWidth > 0 && newHeight > 0) {
                width = newWidth;
                height = newHeight;
            } else {
                width = image.getWidth(observer);
                height = image.getHeight(observer);
            }
            
            picture = new JLabel(
                    new ImageIcon(
                            image.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH)
                    )
            );
            setSize((int)width, (int)height);
            setLayout(new BorderLayout());
            add(picture, BorderLayout.CENTER);
        }
        catch (Exception e)
        {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
            // Create a placeholder label if image fails to load
            JLabel errorLabel = new JLabel("Image not found");
            errorLabel.setForeground(Color.RED);
            add(errorLabel);
            width = 200;
            height = 200;
            setSize((int)width, (int)height);
        }
    }

    public void addCaption(String captionText)
    {
        caption = new JLabel(captionText);
        refreshImg();
        add(caption);
    }
}
