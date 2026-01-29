import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class SlideTextbox extends DraggablePanel {
    private JTextArea textInput;

    public JTextArea getTextArea(){
        return textInput;
    }

    public String getText(){
        return textInput.getText();
    }

    public void setText(String text){
        textInput.setText(text);
    }

    public SlideTextbox() {
        setLayout(new BorderLayout());
        setSize(200, 30);
        setOpaque(false);
        
        textInput = new JTextArea("Text here...");
        textInput.setLineWrap(true);
        textInput.setWrapStyleWord(true);
        textInput.setOpaque(false);
        textInput.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        textInput.setForeground(Color.BLACK);
        
        add(textInput, BorderLayout.CENTER);

        // Replaced constructor so that the entire SlideTextbox is passed in
        new TextAttributes(this);

        textInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePanel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePanel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePanel();
            }

        });
    }

    public void updatePanel()
    {
        // Calculate preferred size based on text content
        int preferredWidth = Math.max(200, textInput.getPreferredSize().width + 20);
        int preferredHeight = Math.max(30, textInput.getPreferredSize().height + 10);
        setSize(preferredWidth, preferredHeight);
        textInput.setSize(preferredWidth - 20, preferredHeight - 10);
        revalidate();
        repaint();
    }
}
