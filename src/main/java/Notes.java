import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;


public class Notes extends JPanel {
    private JTextArea notesArea;
    
    public Notes() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Speaker Notes"));
    
        notesArea = new JTextArea("Click to add notes ...");
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setEditable(true);
        notesArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(notesArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        notesArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (notesArea.getText().equals("Click to add notes ...")) {
                    notesArea.setText("");
                    notesArea.setForeground(Color.BLACK);
                }
            }
        
            @Override
            public void focusLost(FocusEvent e) {
                if (notesArea.getText().trim().isEmpty()) {
                    notesArea.setText("Click to add notes ...");
                    notesArea.setForeground(Color.GRAY);
                }
            }
        });
        
        // Set initial gray color for placeholder
        notesArea.setForeground(Color.GRAY);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public String getNotes() {
        if (notesArea.getText().equals("Click to add notes ...")) {
            return "";
        }
        return notesArea.getText();
    }
    
    public void setNotes(String notes) {
        if (notes == null || notes.trim().isEmpty()) {
            notesArea.setText("Click to add notes ...");
            notesArea.setForeground(Color.GRAY);
        } else {
            notesArea.setText(notes);
            notesArea.setForeground(Color.BLACK);
        }
    }
}
