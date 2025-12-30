import javax.swing.*;
import java.awt.*;
/**
 * Driver is the main startup screen
 */
public class StartupScreen {

    public StartupScreen()
    {
        System.out.println(javax.swing.SwingUtilities.isEventDispatchThread());
        Frame startWindow = new JFrame("Open/Create presentation");
        startWindow.setSize(1920/2,1080/4);
        JPanel entries = new JPanel();
        entries.setLayout(new FlowLayout());
        JButton[] projects = new JButton[5];
        JPanel mainPanel = new JPanel();
        JButton openFileEx = new JButton("Open folder");
        JButton newProject = new JButton("Create new presentation");
        entries.add(newProject,BorderLayout.NORTH);

        for (int i = 0; i < projects.length; i++)
        {
            projects[i] = new JButton("Presentation "+i);
            entries.add(projects[i]);
        }

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(entries, BorderLayout.NORTH);
        mainPanel.add(openFileEx, BorderLayout.PAGE_END);
        startWindow.add(mainPanel);
        startWindow.setVisible(true);
        EditMenu menu = new EditMenu();
        menu.open();
    }
}
