import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.ActionEvent;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DisplayPanelTest {

    private DisplayPanel dp;
    private byte[][] sudoku;
    private final int DISPLAY_WIDTH = 557;
    private final int DISPLAY_HEIGHT = 580;
    private final int BUTTONS_WIDTH = 200;
    private SButton SS = new SButton(" Solved Sudoku ", "SS");
    private SButton GBS = new SButton(" Undo ", "GBS");
    private SButton ES = new SButton(" Easy ", "ES");
    private SButton MS = new SButton(" Medium ", "MS");
    private SButton HS = new SButton(" Hard ", "HS");
    private SButton CS = new SButton(" Custom Sudoku", "CS");


    @BeforeEach
    void init() {
        dp = new DisplayPanel();
        sudoku = new byte[729][82];
    }

    @Test
    void selectNumberNullTest() {

    }

    @Test
    void selectNumberTest() {

    }

    @Test
    void getPreferredSizeTest() {
        Dimension correctDim = new Dimension(DISPLAY_WIDTH + BUTTONS_WIDTH, DISPLAY_HEIGHT);
        assertEquals(dp.getPreferredSize(), correctDim);
    }

    @Test
    void paintComponentTest() {

    }

    @Test
    void actionPerformedNullActionEventTest() {
        assertThrows(NullPointerException.class,
                () -> dp.actionPerformed(null));
    }

    @Test
    void actionPerformedNullActionCommandTest() {
        System.out.println(MySudoku.step);
        MySudoku.step = 100;
        ActionEvent e = new ActionEvent(CS, 1, null);
        dp.actionPerformed(e);
        assertEquals(100, MySudoku.step);
    }

    @Test
    void actionPerformedInvalidCommandTest() {
        MySudoku.step = 100;
        assertEquals(100, MySudoku.step);
        ActionEvent e = new ActionEvent(CS, 1, "invalid");
        assertEquals("invalid", e.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(100, MySudoku.step);
    }

    @Test
    void actionPerformedCSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(CS, 1, CS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(0, MySudoku.step);
    }

    @Test
    void actionPerformedHSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(HS, 1, HS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(25, MySudoku.step);
    }

    @Test
    void actionPerformedMSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(MS, 1, MS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(35, MySudoku.step);
    }

    @Test
    void actionPerformedESTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(ES, 1, ES.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(45, MySudoku.step);
    }

    // TODO: not sure what to test here to ensure it's working, check dp.actionPerformed()
    @Test
    void actionPerformedSSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(SS, 1, SS.getActionCommand());
        dp.actionPerformed(e);
    }

    @Test
    void actionPerformedGBSNegativeStepTest() {
        MySudoku.step = -1;
        assertEquals(-1, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(-1, MySudoku.step);
    }

    @Test
    void actionPerformedGBSZeroStepTest() {
        MySudoku.step = 0;
        assertEquals(0, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(0, MySudoku.step);
    }

    @Test
    void actionPerformedGBSOneStepTest() {
        MySudoku.step = 1;
        assertEquals(1, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(0, MySudoku.step);
    }

    @Test
    void actionPerformedGBSPositiveStepTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(9, MySudoku.step);
    }

    /*
    @Test
    void exceptionTesting() {
        MyException thrown =
                assertThrows(MyException.class,
                        () -> myObject.doThing(),
                        "Expected doThing() to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Stuff"));
    }
    */

}