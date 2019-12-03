import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SmethodsTest {
    private byte[][] sudoku;

    // returns true if board is correctly solved
    boolean isSolved(int[][] board) {
        for (int i = 0; i < 9; i++) {
            int[] row = new int[9];
            int[] col = new int[9];
            int[] grid = new int[9];
            for (int j = 0; j < 9; j++) {
                row[board[i][j]]++;
                col[board[j][i]]++;
                int rowidx = 3*(i/3);
                int colidx = 3*(i%3);
                grid[board[rowidx + j/3][colidx + j%3]]++;
            }
            for (int j = 0; j < 9; j++) {
                if (1 != row[i] || 1 != col[i] || 1 != grid[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    @BeforeEach
    void init() {
        sudoku = new byte[729][82];
    }

    // try start() on an empty board
    @Test
    void testStart() {
        Smethods.start(sudoku);
        // check that board is initialized correctly
        for (int i = 0; i < 729; i++) {
            assertEquals(1+(i%9),sudoku[i][0]);
        }
        for (int i = 1; i < 82; i++) {
            assertEquals(0,sudoku[0][i]);
        }
    }

    // try start() on null
    @Test
    void testStartNull() {
        sudoku = null;
        assertThrows(NullPointerException.class, () -> Smethods.start(sudoku));
    }

    // try to select with a step that is invalid (too high) for the given board
    @Test
    void testSelectInvalidStep() {
        Smethods.start(sudoku);
        // step is set to 1 but step 0 has not occurred yet
        byte ret = Smethods.select(sudoku, (byte)0, (byte)0, (byte)1);
        // will return early - step is not increased
        assertEquals((byte)1, ret);
        // another example - step 4 cannot happen until steps 0-3 occur
        ret = Smethods.select(sudoku, (byte)0, (byte)0, (byte)4);
        assertEquals((byte)4, ret);
    }

    // try to fill in a cell that is already filled
    @Test
    void testSelectAlreadyFilled() {
        Smethods.start(sudoku);
        // fill in 1 at position 1
        byte ret = Smethods.select(sudoku, (byte)1, (byte)1, (byte)0);
        // check that step has been taken (increased)
        assertEquals((byte)1, ret);
        // try to fill in 1 at position 1 again
        ret = Smethods.select(sudoku, (byte)1, (byte)1, (byte)1);
        // check that no step has been taken
        assertEquals((byte)1, ret);
        // try to fill in 4 at position 1
        ret = Smethods.select(sudoku, (byte)4, (byte)1, (byte)1);
        // check that no step has been taken
        assertEquals((byte)1, ret);
    }

    // try to select with a negative step
    @Test
    void testSelectNegativeStep() {
        Smethods.start(sudoku);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Smethods.select(sudoku, (byte)0, (byte)0, (byte)-1));
    }

    // try to select with a step as 81
    @Test
    void testSelect81Step() {
        // fill in board so that 82nd step is not invalid because board is empty at previous step
        testTrySudoku();
        // all cells already marked so method will return early - no step can be taken
        assertEquals(81, Smethods.select(sudoku, (byte)0, (byte)0, (byte)81));
    }

    // try to select with a step over 81
    @Test
    void testSelectOver81Step() {
        // fill in board so that 82nd step is not invalid because board is empty at previous step
        testTrySudoku();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Smethods.select(sudoku, (byte)0, (byte)0, (byte)82));
    }

    // try to fill in a cell with a negative number
    @Test
    void testSelectNegativeNum() {
        Smethods.start(sudoku);
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  Smethods.select(sudoku, (byte)-1, (byte)0, (byte)0));
    }

    // try to fill in a cell with a number over 8
    @Test
    void testSelectAbove8Num() {
        Smethods.start(sudoku);
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  Smethods.select(sudoku, (byte)9, (byte)80, (byte)0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  Smethods.select(sudoku, (byte)9, (byte)0, (byte)0));
    }

    // try to fill in a cell at a negative position
    @Test
    void testSelectNegativePosition() {
        Smethods.start(sudoku);
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  Smethods.select(sudoku, (byte)0, (byte)-1, (byte)0));
    }

    // try to fill in a cell at a position above 80
    @Test
    void testSelectAbove80Position() {
        Smethods.start(sudoku);
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  Smethods.select(sudoku, (byte)0, (byte)81, (byte)0));
    }

    // try to select on a board that was initialized incorrectly (not following board rules)
    @Test
    void testSelectInvalidBoard() {
        for(int i = 0; i < 729; i++) {
            sudoku[i][0] = (byte)4;
        }
        byte ret = Smethods.select(sudoku, (byte)2, (byte)0, (byte)0);
        // a step shouldn't be allowed
        assertEquals((byte)0, ret);
    }

    // test that the first (step 0) select works
    @Test
    void testSelectFirstStep() {
        Smethods.start(sudoku);
        ArrayList<Integer> zeros = new ArrayList<>();
        // fill in 2 at position 0
        byte ret = Smethods.select(sudoku, (byte)2, (byte)0, (byte)0);
        // check that step was taken (increased)
        assertEquals((byte)1, ret);
        // check that board is updated accordingly
        for(int i = 0; i < 9; i++) {
            zeros.add(i);
            zeros.add(i * 9 + 2);
            zeros.add(i * 81 + 2);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                zeros.add(i*81 + j * 9 + 2);
            }
        }
        zeros.remove((Integer)2);
        for (int i = 0; i < 729; i++) {
            if (i == 2) {
                assertEquals(13,sudoku[i][1]);
            }
            else if (zeros.contains(i)) {
                assertEquals(0,sudoku[i][1]);
            }
            else {
                assertEquals(sudoku[i][0],sudoku[i][1]);
            }
        }
    }

    // test that filling in the second and third steps also work
    @Test
    void testSelectNextSteps() {
        testSelectFirstStep();
        byte ret = Smethods.select(sudoku, (byte)8, (byte)80, (byte)1);
        assertEquals((byte)2, ret);
        ret = Smethods.select(sudoku, (byte)0, (byte)24, (byte)2);
        assertEquals((byte)3, ret);
    }

    // test trySudoku with a negative start step
    @Test
    void testTrySudokuNegativeStart() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Smethods.trysudoku(sudoku, (byte)-1));
    }

    // test trySudoku with a start step of 81
    @Test
    void testTrySudoku81Step() {
        Smethods.start(sudoku);
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        assertArrayEquals(copy,sudoku);
        Smethods.trysudoku(sudoku, (byte)81);
        // no step was taken
        assertEquals(81,MySudoku.step);
        // the board was not modified
        assertArrayEquals(copy,sudoku);
    }

    // test trySudoku with a start step over 81
    @Test
    void testTrySudokuOver81Step() {
        Smethods.start(sudoku);
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        assertArrayEquals(copy,sudoku);
        Smethods.trysudoku(sudoku, (byte)82);
        // no step was taken
        assertEquals(82,MySudoku.step);
        // the board was not modified
        assertArrayEquals(copy,sudoku);
    }

    // test trySudoku with a start step that is invalid for the current board
    @Test
    void testTrySudokuInvalidStartStep() {
        Smethods.start(sudoku);
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy(sudoku[i], 0, copy[i], 0, sudoku[i].length);
        }
        assertArrayEquals(copy,sudoku);
        // step 1 can't happen without step 0 first
        Smethods.trysudoku(sudoku, (byte)1);
        // no step was taken
        assertEquals(1,MySudoku.step);
        // the board was not modified
        assertArrayEquals(copy,sudoku);
    }

    // test trySudoku on a properly initialized board with 0 as start step
    @Test
    void testTrySudoku() {
        Smethods.start(sudoku);
        Smethods.trysudoku(sudoku, (byte)0);
        // verify that the board is solved correctly
        int count = 0;
        int[][] board = new int[9][9];
        // fill in board solution
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
        assertTrue(isSolved(board));
    }

    // test trySudoku on a board that is invalid (not following rules)
    @Test
    void testTrySudokuInvalidBoard() {
        for(int i = 0; i < 729; i++) {
            sudoku[i][0] = (byte)4;
        }
        Smethods.trysudoku(sudoku, (byte)0);
        /*assertTimeoutPreemptively(Duration.ofMillis(5000), () -> {
            Smethods.trysudoku(sudoku, (byte)0);
        });*/
    }

    // test trySudoku on a valid board with 1 as start step
    @Test
    void testTrySudokuSecondStep() {
        // fill in first cell
        testSelectFirstStep();
        // complete the board
        Smethods.trysudoku(sudoku, (byte)1);
        // verify that board is solved correctly
        int count = 0;
        int[][] board = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
        assertTrue(isSolved(board));
    }

    // test trySudoku on an already completed board (resolve the board starting from step 0)
    @Test
    void testTrySudokuResolve() {
        testTrySudoku();
        // save copy of saved board
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        // call trySudoku again
        Smethods.trysudoku(sudoku, (byte)0);
        // verify that board is solved correctly
        int count = 0;
        int[][] board = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
        assertTrue(isSolved(board));
        // check if the solutions are the same
        assertArrayEquals(copy,sudoku);
    }

    // test trySudoku on an already completed board (resolve the board starting from step 80)
    @Test
    void testTrySudokuResolve1() {
        testTrySudoku();
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        // call trySudoku with start step 80 (resolves only the last cell)
        Smethods.trysudoku(sudoku, (byte)80);
        // check that solutions are the same
        assertArrayEquals(copy,sudoku);
    }

    // test trySudoky on an already completed board (resolve the board starting from step 40)
    @Test
    void testTrySudokuResolveHalf() {
        testTrySudoku();
        // save copy of saved board
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        // call trySudoku with start step 40 (resolve half the board)
        Smethods.trysudoku(sudoku, (byte)40);
        // verify that board is solved correctly
        int count = 0;
        int[][] board = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
        assertTrue(isSolved(board));
        // check if the solutions are the same
        assertArrayEquals(copy,sudoku);
    }

}
