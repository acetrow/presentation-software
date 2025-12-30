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
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent mouse)
            {
                // update cursor position on click
                lastClickX = mouse.getLocationOnScreen().x;
                lastClickY = mouse.getLocationOnScreen().y;

                // output position to terminal for debugging
                // System.out.println("("+lastClickX+" "+lastClickY+")");
                updateEditMenu();

            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent mouse)
            {
                // We calculate how far the mouse has travelled in both directions since the mousePressed event
                double xDiff = mouse.getLocationOnScreen().x - lastClickX;
                double yDiff = mouse.getLocationOnScreen().y - lastClickY;

                // Update the initial mouse location (is this necessary?)

                lastClickX = mouse.getLocationOnScreen().x;
                lastClickY = mouse.getLocationOnScreen().y;

                // Calculate (as an integer) the new position of the draggable panel by adding the
                // mouse offset to the object's position

                int newPosX = (int)(getLocation().x + xDiff);
                int newPosY = (int)(getLocation().y + yDiff);
                setLocation(newPosX,newPosY);

                // Output the new location to terminal for debugging
                // System.out.println(getLocation());
            }
        });
    }
}
