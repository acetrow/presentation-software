import javax.swing.JMenuBar;
import java.awt.GridLayout;
import java.awt.LayoutManager;

public class VerticalMenuBar extends JMenuBar {
    private static final LayoutManager grid = new GridLayout(0, 1);
    public VerticalMenuBar() {
        setLayout(grid);
    }
}
