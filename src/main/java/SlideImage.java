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
            // String filepath = new File("").getAbsolutePath();
            // System.out.println(filepath);
            // filepath = filepath + "/src/images/"+fileName;
            image = ImageIO.read(path);

            ImageObserver observer = new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            };

            image = ImageIO.read(path);
            width = image.getWidth(observer);
            System.out.println(width);
            height = image.getHeight(observer);
            picture = new JLabel(
                    new ImageIcon(
                            image.getScaledInstance((int)width - 10, (int)height - 10, 1)
                    )
            );
            setSize((int)width, (int)height);
            add(picture);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        // Listener for when the image is resized
    }

    public void addCaption(String captionText)
    {
        caption = new JLabel(captionText);
        refreshImg();
        add(caption);
    }
}
