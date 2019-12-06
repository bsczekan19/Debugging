import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.Dimension;

import static org.junit.Assert.assertEquals;

public class DisplayPanelPITTest {

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

    @Before
    public void setup() {
        sudoku = new byte[729][82];
        Smethods.start(sudoku);
        dp = new DisplayPanel();
    }

    @Test
    public void getPreferredSizeTest() {
        Dimension correctDim = new Dimension(DISPLAY_WIDTH + BUTTONS_WIDTH, DISPLAY_HEIGHT);
        assertEquals(dp.getPreferredSize(), correctDim);
    }

    @Test(expected = NullPointerException.class)
    public void actionPerformedNullActionEventTest() {
        dp.actionPerformed(null);
    }

    @Test
    public void actionPerformedNullActionCommandTest() {
        MySudoku.step = 100;
        ActionEvent e = new ActionEvent(CS, 1, null);
        dp.actionPerformed(e);
        assertEquals(100, MySudoku.step);
    }

    @Test
    public void actionPerformedInvalidCommandTest() {
        MySudoku.step = 100;
        assertEquals(100, MySudoku.step);
        ActionEvent e = new ActionEvent(CS, 1, "invalid");
        assertEquals("invalid", e.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(100, MySudoku.step);
    }

    @Test
    public void actionPerformedCSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(CS, 1, CS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(0, MySudoku.step);
    }

    @Test
    public void actionPerformedHSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(HS, 1, HS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(25, MySudoku.step);
    }

    @Test
    public void actionPerformedMSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(MS, 1, MS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(35, MySudoku.step);
    }

    @Test
    public void actionPerformedESTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(ES, 1, ES.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(45, MySudoku.step);
    }

    /*
    // TODO: figure out why MySudoku.step isn't properly being updated to 81 after SS action
    @Test
    public void actionPerformedSSTest() {
        ActionEvent easy = new ActionEvent(ES, 2, ES.getActionCommand());
        ActionEvent solve = new ActionEvent(SS, 1, SS.getActionCommand());
        dp.actionPerformed(easy);
        assertEquals(45, MySudoku.step);
        dp.actionPerformed(solve);
        assertEquals(81, MySudoku.step);
    }*/

    @Test
    public void actionPerformedGBSNegativeStepTest() {
        MySudoku.step = -1;
        assertEquals(-1, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(-1, MySudoku.step);
    }

    @Test
    public void actionPerformedGBSZeroStepTest() {
        MySudoku.step = 0;
        assertEquals(0, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(0, MySudoku.step);
    }

    @Test
    public void actionPerformedGBSOneStepTest() {
        MySudoku.step = 1;
        assertEquals(1, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(0, MySudoku.step);
    }

    @Test
    public void actionPerformedGBSPositiveStepTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(9, MySudoku.step);
    }
}
