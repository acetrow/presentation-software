import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;


public class Notes extends JComponent {
    private JPanel notespanel;
    // private TextAttributes textAttributes;
    public Notes() {
        notespanel = new JPanel(null);
        notespanel.setSize(1920 / 4, 960);
        notespanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
        JTextArea notesArea = new JTextArea("Click to add notes ...");
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setEditable(true); 
        notesArea.setBounds(1, 1, 1920 / 4, 960);
        
        notesArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (notesArea.getText().equals("Click to add notes ...")) {
                    notesArea.setText("");
                }
            }
        
            @Override
            public void focusLost(FocusEvent e) {
                
            }
        });
        
        // textAttributes = new TextAttributes(notesArea);
        notespanel.add(notesArea); 
        add(notespanel); 
    }
}
