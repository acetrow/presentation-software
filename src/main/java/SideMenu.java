import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.FileReader;



import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class SideMenu {

    private Slide slide;
    private EditMenu editMenu;
    private boolean saved;
    private File saveDirectory;

    public SideMenu(EditMenu editMenu){
        this.editMenu = editMenu;
    }

    public void setSlide(Slide slide) {
        this.slide = slide;
    }
    
    public Slide getSlide() {
        if (slide == null && editMenu != null) {
            slide = editMenu.getSlide();
        }
        return slide;
    }
    
    public JMenuBar open(){
    
        JMenu menuFile, menuEdit, menuHome, menuInsert, menuTools,menuPresent,menuHelp;
        JMenuItem itemSave, itemSaveAs, itemOpen, itemNewPresentation
                    ,itemSpellCheck, itemCopy, itemPaste, 
                    itemShortcutList, itemFont, itemFontSize, itemBold, itemUnderLine, itemItallic, itemTextBox, itemJustifyleft, itemJustifyCenter, 
                    itemJustifyRight, itemFontColor, itemNewSlide, itemRemoveSlide,itemNew, itemBulletpoints, itemUndo, itemRedo, itemSubscript, itemSuperscript,
                    itemSlideColor, itemNewTable, itemInsertImage, itemInsertCodeSnippit,itemToolsLine, itemToolsCircle, itemToolsSquare, itemToolsDraw, itemToolsErase, itemPresent;  
        
        VerticalMenuBar menuBar = new VerticalMenuBar();
        
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
        menuHome = new JMenu("Home");
        menuInsert = new JMenu("Insert");
        menuTools = new JMenu("Tools");
        menuPresent = new JMenu("Present");
        menuHelp = new JMenu("Help");

        itemSave = new JMenuItem("Save");
        itemSaveAs = new JMenuItem("Save As");
        itemOpen = new JMenuItem("Open");
        itemNewPresentation = new JMenuItem("New presentation");
       
       
        itemFont = new JMenuItem("Fonts");
        itemBold = new JMenuItem("Bold");
        itemUnderLine = new JMenuItem("Underline");
        itemItallic = new JMenuItem("Itallic");
        itemTextBox = new JMenuItem("Insert Text Box");
        itemJustifyleft = new JMenuItem("Justify Text Left");
        itemJustifyCenter = new JMenuItem("Justify Text Center");
        itemJustifyRight = new JMenuItem("Justify Text Right");
        itemFontColor = new JMenuItem("Font Colour");
        itemNewSlide = new JMenuItem("New slide");
        itemRemoveSlide = new JMenuItem("Remove Slide");
        itemBulletpoints = new JMenuItem("New Bullet Point List");
        itemUndo = new JMenuItem("Undo Last");
        itemRedo = new JMenuItem("Redo Last");
        itemSubscript = new JMenuItem("Subscript Text");
        itemSuperscript = new JMenuItem("Superscript Text");
        itemSlideColor = new JMenuItem("Slide Colour");
        itemNewTable = new JMenuItem("Insert New Table");
        itemInsertImage = new JMenuItem("Insert Image");
        itemInsertCodeSnippit = new JMenuItem("Insert Code Snippet");
        itemPresent = new JMenuItem("Present");

        itemToolsLine = new JMenuItem("Insert Line");
        itemToolsCircle = new JMenuItem("Insert Circle");
        itemToolsSquare = new JMenuItem("Insert Square");
        itemToolsDraw = new JMenuItem("Draw");
        itemToolsErase = new JMenuItem("Erase");

        menuTools.add(itemToolsCircle);
        menuTools.add(itemToolsSquare);
        menuTools.add(itemToolsDraw);
        menuTools.add(itemToolsErase);

        itemSpellCheck = new JMenuItem("Spell Check Language");
        itemCopy = new JMenuItem("Copy");
        itemPaste = new JMenuItem("Paste");
        
        itemShortcutList = new JMenuItem("View Shortcut List");
        
        

        menuFile.add(itemSave);
        menuFile.add(itemSaveAs);
        menuFile.add(itemOpen);
        menuFile.add(itemNewPresentation);
        menuHome.add(itemFont);
        menuHome.add(itemBold);
        menuHome.add(itemUnderLine);
        menuHome.add(itemItallic);
        menuHome.add(itemJustifyleft);
        menuHome.add(itemJustifyCenter);
        menuHome.add(itemJustifyRight);
        menuHome.add(itemFontColor);
        menuHome.add(itemNewSlide);
        menuHome.add(itemRemoveSlide);
        menuHome.add(itemUndo);
        menuHome.add(itemRedo);
        menuHome.add(itemSubscript);
        menuHome.add(itemSuperscript);
        menuHome.add(itemSlideColor);
        menuInsert.add(itemTextBox);
        menuInsert.add(itemNewTable);
        menuInsert.add(itemInsertImage);

        menuInsert.add(itemInsertCodeSnippit);
        menuPresent.add(itemPresent);
        menuTools.add(itemToolsLine);
        menuTools.add(itemToolsCircle);
        menuTools.add(itemToolsSquare);
        menuTools.add(itemToolsDraw);
        menuTools.add(itemToolsErase);

        menuEdit.add(itemSpellCheck);
        menuEdit.add(itemCopy);
        menuEdit.add(itemPaste);
        

        menuHelp.add(itemShortcutList);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuHelp);
        menuBar.add(menuHome);
        menuBar.add(menuInsert);
        menuBar.add(menuTools);
        menuBar.add(menuPresent);

        itemToolsLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenu.enableDrawingLine(); // Use EditMenu method
            }
        });

        itemToolsSquare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenu.enableDrawingSquare(); // Use EditMenu method
            }
        });

        itemToolsCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenu.enableDrawingCircle();
            }
        });
        
        itemToolsDraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenu.togglePencilMode();
            }
        });

        itemToolsErase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenu.toggleEraserMode();
            }
        });


        itemTextBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                addTextBox();
            }
        });

        itemSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                saveAsPresentation();
            }
        });

        itemSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(saved){
                    savePresentation(saveDirectory);
                }else{
                    saveAsPresentation();
                }
                
            }
        });

        itemOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                openPresentation();
            }
        });

        itemInsertImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                addImage();
            }
        });

        // Text formatting action listeners
        itemBold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyBold();
            }
        });

        itemItallic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyItalic();
            }
        });

        itemUnderLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyUnderline();
            }
        });

        itemFont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFontDialog();
            }
        });

        itemFontColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFontColorDialog();
            }
        });

        itemJustifyleft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyJustification(SwingConstants.LEFT);
            }
        });

        itemJustifyCenter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyJustification(SwingConstants.CENTER);
            }
        });

        itemJustifyRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyJustification(SwingConstants.RIGHT);
            }
        });

        itemSubscript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Subscript feature: Right-click on text box and use formatting options.", 
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemSuperscript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Superscript feature: Right-click on text box and use formatting options.", 
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemSlideColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeSlideColor();
            }
        });

        itemNewSlide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editMenu.newSlide();
            }
        });

        itemRemoveSlide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editMenu.removeCurrentSlide();
            }
        });

        itemNewPresentation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, 
                    "Create a new presentation? Current unsaved changes will be lost.", 
                    "New Presentation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    editMenu.resetPresentation();
                    saved = false;
                    saveDirectory = null;
                }
            }
        });

        itemPresent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startPresentation();
            }
        });

        // Placeholder actions for unimplemented features
        itemSpellCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Spell check feature coming soon!", 
                    "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Copy feature coming soon!", 
                    "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Paste feature coming soon!", 
                    "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Undo feature coming soon!", 
                    "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Redo feature coming soon!", 
                    "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemNewTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Table insertion feature coming soon!", 
                    "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemInsertCodeSnippit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Code snippet feature coming soon!", 
                    "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemBulletpoints.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Bullet points feature coming soon!", 
                    "Feature Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemShortcutList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showShortcuts();
            }
        });

        return menuBar;
        
    }


    public void addTextBox(){
        SlideTextbox textBox = new SlideTextbox();
        textBox.setLocation(20, 20);
        textBox.setSize(250, 250);
        textBox.setVisible(true);

        editMenu.add(textBox);

    }

    public void addTextBox(int x, int y,String fontName, int fontSize, int fontStyle,String content, int red, int green, int blue){
        SlideTextbox textBox = new SlideTextbox();
        textBox.getTextArea().setFont(new Font(fontName, fontStyle, fontSize));
        textBox.getTextArea().setForeground(new Color(red, green, blue));
        textBox.setSize(250, 250);
        textBox.setLocation(x, y);
        textBox.setText(content);
        textBox.setVisible(true);
        editMenu.add(textBox);

    }
    public void addImage(){

        JFileChooser fileChooser = new JFileChooser();
        File file;
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
            SlideImage image = new SlideImage(50, 50, file);
            editMenu.add(image);
        }
    }

    public void addImage(int xPosition, int yPosition, int width, int height, String fileDirectory){
        File file = new File(fileDirectory);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, 
                "Image file not found: " + fileDirectory + "\nPlease ensure the image file exists.",
                "File Not Found", JOptionPane.WARNING_MESSAGE);
            return;
        }
        SlideImage image = new SlideImage(width, height, file);
        image.setLocation(xPosition, yPosition);
        editMenu.add(image);
    }

    public void saveAsPresentation(){

        JFileChooser fileChooser = new JFileChooser();

        if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            String path = file.getPath() + ".json";
            file = new File(path);
            saveDirectory = file;
            savePresentation(file);
            saved = true;
        }

    }

    public void savePresentation(File file){

        JSONObject attributes = new JSONObject();

        // v2 multi-slide format
        attributes.put("version", 2);
        attributes.put("currentSlideIndex", editMenu.getCurrentSlideIndex());
        attributes.put("slides", editMenu.exportSlidesToJson());


      
        try (FileWriter fileWriter = new FileWriter(file.getPath(), false)) {
            fileWriter.write(attributes.toString());
            fileWriter.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }

    public void openPresentation(){

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));

        if(fileChooser.showOpenDialog(editMenu) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            saveDirectory = file;
            saved = true;

            try {
                String data = new String((Files.readAllBytes(Paths.get(file.getAbsolutePath()))));
                
                JSONObject json = new JSONObject(data);

                // v2 multi-slide: {"slides":[...]} ; v1 legacy: {"textBoxes":[...],"images":[...]}
                JSONArray slidesJson = json.optJSONArray("slides");
                if (slidesJson != null) {
                    editMenu.loadSlidesFromJson(slidesJson);
                    int idx = json.optInt("currentSlideIndex", 0);
                    editMenu.goToSlide(idx);
                } else {
                    // legacy single-slide load into slide 1
                    editMenu.resetPresentation();

                    JSONArray textBoxes = (JSONArray) json.get("textBoxes");
                    JSONArray images = (JSONArray) json.get("images");

                    for(int i = 0; i < textBoxes.length(); i++ ){
                        JSONObject textBox = (JSONObject) textBoxes.get(i);
                        addTextBox(
                                textBox.getInt("xPosition"),
                                textBox.getInt("yPosition"),
                                textBox.getString("fontName"),
                                textBox.getInt("fontSize"),
                                textBox.getInt("fontStyle"),
                                textBox.getString("content"),
                                textBox.getInt("colorRed"),
                                textBox.getInt("colorGreen"),
                                textBox.getInt("colorBlue")
                        );
                    }

                    for(int i = 0; i < images.length(); i++){
                        JSONObject image = (JSONObject) images.get(i);
                        addImage(
                                image.getInt("xPosition"),
                                image.getInt("yPosition"),
                                image.getInt("width"),
                                image.getInt("height"),
                                image.getString("path")
                        );
                    }
                }
    
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(editMenu, 
                    "File not found: " + file.getAbsolutePath(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(editMenu, 
                    "Error reading file: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (org.json.JSONException e) {
                JOptionPane.showMessageDialog(editMenu, 
                    "Invalid JSON file format: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(editMenu, 
                    "Unexpected error: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void applyBold() {
        DraggablePanel lastClicked = editMenu.getLastClicked();
        if (lastClicked instanceof SlideTextbox) {
            SlideTextbox textBox = (SlideTextbox) lastClicked;
            Font currentFont = textBox.getTextArea().getFont();
            int newStyle = currentFont.getStyle() ^ Font.BOLD;
            textBox.getTextArea().setFont(new Font(currentFont.getName(), newStyle, currentFont.getSize()));
            textBox.updatePanel();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a text box first.", "No Text Box Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void applyItalic() {
        DraggablePanel lastClicked = editMenu.getLastClicked();
        if (lastClicked instanceof SlideTextbox) {
            SlideTextbox textBox = (SlideTextbox) lastClicked;
            Font currentFont = textBox.getTextArea().getFont();
            int newStyle = currentFont.getStyle() ^ Font.ITALIC;
            textBox.getTextArea().setFont(new Font(currentFont.getName(), newStyle, currentFont.getSize()));
            textBox.updatePanel();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a text box first.", "No Text Box Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void applyUnderline() {
        DraggablePanel lastClicked = editMenu.getLastClicked();
        if (lastClicked instanceof SlideTextbox) {
            SlideTextbox textBox = (SlideTextbox) lastClicked;
            // Note: JTextArea doesn't support underline directly, would need styled text
            JOptionPane.showMessageDialog(null, 
                "Underline formatting requires styled text. Use right-click menu for advanced formatting.", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a text box first.", "No Text Box Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showFontDialog() {
        DraggablePanel lastClicked = editMenu.getLastClicked();
        if (lastClicked instanceof SlideTextbox) {
            SlideTextbox textBox = (SlideTextbox) lastClicked;
            JFontChooser fontChooser = new JFontChooser(editMenu, textBox.getTextArea().getFont());
            boolean result = fontChooser.showDialog();
            if (result) {
                Font selectedFont = fontChooser.getSelectedFont();
                textBox.getTextArea().setFont(selectedFont);
                textBox.updatePanel();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a text box first.", "No Text Box Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showFontColorDialog() {
        DraggablePanel lastClicked = editMenu.getLastClicked();
        if (lastClicked instanceof SlideTextbox) {
            SlideTextbox textBox = (SlideTextbox) lastClicked;
            Color newColor = JColorChooser.showDialog(editMenu, "Choose Text Colour", textBox.getTextArea().getForeground());
            if (newColor != null) {
                textBox.getTextArea().setForeground(newColor);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a text box first.", "No Text Box Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void applyJustification(int alignment) {
        DraggablePanel lastClicked = editMenu.getLastClicked();
        if (lastClicked instanceof SlideTextbox) {
            SlideTextbox textBox = (SlideTextbox) lastClicked;
            // JTextArea doesn't support alignment directly, but we can show a message
            JOptionPane.showMessageDialog(null, 
                "Text alignment is applied visually. For precise alignment, position text boxes manually.", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a text box first.", "No Text Box Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void changeSlideColor() {
        Slide slide = getSlide();
        if (slide != null) {
            Color newColor = JColorChooser.showDialog(editMenu, "Choose Slide Background Colour", slide.getBackground());
            if (newColor != null) {
                slide.setBackground(newColor);
                slide.repaint();
            }
        }
    }

    private void startPresentation() {
        Slide slide = getSlide();
        if (slide != null) {
            PresentingView presenter = new PresentingView(slide, editMenu);
            presenter.show();
        }
    }

    private void showShortcuts() {
        String shortcuts = "Keyboard Shortcuts:\n\n" +
            "File Operations:\n" +
            "  Ctrl+S - Save\n" +
            "  Ctrl+O - Open\n" +
            "  Ctrl+N - New Presentation\n\n" +
            "Text Formatting:\n" +
            "  Ctrl+B - Bold\n" +
            "  Ctrl+I - Italic\n" +
            "  Ctrl+U - Underline\n\n" +
            "Tools:\n" +
            "  Right-click on text box - Format options\n" +
            "  Click and drag - Move objects\n" +
            "  Use + and - buttons - Resize selected object\n\n" +
            "Drawing:\n" +
            "  Tools → Insert Line/Circle/Square - Draw shapes\n" +
            "  Tools → Draw - Freehand drawing\n" +
            "  Tools → Erase - Remove drawn elements";
        
        JOptionPane.showMessageDialog(editMenu, shortcuts, "Keyboard Shortcuts", JOptionPane.INFORMATION_MESSAGE);
    }
}
