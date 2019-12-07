import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.awt.event.ActionEvent;
import java.awt.Dimension;

class DisplayPanelTest {

    private DisplayPanel dp;
    private final int DISPLAY_WIDTH = 557;
    private final int DISPLAY_HEIGHT = 580;
    private final int BUTTONS_WIDTH = 200;
    private SButton SS = new SButton(" Solved Sudoku ", "SS");
    private SButton GBS = new SButton(" Undo ", "GBS");
    private SButton ES = new SButton(" Easy ", "ES");
    private SButton MS = new SButton(" Medium ", "MS");
    private SButton HS = new SButton(" Hard ", "HS");
    private SButton CS = new SButton(" Custom Sudoku", "CS");

    // Reset step value and generate a new DisplayPanel for each test
    @BeforeEach
    void setUp() {
        MySudoku.step = 0;
        dp = new DisplayPanel();
    }

    // Clear the DisplayPanel after each test
    @AfterEach
    void tearDown() {
        dp = null;
    }

    /*
     * selectNumber tests work to check and see that the select number method is updating the value for
     * MySudoku.step (or not). Each test gets the current step value and then tests the selectNumber method with
     * a variety of invalid x, y values and one with correct values.
     */

    // Test with both x and y negative
    @Test
    void selectNumberNegativesTest() {
        int currentStep = MySudoku.step;
        dp.selectNumber(-1, -1);
        assertEquals(currentStep, MySudoku.step);
    }

    // Test with one value positive (valid value) and one negative twice for both combinations
    @Test
    void selectNumberOneNegativeTest() {
        int currentStep = MySudoku.step;
        dp.selectNumber(300, -1);
        assertEquals(currentStep, MySudoku.step);
        dp.selectNumber(-1, 100);
        assertEquals(currentStep, MySudoku.step);
    }

    // Test with both x and y equal to zero
    @Test
    void selectNumberZerosTest() {
        int currentStep = MySudoku.step;
        dp.selectNumber(0, 0);
        assertEquals(currentStep, MySudoku.step);
    }

    // Test with one value equal to zero and one a valid value, twice for both combinations
    @Test
    void selectNumberOneZeroTest() {
        int currentStep = MySudoku.step;
        dp.selectNumber(300, 0);
        assertEquals(currentStep, MySudoku.step);
        dp.selectNumber(0, 100);
        assertEquals(currentStep, MySudoku.step);
    }

    // Test with the x value equal to the boundary of the sudoku board
    @Test
    void selectNumberOnXBoundaryTest() {
        int currentStep = MySudoku.step;
        dp.selectNumber(203, 100);
        assertEquals(currentStep, MySudoku.step);
    }

    // Test with the x value one above the boundary of the sudoku board
    @Test
    void selectNumberAboveXBoundaryTest() {
        int currentStep = MySudoku.step;
        dp.selectNumber(204, 100);
        assertEquals(currentStep + 1, MySudoku.step);
    }

    // Test selecting a number with x or y above the bounds of the boarda
    @Test
    void selectNumberAboveBoundsTest() {
        int currentStep = MySudoku.step;
        dp.selectNumber(1000, 100);
        assertEquals(currentStep, MySudoku.step);
        dp.selectNumber(300, 1000);
        assertEquals(currentStep, MySudoku.step);
    }

    // Test with a valid x y combination
    @Test
    void selectNumberValidXYTest() {
        int currentStep = MySudoku.step;
        dp.selectNumber(538, 177);
        assertEquals(currentStep + 1, MySudoku.step);
    }

    // Test getPreferredSize method. Somewhat extraneous as the method returns a fixed value based on hardcoded values
    @Test
    void getPreferredSizeTest() {
        Dimension correctDim = new Dimension(DISPLAY_WIDTH + BUTTONS_WIDTH, DISPLAY_HEIGHT);
        assertEquals(dp.getPreferredSize(), correctDim);
    }

    /*
     * The actionPerformed set of tests works on the actionPerformed method, passing in null, invalid, and valid values
     * and checks to see that the action is being performed correctly. Before most methods the step value is set to an
     * arbitrary number and verified to have changed. Then after performing an action (valid or invalid) the test looks
     * to see that the step value was updated properly.
     */

    // Test with null as the ActionEvent. A NullPointerException is expected here
    @Test
    void actionPerformedNullActionEventTest() {
        assertThrows(NullPointerException.class,
                () -> dp.actionPerformed(null));
    }

    // Test with a non-null ActionEvent, but one whose command is null. Should not cause the step to be updated
    @Test
    void actionPerformedNullActionCommandTest() {
        MySudoku.step = 100;
        ActionEvent e = new ActionEvent(CS, 1, null);
        dp.actionPerformed(e);
        assertEquals(100, MySudoku.step);
    }

    // Test with a non-null ActionEvent, but one whose command is an invalid command. Step value should not change
    @Test
    void actionPerformedInvalidCommandTest() {
        MySudoku.step = 100;
        assertEquals(100, MySudoku.step);
        ActionEvent e = new ActionEvent(CS, 1, "invalid");
        assertEquals("invalid", e.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(100, MySudoku.step);
    }

    // Test the "CS" command. Step value should be set to 0
    @Test
    void actionPerformedCSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(CS, 1, CS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(0, MySudoku.step);
    }

    // Test the "HS" command. Step value should be set to 25
    @Test
    void actionPerformedHSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(HS, 1, HS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(25, MySudoku.step);
    }

    // Test the "MS" command. Step value should be set to 35
    @Test
    void actionPerformedMSTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(MS, 1, MS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(35, MySudoku.step);
    }

    // Test the "ES" command. Step value should be set to 45
    @Test
    void actionPerformedESTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(ES, 1, ES.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(45, MySudoku.step);
    }

    // Test the "SS" command. Step value should be updated to 81
    @Test
    void actionPerformedSSTest() {
        ActionEvent easy = new ActionEvent(ES, 2, ES.getActionCommand());
        ActionEvent solve = new ActionEvent(SS, 1, SS.getActionCommand());
        dp.actionPerformed(easy);
        assertEquals(45, MySudoku.step);
        dp.actionPerformed(solve);
        assertEquals(81, MySudoku.step);
    }

    // Test the "GBS" command with a negative step value. Should not change step value
    @Test
    void actionPerformedGBSNegativeStepTest() {
        MySudoku.step = -1;
        assertEquals(-1, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(-1, MySudoku.step);
    }

    // Test the "GBS" command with a step value of zero. Should not change step value
    @Test
    void actionPerformedGBSZeroStepTest() {
        MySudoku.step = 0;
        assertEquals(0, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(0, MySudoku.step);
    }

    // Test the "GBS" command with a step value of one. Should decrement the step value
    @Test
    void actionPerformedGBSOneStepTest() {
        MySudoku.step = 1;
        assertEquals(1, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(0, MySudoku.step);
    }

    // Test the "GBS" command with a positive step value. Should decrement the step value
    @Test
    void actionPerformedGBSPositiveStepTest() {
        MySudoku.step = 10;
        assertEquals(10, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(9, MySudoku.step);
    }

    // Test the "GBS" command with a step value that could not be achieved normally. Should decrement the step value
    @Test
    void actionPerformedGBSHigherThanPossibleStepTest() {
        MySudoku.step = 100;
        assertEquals(100, MySudoku.step);
        ActionEvent e = new ActionEvent(GBS, 1, GBS.getActionCommand());
        dp.actionPerformed(e);
        assertEquals(99, MySudoku.step);
    }

}
