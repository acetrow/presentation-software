import javax.swing.*;
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

    public void addImage(int xPosition, int yPosition,int height,int width , String fileDirectory){
        File file = new File(fileDirectory);
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
        JSONArray textBoxes = new JSONArray();
        JSONArray images = new JSONArray();


        editMenu.getTextBoxes().forEach((n) -> { // runs through the array of textBoxes adding each to an object
            System.out.println(n);
            JSONObject textBox = new JSONObject();

            textBox.put("xPosition", n.getLocation().x);
            textBox.put("yPosition", n.getLocation().y);
            textBox.put("fontName", n.getTextArea().getFont().getName());
            textBox.put("fontStyle", n.getTextArea().getFont().getStyle());
            textBox.put("fontSize", n.getTextArea().getFont().getSize());
            textBox.put("content", n.getTextArea().getText());
            textBox.put("colorRed", n.getTextArea().getForeground().getRed());
            textBox.put("colorGreen", n.getTextArea().getForeground().getGreen());
            textBox.put("colorBlue", n.getTextArea().getForeground().getBlue());



            textBoxes.put(textBox);
            System.out.println(n.getFontMetrics(n.getFont()));
        });

        attributes.put("textBoxes", textBoxes);


        editMenu.getImages().forEach((n) -> { // runs through the array of images adding each to an object
            System.out.println(n);
            JSONObject image = new JSONObject();

            image.put("xPosition", n.getLocation().x);
            image.put("yPosition", n.getLocation().y);
            image.put("height", n.getHeight());
            image.put("width", n.getWidth());
            image.put("path", n.getFile().toString());
            
            images.put(image);
        });

        attributes.put("images", images);

        System.out.println(attributes);


      
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

        //fileChooser.showOpenDialog(fileChooser);

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            saveDirectory = file;
            saved = true;

            try {
            
                String data = new String((Files.readAllBytes(Paths.get(file.getAbsolutePath()))));
                
                JSONObject json = new JSONObject(data);
    
                JSONArray textBoxes = (JSONArray) json.get("textBoxes");

                JSONArray images = (JSONArray) json.get("images");

                //System.out.println(textBoxes);
                for(int i = 0; i < textBoxes.length(); i++ ){
                    
                    JSONObject textBox = (JSONObject) textBoxes.get(i);
                    addTextBox(textBox.getInt("xPosition"), 
                    
                    textBox.getInt("yPosition"),
                    textBox.getString("fontName"),
                    textBox.getInt("fontSize"),
                    textBox.getInt("fontStyle"),
                    textBox.getString("content"),
                    textBox.getInt("colorRed"),
                    textBox.getInt("colorGreen"),
                    textBox.getInt("colorBlue")
                    
                    );
                    
                    System.out.println(textBox.getInt("xPosition")+ " " + textBox.getInt("yPosition")); 
            
                }

                for(int i = 0; i < images.length(); i++){
                    JSONObject image = (JSONObject) images.get(i);
                                       
                    addImage(image.getInt("xPosition"),
                    image.getInt("yPosition"),
                    image.getInt("width"),
                    image.getInt("width"),
                    image.getString("path"));
                }
    
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
