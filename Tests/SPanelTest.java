import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SPanelTest {
    private SPanel panel;

    @BeforeEach
    void init() {
        panel = new SPanel(new Dimension(10,20));
    }

    @Test
    void testSize() {
        assertEquals(new Dimension(10,20), panel.getPreferredSize());
    }

    @Test
    void testColor() {
        assertEquals(new Color(0xf5, 0xf5, 0xf5), panel.WS);
    }

    @Test
    void testBackground() {
        assertEquals(panel.WS, panel.getBackground());
    }
}

