import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JFontChooser extends JDialog {
    private Font thisFont;
    private JList<String> fontList, fontStyleList, fontSizeList;
    private JButton okButton, cancelButton;
    private boolean okSelected;

    public JFontChooser(JFrame parent, Font currentFont) {
        super(parent, true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        thisFont = currentFont;

        setLayout(new BorderLayout());

        // Font names
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fontList = new JList<>(gEnv.getAvailableFontFamilyNames());
        fontList.setSelectedValue(thisFont.getFamily(), true);
        JScrollPane fontPane = new JScrollPane(fontList);
        add(fontPane, BorderLayout.WEST);

        // Font styles
        String[] fontStyles = {"Regular", "Bold", "Italic", "Bold Italic"};
        fontStyleList = new JList<>(fontStyles);
        fontStyleList.setSelectedIndex(thisFont.getStyle());
        JScrollPane stylePane = new JScrollPane(fontStyleList);
        add(stylePane, BorderLayout.CENTER);

        // Font sizes
        String[] fontSizes = new String[30];
        for (int i = 0; i < fontSizes.length; i++) {
            fontSizes[i] = String.valueOf(i + 10);
        }
        fontSizeList = new JList<>(fontSizes);
        fontSizeList.setSelectedValue(String.valueOf(thisFont.getSize()), true);
        JScrollPane sizePane = new JScrollPane(fontSizeList);
        add(sizePane, BorderLayout.EAST);

        // Buttons
        JPanel buttonPanel = new JPanel();
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        okButton.addActionListener(e -> {
            String fontName = fontList.getSelectedValue();
            int fontStyle = fontStyleList.getSelectedIndex();
            int fontSize = Integer.parseInt(fontSizeList.getSelectedValue());
            thisFont = new Font(fontName, fontStyle, fontSize);
            okSelected = true;
            setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            thisFont = null;
            setVisible(false);
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public Font getSelectedFont() {
        return thisFont;
    }

    public boolean showDialog() {
        setVisible(true);
        return okSelected;
    }
}
