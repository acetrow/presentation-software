import javax.management.ListenerNotFoundException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class EditMenu extends JFrame {
    private Slide slide;
    private DraggablePanel lastClicked;
    private ArrayList<SlideTextbox> textBoxes = new ArrayList<>();
    private ArrayList<SlideImage> images = new ArrayList<>();

    private JLabel lblLastClicked = new JLabel("None");

    public ArrayList<SlideTextbox> getTextBoxes() {
        return textBoxes;
    }
    public ArrayList<SlideImage> getImages() {
        return images;
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

        SideMenu sideMenu = new SideMenu(this);
        add(sideMenu.open(), BorderLayout.WEST);

        
        JPanel topButtons = new JPanel(new FlowLayout());
        JButton btnGrowPanel = new JButton("+");
        JButton btnShrinkPanel = new JButton("-");
        SpinnerModel model = new SpinnerNumberModel(10, 0, 100, 10); // Initial value, minimum, maximum, and step
        JSpinner spinnerResizeAmt = new JSpinner(model);
        JLabel resizeLabel = new JLabel("Resize object: ");
        JCheckBox chkResizeX = new JCheckBox("Resize Width:",true);
        JCheckBox chkResizeY = new JCheckBox("Resize Height:",true);

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

        topButtons.add(resizeLabel);
        topButtons.add(btnGrowPanel);
        topButtons.add(btnShrinkPanel);
        topButtons.add(spinnerResizeAmt);
        topButtons.add(chkResizeX);
        topButtons.add(chkResizeY);

  
        slide = new Slide();
        slide.setPreferredSize(new Dimension(1920 / 2, 1080 - 1080 / 4)); // Adjust size as needed

        Notes notes = new Notes();
        notes.setPreferredSize(new Dimension(1920 / 6, 1080 / 100)); // Adjust size as needed

        // SlideImage image = new SlideImage(250,250,"test.png");
        // image.setLocation(20,20);
        // slide.add(image);

        // SlideImage image2 = new SlideImage(500,500,"Untitled.jpeg");
        // image.setLocation(20,40);
        // slide.add(image2);

        add(topButtons, BorderLayout.NORTH);
        add(slide, BorderLayout.CENTER);
        add(notes, BorderLayout.EAST);

        // DraggablePanel textBox = new DraggablePanel();
        // textBox.setLocation(20, 20);
        // textBox.setSize(250, 250);
        // textBox.setVisible(true);

        // slide.add(textBox);

        setSize(1920 / 2, 1080 / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

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
}

