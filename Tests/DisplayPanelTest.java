import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.awt.Dimension;


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
        sudoku = new byte[729][82];
        Smethods.start(sudoku);
        dp = new DisplayPanel();
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

    // TODO: figure out why MySudoku.step isn't properly being updated to 81 after SS action
    @Test
    void actionPerformedSSTest() {
        ActionEvent easy = new ActionEvent(ES, 2, ES.getActionCommand());
        ActionEvent e = new ActionEvent(SS, 1, SS.getActionCommand());
        dp.actionPerformed(easy);
        assertEquals(45, MySudoku.step);
        dp.actionPerformed(e);
        assertEquals(81, MySudoku.step);
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
