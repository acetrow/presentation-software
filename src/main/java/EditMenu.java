import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class EditMenu extends JFrame {
    private Slide slide;
    private DraggablePanel lastClicked;
    private ArrayList<SlideTextbox> textBoxes = new ArrayList<>();
    private ArrayList<SlideImage> images = new ArrayList<>();
    private SideMenu sideMenu;
    private Notes notes;
    private SlideThumbnailPanel thumbnailPanel;

    // Multi-slide state
    private final ArrayList<SlideSnapshot> slides = new ArrayList<>();
    private int currentSlideIndex = 0;
    private JLabel lblSlideStatus;

    private JLabel lblLastClicked = new JLabel("None");

    public ArrayList<SlideTextbox> getTextBoxes() {
        return textBoxes;
    }
    public ArrayList<SlideImage> getImages() {
        return images;
    }
    
    public Slide getSlide() {
        return slide;
    }
    
    public SideMenu getSideMenu() {
        return sideMenu;
    }
    
    public DraggablePanel getLastClicked() {
        return lastClicked;
    }

    public int getCurrentSlideIndex() {
        return currentSlideIndex;
    }

    public int getSlideCount() {
        return slides.size();
    }
    
    public SlideSnapshot getSlideSnapshot(int index) {
        if (index >= 0 && index < slides.size()) {
            return slides.get(index);
        }
        return null;
    }

    public JSONArray exportSlidesToJson() {
        snapshotCurrentSlide();
        JSONArray arr = new JSONArray();
        for (SlideSnapshot s : slides) {
            arr.put(s.toJson());
        }
        return arr;
    }

    public void loadSlidesFromJson(JSONArray slidesJson) {
        slides.clear();
        if (slidesJson == null || slidesJson.length() == 0) {
            slides.add(new SlideSnapshot());
        } else {
            for (int i = 0; i < slidesJson.length(); i++) {
                slides.add(SlideSnapshot.fromJson(slidesJson.getJSONObject(i)));
            }
        }
        currentSlideIndex = 0;
        renderSlide(currentSlideIndex);
        updateThumbnails();
    }
    

    // This is to be called by a draggable panel's mouse pressed event. When the panel is clicked, the editMenu instance
    // containing it will then know which one was clicked.
    public void setLastClicked(DraggablePanel newObject)
    {
        // remove the border on the old object if one exists
        if (lastClicked != null) lastClicked.setBorder(null);

        // update lastClicked
        lastClicked = newObject;

        // add the border to the new selected object
        lastClicked.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void open() {
        setTitle("Edit Presentation");
        setLayout(new BorderLayout(3,1));

        // Create left panel with thumbnails and side menu
        JPanel leftPanel = new JPanel(new BorderLayout());
        thumbnailPanel = new SlideThumbnailPanel(this);
        leftPanel.add(thumbnailPanel, BorderLayout.WEST);
        
        sideMenu = new SideMenu(this);
        leftPanel.add(sideMenu.open(), BorderLayout.CENTER);
        
        add(leftPanel, BorderLayout.WEST);

        
        JPanel topButtons = new JPanel(new FlowLayout());
        JButton btnGrowPanel = new JButton("+");
        JButton btnShrinkPanel = new JButton("-");
        SpinnerModel model = new SpinnerNumberModel(10, 0, 100, 10); // Initial value, minimum, maximum, and step
        JSpinner spinnerResizeAmt = new JSpinner(model);
        JLabel resizeLabel = new JLabel("Resize object: ");
        JCheckBox chkResizeX = new JCheckBox("Resize Width:",true);
        JCheckBox chkResizeY = new JCheckBox("Resize Height:",true);

        // Slide navigation controls (PowerPoint-like basics)
        JButton btnPrevSlide = new JButton("<");
        JButton btnNextSlide = new JButton(">");
        JButton btnAddSlide = new JButton("+ Slide");
        JButton btnRemoveSlide = new JButton("- Slide");
        lblSlideStatus = new JLabel();

        btnGrowPanel.addActionListener(e -> {
            if (lastClicked != null)  {
                int resizeAmt = (int) spinnerResizeAmt.getValue();
                if (chkResizeY.isSelected()) lastClicked.growY(resizeAmt);
                if (chkResizeX.isSelected()) lastClicked.growX(resizeAmt);
            }
        });
        btnShrinkPanel.addActionListener(e -> {
            if (lastClicked != null) {
                int resizeAmt = (int) spinnerResizeAmt.getValue() * -1;
                if (chkResizeX.isSelected()) lastClicked.growX(resizeAmt);
                if (chkResizeY.isSelected()) lastClicked.growY(resizeAmt);
            }
        });

        btnPrevSlide.addActionListener(e -> goToSlide(currentSlideIndex - 1));
        btnNextSlide.addActionListener(e -> goToSlide(currentSlideIndex + 1));
        btnAddSlide.addActionListener(e -> newSlide());
        btnRemoveSlide.addActionListener(e -> removeCurrentSlide());

        topButtons.add(resizeLabel);
        topButtons.add(btnGrowPanel);
        topButtons.add(btnShrinkPanel);
        topButtons.add(spinnerResizeAmt);
        topButtons.add(chkResizeX);
        topButtons.add(chkResizeY);
        topButtons.add(new JSeparator(SwingConstants.VERTICAL));
        topButtons.add(btnPrevSlide);
        topButtons.add(btnNextSlide);
        topButtons.add(lblSlideStatus);
        topButtons.add(btnAddSlide);
        topButtons.add(btnRemoveSlide);

  
        slide = new Slide();
        slide.setPreferredSize(new Dimension(1920 / 2, 1080 - 1080 / 4)); // Adjust size as needed

        notes = new Notes();
        notes.setPreferredSize(new Dimension(300, 400)); // Reasonable size for notes panel

        // SlideImage image = new SlideImage(250,250,"test.png");
        // image.setLocation(20,20);
        // slide.add(image);

        // SlideImage image2 = new SlideImage(500,500,"Untitled.jpeg");
        // image.setLocation(20,40);
        // slide.add(image2);

        // Create center panel with slide and notes
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(slide, BorderLayout.CENTER);
        centerPanel.add(notes, BorderLayout.EAST);
        
        add(topButtons, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // DraggablePanel textBox = new DraggablePanel();
        // textBox.setLocation(20, 20);
        // textBox.setSize(250, 250);
        // textBox.setVisible(true);

        // slide.add(textBox);

        setSize(1920 / 2, 1080 / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Initialize with one empty slide snapshot
        slides.clear();
        slides.add(new SlideSnapshot());
        currentSlideIndex = 0;
        renderSlide(currentSlideIndex);
        
        // Initialize thumbnail panel
        if (thumbnailPanel != null) {
            thumbnailPanel.refreshThumbnails();
        }

    }
    // useless comment to try and fix git

    public void add(DraggablePanel panel){

        if(panel instanceof SlideTextbox){
            textBoxes.add((SlideTextbox) panel);
        }
        if (panel instanceof SlideImage) {
            images.add((SlideImage) panel);
        }
        slide.add(panel);
        slide.repaint();
        slide.revalidate();
        
        // Update thumbnails when content changes (defer to avoid blocking)
        SwingUtilities.invokeLater(() -> {
            if (thumbnailPanel != null) {
                thumbnailPanel.refreshThumbnails();
            }
        });
    }
    
    public void enableDrawingLine() {
        slide.enableDrawingLine();
    }

    public void enableDrawingCircle() {
        slide.enableDrawingCircle();
    }

    public void enableDrawingSquare() {
        slide.enableDrawingSquare();
    }

    public void togglePencilMode() {
        slide.togglePencilMode();
    }

    public void toggleEraserMode() {
        slide.toggleEraserMode();
    }
    
    public void clearSlide() {
        // Clear all components from slide
        slide.removeAll();
        textBoxes.clear();
        images.clear();
        slide.repaint();
        slide.revalidate();
    }

    // ---- Multi-slide helpers ----

    public void newSlide() {
        snapshotCurrentSlide();
        slides.add(currentSlideIndex + 1, new SlideSnapshot()); // insert after current
        currentSlideIndex++;
        renderSlide(currentSlideIndex);
        updateThumbnails();
    }

    public void removeCurrentSlide() {
        if (slides.isEmpty()) {
            slides.add(new SlideSnapshot());
            currentSlideIndex = 0;
            renderSlide(currentSlideIndex);
            return;
        }

        if (slides.size() == 1) {
            slides.set(0, new SlideSnapshot());
            currentSlideIndex = 0;
            renderSlide(currentSlideIndex);
            return;
        }

        slides.remove(currentSlideIndex);
        if (currentSlideIndex >= slides.size()) currentSlideIndex = slides.size() - 1;
        renderSlide(currentSlideIndex);
        updateThumbnails();
    }

    public void goToSlide(int newIndex) {
        if (newIndex < 0 || newIndex >= slides.size()) return;
        snapshotCurrentSlide();
        currentSlideIndex = newIndex;
        renderSlide(currentSlideIndex);
        updateThumbnails();
    }

    public void resetPresentation() {
        slides.clear();
        slides.add(new SlideSnapshot());
        currentSlideIndex = 0;
        renderSlide(currentSlideIndex);
        updateThumbnails();
    }

    private void snapshotCurrentSlide() {
        if (slides.isEmpty()) return;
        SlideSnapshot s = SlideSnapshot.fromCurrent(this);
        slides.set(currentSlideIndex, s);
    }

    private void renderSlide(int index) {
        if (slides.isEmpty()) return;
        clearSlide();
        SlideSnapshot s = slides.get(index);

        // background + drawings
        slide.setBackground(s.background);
        slide.importDrawingsFromJson(s.drawings);

        // notes
        notes.setNotes(s.notes);

        // components
        for (SlideSnapshot.TextBoxSnapshot t : s.textBoxes) {
            SlideTextbox tb = new SlideTextbox();
            tb.getTextArea().setFont(new Font(t.fontName, t.fontStyle, t.fontSize));
            tb.getTextArea().setForeground(new Color(t.colorRed, t.colorGreen, t.colorBlue));
            tb.setLocation(t.x, t.y);
            tb.setSize(t.width, t.height);
            tb.setText(t.content);
            tb.setVisible(true);
            add(tb);
        }

        for (SlideSnapshot.ImageSnapshot im : s.images) {
            sideMenu.addImage(im.x, im.y, im.width, im.height, im.path);
        }

        updateSlideStatusLabel();
    }

    private void updateSlideStatusLabel() {
        if (lblSlideStatus != null) {
            lblSlideStatus.setText("Slide " + (currentSlideIndex + 1) + " / " + Math.max(1, slides.size()));
        }
    }
    
    private void updateThumbnails() {
        if (thumbnailPanel != null) {
            thumbnailPanel.refreshThumbnails();
            thumbnailPanel.setSelectedIndex(currentSlideIndex);
        }
        updateSlideStatusLabel();
    }

    // ---- Slide snapshot model (in-memory + JSON) ----
    public static class SlideSnapshot {
        public Color background = Color.WHITE;
        public String notes = "";
        public ArrayList<TextBoxSnapshot> textBoxes = new ArrayList<>();
        public ArrayList<ImageSnapshot> images = new ArrayList<>();
        public JSONObject drawings = new JSONObject(); // may be empty

        public static SlideSnapshot fromCurrent(EditMenu editMenu) {
            SlideSnapshot s = new SlideSnapshot();
            s.background = editMenu.slide.getBackground();
            s.notes = editMenu.notes.getNotes();
            s.drawings = editMenu.slide.exportDrawingsToJson();

            for (SlideTextbox tb : editMenu.textBoxes) {
                TextBoxSnapshot t = new TextBoxSnapshot();
                t.x = tb.getX();
                t.y = tb.getY();
                t.width = tb.getWidth();
                t.height = tb.getHeight();
                t.fontName = tb.getTextArea().getFont().getName();
                t.fontStyle = tb.getTextArea().getFont().getStyle();
                t.fontSize = tb.getTextArea().getFont().getSize();
                t.content = tb.getTextArea().getText();
                t.colorRed = tb.getTextArea().getForeground().getRed();
                t.colorGreen = tb.getTextArea().getForeground().getGreen();
                t.colorBlue = tb.getTextArea().getForeground().getBlue();
                s.textBoxes.add(t);
            }

            for (SlideImage img : editMenu.images) {
                ImageSnapshot im = new ImageSnapshot();
                im.x = img.getX();
                im.y = img.getY();
                im.width = img.getWidth();
                im.height = img.getHeight();
                im.path = img.getFile().toString();
                s.images.add(im);
            }

            return s;
        }

        public JSONObject toJson() {
            JSONObject o = new JSONObject();
            o.put("backgroundRed", background.getRed());
            o.put("backgroundGreen", background.getGreen());
            o.put("backgroundBlue", background.getBlue());
            o.put("notes", notes);
            o.put("drawings", drawings);

            JSONArray tbArr = new JSONArray();
            for (TextBoxSnapshot t : textBoxes) tbArr.put(t.toJson());
            o.put("textBoxes", tbArr);

            JSONArray imgArr = new JSONArray();
            for (ImageSnapshot i : images) imgArr.put(i.toJson());
            o.put("images", imgArr);
            return o;
        }

        public static SlideSnapshot fromJson(JSONObject o) {
            SlideSnapshot s = new SlideSnapshot();
            int r = o.optInt("backgroundRed", 255);
            int g = o.optInt("backgroundGreen", 255);
            int b = o.optInt("backgroundBlue", 255);
            s.background = new Color(r, g, b);
            s.notes = o.optString("notes", "");
            s.drawings = o.optJSONObject("drawings");
            if (s.drawings == null) s.drawings = new JSONObject();

            JSONArray tbArr = o.optJSONArray("textBoxes");
            if (tbArr != null) {
                for (int i = 0; i < tbArr.length(); i++) {
                    s.textBoxes.add(TextBoxSnapshot.fromJson(tbArr.getJSONObject(i)));
                }
            }

            JSONArray imgArr = o.optJSONArray("images");
            if (imgArr != null) {
                for (int i = 0; i < imgArr.length(); i++) {
                    s.images.add(ImageSnapshot.fromJson(imgArr.getJSONObject(i)));
                }
            }
            return s;
        }

        public static class TextBoxSnapshot {
            public int x, y, width, height;
            public String fontName;
            public int fontStyle;
            public int fontSize;
            public String content;
            public int colorRed, colorGreen, colorBlue;

            public JSONObject toJson() {
                JSONObject o = new JSONObject();
                o.put("xPosition", x);
                o.put("yPosition", y);
                o.put("width", width);
                o.put("height", height);
                o.put("fontName", fontName);
                o.put("fontStyle", fontStyle);
                o.put("fontSize", fontSize);
                o.put("content", content);
                o.put("colorRed", colorRed);
                o.put("colorGreen", colorGreen);
                o.put("colorBlue", colorBlue);
                return o;
            }

            public static TextBoxSnapshot fromJson(JSONObject o) {
                TextBoxSnapshot t = new TextBoxSnapshot();
                t.x = o.getInt("xPosition");
                t.y = o.getInt("yPosition");
                t.width = o.optInt("width", 250);
                t.height = o.optInt("height", 250);
                t.fontName = o.optString("fontName", Font.SANS_SERIF);
                t.fontStyle = o.optInt("fontStyle", Font.PLAIN);
                t.fontSize = o.optInt("fontSize", 14);
                t.content = o.optString("content", "");
                t.colorRed = o.optInt("colorRed", 0);
                t.colorGreen = o.optInt("colorGreen", 0);
                t.colorBlue = o.optInt("colorBlue", 0);
                return t;
            }
        }

        public static class ImageSnapshot {
            public int x, y, width, height;
            public String path;

            public JSONObject toJson() {
                JSONObject o = new JSONObject();
                o.put("xPosition", x);
                o.put("yPosition", y);
                o.put("width", width);
                o.put("height", height);
                o.put("path", path);
                return o;
            }

            public static ImageSnapshot fromJson(JSONObject o) {
                ImageSnapshot im = new ImageSnapshot();
                im.x = o.getInt("xPosition");
                im.y = o.getInt("yPosition");
                im.width = o.optInt("width", 200);
                im.height = o.optInt("height", 200);
                im.path = o.getString("path");
                return im;
            }
        }
    }
}

