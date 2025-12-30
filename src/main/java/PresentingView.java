import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class PresentingView {

    public static void main(String[] args){
        JFrame window = new JFrame("Presentation view");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int)screenSize.getWidth();
        int screenHeight = (int)screenSize.getHeight();
        window.setSize(screenWidth,screenHeight);

        window.setVisible(true);
        window.setLayout(null);
        JPanel panel = new JPanel();
        panel.setLocation(0,0);
        panel.setSize(100,100);

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(new JButton("HELLO!"));
        window.add(panel);

        // This grabs a screenshot of the JPanel panel
        Dimension d = panel.getSize();
        BufferedImage image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        panel.print( g2d );
        g2d.dispose();
        String filepath = new File("").getAbsolutePath();
        filepath = filepath + "/src/images/Slide.jpg";

        try {
            System.out.println(filepath);
            ImageIO.write(image, "jpg", new File(filepath));
            System.out.println("DONE!");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        // End of screenshot code
    }
}