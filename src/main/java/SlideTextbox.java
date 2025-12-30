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

        setSize(200, 30);
        textInput = new JTextArea("Text here...");
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
        setSize(textInput.getPreferredSize().width+15,textInput.getPreferredSize().height+15);
//        System.out.println(getSize());
    }
}
