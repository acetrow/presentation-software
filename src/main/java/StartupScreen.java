import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Driver is the main startup screen
 */
public class StartupScreen {

    public StartupScreen()
    {
        System.out.println(javax.swing.SwingUtilities.isEventDispatchThread());
        JFrame startWindow = new JFrame("Open/Create presentation");
        startWindow.setSize(1920/2,1080/4);
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.setLocationRelativeTo(null);
        
        JPanel entries = new JPanel();
        entries.setLayout(new FlowLayout());
        JButton[] projects = new JButton[5];
        JPanel mainPanel = new JPanel();
        JButton openFileEx = new JButton("Open existing presentation");
        JButton newProject = new JButton("Create new presentation");
        
        // Add action listener for creating new presentation
        newProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startWindow.dispose();
                EditMenu menu = new EditMenu();
                menu.open();
            }
        });
        
        // Add action listener for opening existing presentation
        openFileEx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startWindow.dispose();
                EditMenu menu = new EditMenu();
                menu.open();
                // Trigger open dialog
                menu.getSideMenu().openPresentation();
            }
        });
        
        entries.add(newProject);

        for (int i = 0; i < projects.length; i++)
        {
            projects[i] = new JButton("Presentation "+i);
            // Placeholder for future: load specific presentation
            projects[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(startWindow, 
                        "This feature will load saved presentations.\nUse 'Open existing presentation' for now.");
                }
            });
            entries.add(projects[i]);
        }

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(entries, BorderLayout.NORTH);
        mainPanel.add(openFileEx, BorderLayout.PAGE_END);
        startWindow.add(mainPanel);
        startWindow.setVisible(true);
    }
}
