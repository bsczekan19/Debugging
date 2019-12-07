import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;

// This class creates the panels used for the border on the main window.
public class SPanel extends Panel {
    private static final long serialVersionUID = 1L;
    Color WS = new Color(0xf5, 0xf5, 0xf5);  //White Smoke background

    public SPanel(Dimension set) {
       super();
       this.setBackground(WS);
       this.setPreferredSize(set);
    }
}

