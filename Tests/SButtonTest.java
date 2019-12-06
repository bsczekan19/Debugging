import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SButtonTest {
    private SButton button;

    @BeforeEach
    void init() {
        button = new SButton("TestAction","TestCommand");
    }

    @Test
    void testGetSize() {
        assertEquals(new Dimension(130,30), button.getPreferredSize());
    }

    @Test
    void testColors() {
        assertEquals(new Color(0x00, 0x00, 0xcd), button.DB);
        assertEquals(new Color(0xf5, 0xf5, 0xf5), button.WS);
    }

    @Test
    void testBackground() {
        assertEquals(button.WS,button.getBackground());
    }

    @Test
    void testForeground() {
        assertEquals(button.DB,button.getForeground());
    }

    @Test
    void testActionCommand() {
        assertEquals("TestCommand",button.getActionCommand());
    }
}
