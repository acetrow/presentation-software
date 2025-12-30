import javax.swing.*;
import java.awt.*;


public class TextAttributes{
    private JTextArea textArea;

    private SlideTextbox textBox;
    private JPopupMenu popupMenu;

    public Font getText(){
        return textArea.getFont();
    }

    public TextAttributes(JTextArea textArea) {
        this.textArea = textArea;
        createPopupMenu();
    }

    // This is an alternative constructor that passes in an entire instance of SlideTextbox rather than just
    // a JTextArea. Using this constructor will set private variable textBox.
    public TextAttributes(SlideTextbox textbox)
    {
        this.textBox = textbox;
        this.textArea = textbox.getTextArea();
        createPopupMenu();
    }

    private void createPopupMenu() {
        popupMenu = new JPopupMenu();

        //changing font
        JMenuItem changeFontItem = new JMenuItem("Change Font");
        changeFontItem.addActionListener(e -> showFontDialog());
        popupMenu.add(changeFontItem);

        //changing colour
        JMenuItem changeColorItem = new JMenuItem("Change Colour");
        changeColorItem.addActionListener(e -> showColorDialog());
        popupMenu.add(changeColorItem);

        textArea.setComponentPopupMenu(popupMenu);
    }

    private void showFontDialog() {
        JFontChooser fontChooser = new JFontChooser((JFrame) SwingUtilities.getWindowAncestor(textArea), textArea.getFont());
        boolean result = fontChooser.showDialog();
        if (result) {
            Font selectedFont = fontChooser.getSelectedFont();
            textArea.setFont(selectedFont);

            // if textBox isn't null (if a SlideTextbox was passed into this class instead of a JTextArea),
            // we run updatePanel on the SlideTextbox so that its size matches the new font size.
            if (textBox != null) textBox.updatePanel();
        }
    }

    private void showColorDialog() {
        Color newColor = JColorChooser.showDialog(textArea, "Choose Text Colour", textArea.getForeground());
        if (newColor != null) {
            textArea.setForeground(newColor);
        }
    }
}
