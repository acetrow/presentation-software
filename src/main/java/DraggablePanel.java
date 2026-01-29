import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// This class extends JPanel since I initially thought a panel would be an ideal parent,
// but now I'm not sure if it should be a card object or some kind of object that has editable layers.
// Might consider rewriting this to be a more generic "draggable object" class.
public class DraggablePanel extends JPanel {
    // These variables store the position of the mouse every time the button is pressed down.
    private double lastClickX;
    private double lastClickY;

    public void updateEditMenu()
    {
        EditMenu editMenu = (EditMenu) SwingUtilities.getWindowAncestor(this);
        System.out.println(editMenu.getName());
        editMenu.setLastClicked(this);
    }
    public void growX(int x)
    {
        System.out.println("This object can't be manually resized!");
    }

    public void growY(int x)
    {
        System.out.println("This object can't be manually resized!");
    }


    public DraggablePanel() {
        setOpaque(false);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent mouse)
            {
                // Use relative coordinates instead of screen coordinates for better accuracy
                lastClickX = mouse.getX();
                lastClickY = mouse.getY();

                // output position to terminal for debugging
                // System.out.println("("+lastClickX+" "+lastClickY+")");
                updateEditMenu();

            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent mouse)
            {
                // Calculate the offset from the initial click position
                double xDiff = mouse.getX() - lastClickX;
                double yDiff = mouse.getY() - lastClickY;

                // Calculate the new position relative to parent
                Point parentLocation = getParent() != null ? getParent().getLocationOnScreen() : new Point(0, 0);
                Point currentLocation = getLocationOnScreen();
                
                int newPosX = (int)(getLocation().x + xDiff);
                int newPosY = (int)(getLocation().y + yDiff);
                
                // Ensure the component stays within bounds of parent
                if (getParent() != null) {
                    int maxX = getParent().getWidth() - getWidth();
                    int maxY = getParent().getHeight() - getHeight();
                    newPosX = Math.max(0, Math.min(newPosX, maxX));
                    newPosY = Math.max(0, Math.min(newPosY, maxY));
                }
                
                setLocation(newPosX, newPosY);

                // Output the new location to terminal for debugging
                // System.out.println(getLocation());
            }
        });
    }
}
