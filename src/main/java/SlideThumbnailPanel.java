import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Thumbnail panel that displays slide previews on the left side, similar to PowerPoint.
 * Clicking a thumbnail switches to that slide.
 */
public class SlideThumbnailPanel extends JPanel {
    private EditMenu editMenu;
    private JPanel thumbnailContainer;
    private ArrayList<ThumbnailButton> thumbnailButtons = new ArrayList<>();
    private int selectedIndex = -1;
    
    private static final int THUMBNAIL_WIDTH = 150;
    private static final int THUMBNAIL_HEIGHT = 100;
    private static final int THUMBNAIL_SPACING = 10;
    
    public SlideThumbnailPanel(EditMenu editMenu) {
        this.editMenu = editMenu;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 0));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Slides"));
        setBackground(new Color(240, 240, 240));
        
        thumbnailContainer = new JPanel();
        thumbnailContainer.setLayout(new BoxLayout(thumbnailContainer, BoxLayout.Y_AXIS));
        thumbnailContainer.setBackground(new Color(240, 240, 240));
        
        JScrollPane scrollPane = new JScrollPane(thumbnailContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Refresh all thumbnails from the current slide list
     */
    public void refreshThumbnails() {
        thumbnailContainer.removeAll();
        thumbnailButtons.clear();
        
        int slideCount = editMenu.getSlideCount();
        if (slideCount == 0) {
            return;
        }
        
        // Create a thumbnail for each slide
        for (int i = 0; i < slideCount; i++) {
            ThumbnailButton thumbBtn = new ThumbnailButton(i);
            thumbnailButtons.add(thumbBtn);
            thumbnailContainer.add(thumbBtn);
            thumbnailContainer.add(Box.createVerticalStrut(THUMBNAIL_SPACING));
        }
        
        // Add glue to push thumbnails to top
        thumbnailContainer.add(Box.createVerticalGlue());
        
        // Update selection
        setSelectedIndex(editMenu.getCurrentSlideIndex());
        
        revalidate();
        repaint();
    }
    
    /**
     * Update the selected thumbnail highlight
     */
    public void setSelectedIndex(int index) {
        if (selectedIndex >= 0 && selectedIndex < thumbnailButtons.size()) {
            thumbnailButtons.get(selectedIndex).setSelected(false);
        }
        selectedIndex = index;
        if (selectedIndex >= 0 && selectedIndex < thumbnailButtons.size()) {
            thumbnailButtons.get(selectedIndex).setSelected(true);
        }
        repaint();
    }
    
    /**
     * Individual thumbnail button that displays a slide preview
     */
    private class ThumbnailButton extends JButton {
        private int slideIndex;
        private boolean isSelected = false;
        private BufferedImage thumbnailImage;
        
        public ThumbnailButton(int slideIndex) {
            this.slideIndex = slideIndex;
            setPreferredSize(new Dimension(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT));
            setMaximumSize(new Dimension(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT));
            setMinimumSize(new Dimension(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            setContentAreaFilled(false);
            setFocusPainted(false);
            
            // Generate thumbnail
            generateThumbnail();
            
            // Click handler
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    editMenu.goToSlide(slideIndex);
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }
        
        private void generateThumbnail() {
            // Create a small preview of the slide
            thumbnailImage = new BufferedImage(THUMBNAIL_WIDTH - 10, THUMBNAIL_HEIGHT - 30, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = thumbnailImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // Get slide snapshot for this index
            EditMenu.SlideSnapshot snapshot = editMenu.getSlideSnapshot(slideIndex);
            if (snapshot == null) {
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, thumbnailImage.getWidth(), thumbnailImage.getHeight());
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
                String text = "Slide " + (slideIndex + 1);
                FontMetrics fm = g2d.getFontMetrics();
                int x = (thumbnailImage.getWidth() - fm.stringWidth(text)) / 2;
                int y = thumbnailImage.getHeight() / 2;
                g2d.drawString(text, x, y);
            } else {
                // Draw background
                g2d.setColor(snapshot.background);
                g2d.fillRect(0, 0, thumbnailImage.getWidth(), thumbnailImage.getHeight());
                
                // Draw border
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(0, 0, thumbnailImage.getWidth() - 1, thumbnailImage.getHeight() - 1);
                
                // Scale factor for positioning elements
                double scaleX = (double) thumbnailImage.getWidth() / 960; // Assuming slide width ~960
                double scaleY = (double) thumbnailImage.getHeight() / 540; // Assuming slide height ~540
                
                // Draw text boxes (scaled and simplified)
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 7));
                for (EditMenu.SlideSnapshot.TextBoxSnapshot tb : snapshot.textBoxes) {
                    int x = (int)(tb.x * scaleX);
                    int y = (int)(tb.y * scaleY);
                    int w = (int)(tb.width * scaleX);
                    int h = (int)(tb.height * scaleY);
                    
                    // Draw text box outline
                    g2d.setColor(new Color(tb.colorRed, tb.colorGreen, tb.colorBlue));
                    g2d.drawRect(x, y, Math.min(w, thumbnailImage.getWidth() - x - 1), Math.min(h, thumbnailImage.getHeight() - y - 1));
                    
                    // Draw text content (first line only)
                    if (!tb.content.isEmpty()) {
                        String[] lines = tb.content.split("\n");
                        String firstLine = lines[0];
                        if (firstLine.length() > 15) firstLine = firstLine.substring(0, 15) + "...";
                        g2d.setColor(new Color(tb.colorRed, tb.colorGreen, tb.colorBlue));
                        g2d.drawString(firstLine, x + 2, y + 8);
                    }
                }
                
                // Draw image indicators
                g2d.setColor(Color.BLUE);
                for (EditMenu.SlideSnapshot.ImageSnapshot img : snapshot.images) {
                    int x = (int)(img.x * scaleX);
                    int y = (int)(img.y * scaleY);
                    int w = Math.min((int)(img.width * scaleX), thumbnailImage.getWidth() - x - 1);
                    int h = Math.min((int)(img.height * scaleY), thumbnailImage.getHeight() - y - 1);
                    g2d.fillRect(x, y, w, h);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("IMG", x + 2, y + 8);
                    g2d.setColor(Color.BLUE);
                }
                
                // Draw drawing shapes indicator if present
                if (snapshot.drawings != null && snapshot.drawings.length() > 0) {
                    g2d.setColor(new Color(0, 150, 0));
                    g2d.fillOval(thumbnailImage.getWidth() - 15, thumbnailImage.getHeight() - 15, 10, 10);
                }
            }
            
            g2d.dispose();
        }
        
        public void setSelected(boolean selected) {
            this.isSelected = selected;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            
            // Draw background
            if (isSelected) {
                g2d.setColor(new Color(200, 220, 255)); // Light blue for selected
            } else {
                g2d.setColor(Color.WHITE);
            }
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Draw border
            if (isSelected) {
                g2d.setColor(new Color(0, 100, 255)); // Blue border for selected
                g2d.setStroke(new BasicStroke(3));
            } else {
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(1));
            }
            g2d.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
            
            // Draw thumbnail image
            if (thumbnailImage != null) {
                int x = (getWidth() - thumbnailImage.getWidth()) / 2;
                int y = 5;
                g2d.drawImage(thumbnailImage, x, y, null);
            }
            
            // Draw slide number
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
            String slideNum = "Slide " + (slideIndex + 1);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = (getWidth() - fm.stringWidth(slideNum)) / 2;
            int textY = getHeight() - 5;
            g2d.drawString(slideNum, textX, textY);
            
            g2d.dispose();
        }
    }
}

