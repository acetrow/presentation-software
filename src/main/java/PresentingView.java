import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class PresentingView {
    private JFrame window;
    private Slide slide;
    private EditMenu editMenu;
    private JPanel slidePanel;

    public PresentingView(Slide slide, EditMenu editMenu) {
        this.slide = slide;
        this.editMenu = editMenu;
    }

    public void show() {
        window = new JFrame("Presentation Mode - Press ESC to exit");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int)screenSize.getWidth();
        int screenHeight = (int)screenSize.getHeight();
        
        window.setSize(screenWidth, screenHeight);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setBackground(Color.BLACK);

        // Create a copy of the slide for presentation
        slidePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Scale the slide to fit the screen while maintaining aspect ratio
                Dimension slideSize = slide.getPreferredSize();
                double scaleX = (double) getWidth() / slideSize.width;
                double scaleY = (double) getHeight() / slideSize.height;
                double scale = Math.min(scaleX, scaleY);
                
                int scaledWidth = (int) (slideSize.width * scale);
                int scaledHeight = (int) (slideSize.height * scale);
                int x = (getWidth() - scaledWidth) / 2;
                int y = (getHeight() - scaledHeight) / 2;
                
                g2d.translate(x, y);
                g2d.scale(scale, scale);
                
                // Draw slide background
                g2d.setColor(slide.getBackground());
                g2d.fillRect(0, 0, slideSize.width, slideSize.height);
                
                // Draw slide border
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(0, 0, slideSize.width, slideSize.height);
                
                // Create a temporary graphics context for the slide
                // We need to render the slide and its children
                Graphics2D slideGraphics = (Graphics2D) g2d.create();
                slideGraphics.setClip(0, 0, slideSize.width, slideSize.height);
                
                // Paint the slide component
                slide.paintComponent(slideGraphics);
                slide.paintChildren(slideGraphics);
                
                slideGraphics.dispose();
                g2d.dispose();
            }
        };
        
        slidePanel.setBackground(Color.BLACK);
        window.add(slidePanel, BorderLayout.CENTER);

        // Add key listener to exit on ESC
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    window.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        window.setFocusable(true);
        window.requestFocus();

        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                // Return focus to edit menu when presentation closes
                if (editMenu != null) {
                    editMenu.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        window.setVisible(true);
        
        // Refresh the presentation view periodically to show updates
        Timer refreshTimer = new Timer(100, e -> slidePanel.repaint());
        refreshTimer.start();
    }
}