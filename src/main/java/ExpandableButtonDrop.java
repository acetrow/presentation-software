import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpandableButtonDrop extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel buttonPanel;
    private JButton expandButton;

    public ExpandableButtonDrop() {
        setTitle("Expandable Button Drop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.add(createButton("Button 1"));
        buttonPanel.add(createButton("Button 2"));
        buttonPanel.add(createButton("Button 3"));

        expandButton = new JButton("Expand");
        expandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expandButtonClicked();
            }
        });

        mainPanel.add(buttonPanel, "buttons");
        mainPanel.add(expandButton, "expand");

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonText.equals("Button 1")) {
                    createDropdownMenu(button);
                } else {
                    // Add functionality for other buttons if needed
                    JOptionPane.showMessageDialog(null, "Button Clicked: " + buttonText);
                }
            }
        });
        return button;
    }

    private void expandButtonClicked() {
        cardLayout.next(mainPanel);
    }

    private void createDropdownMenu(Component parentComponent) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem itemA = new JMenuItem("A");
        JMenuItem itemB = new JMenuItem("B");
        JMenuItem itemC = new JMenuItem("C");

        itemA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parentComponent, "Button 1 - A Clicked");
            }
        });

        itemB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parentComponent, "Button 1 - B Clicked");
            }
        });

        itemC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parentComponent, "Button 1 - C Clicked");
            }
        });

        popupMenu.add(itemA);
        popupMenu.add(itemB);
        popupMenu.add(itemC);
        popupMenu.show(parentComponent, 0, parentComponent.getHeight());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExpandableButtonDrop();
            }
        });
    }
}
