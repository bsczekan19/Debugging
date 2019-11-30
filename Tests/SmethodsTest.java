import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SmethodsTest {
    private Smethods s;
    private byte[][] sudoku;

    @BeforeEach
    void init() {
        s = new Smethods();
        sudoku = new byte[729][82];
    }

    @Test
    void testStart() {
        Smethods.start(sudoku);
        for (int i = 0; i < 729; i++) {
            assertEquals(1+(i%9),sudoku[i][0]);
        }
        for (int i = 1; i < 82; i++) {
            assertEquals(0,sudoku[0][i]);
        }
    }

    @Test
    void testStartNull() {
        sudoku = null;
        assertThrows(NullPointerException.class, () -> Smethods.start(sudoku));
    }

    @Test
    void testSelectInvalidStep() {
        Smethods.start(sudoku);
        byte ret = Smethods.select(sudoku, (byte)0, (byte)0, (byte)1);
        assertEquals((byte)1, ret);
        ret = Smethods.select(sudoku, (byte)0, (byte)0, (byte)4);
        assertEquals((byte)4, ret);
    }

    @Test
    void testSelectAlreadyFilled() {
        Smethods.start(sudoku);
        byte ret = Smethods.select(sudoku, (byte)1, (byte)1, (byte)0);
        assertEquals((byte)1, ret);
        ret = Smethods.select(sudoku, (byte)1, (byte)1, (byte)1);
        assertEquals((byte)1, ret);
        ret = Smethods.select(sudoku, (byte)4, (byte)1, (byte)1);
        assertEquals((byte)1, ret);
    }

    @Test
    void testSelectNegativeStep() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Smethods.select(sudoku, (byte)0, (byte)0, (byte)-1));
    }

    @Test
    void testSelect81Step() {
        testTrySudoku();
        // all cells already marked so method will return early - first if condition is true
        assertEquals(81, Smethods.select(sudoku, (byte)0, (byte)0, (byte)81));
    }

    @Test
    void testSelectOver81Step() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Smethods.select(sudoku, (byte)0, (byte)0, (byte)82));
    }

    @Test
    void testSelectNegativeNum() {
        Smethods.start(sudoku);
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  Smethods.select(sudoku, (byte)-1, (byte)0, (byte)0));
    }

    // ok because the program prevents user from actually putting in num over 8
    @Test
    void testSelectAbove8Num() {
        Smethods.start(sudoku);
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  Smethods.select(sudoku, (byte)9, (byte)0, (byte)0));
    }

    @Test
    void testSelectNegativePosition() {
        Smethods.start(sudoku);
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  Smethods.select(sudoku, (byte)0, (byte)-1, (byte)0));
    }

    @Test
    void testSelectAbove80Position() {
        Smethods.start(sudoku);
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  Smethods.select(sudoku, (byte)0, (byte)81, (byte)0));
    }

    @Test
    void testSelectInvalidBoard() {
        for(int i = 0; i < 729; i++) {
            sudoku[i][0] = (byte)i;
        }
        ArrayList<Integer> zeros = new ArrayList<>();
        // first cell, fill with number 3
        byte ret = Smethods.select(sudoku, (byte)2, (byte)0, (byte)0);
        assertEquals((byte)1, ret);
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

    @Test
    void testSelectFirstStep() {
        Smethods.start(sudoku);
        ArrayList<Integer> zeros = new ArrayList<>();
        // first cell, fill with number 3
        byte ret = Smethods.select(sudoku, (byte)2, (byte)0, (byte)0);
        assertEquals((byte)1, ret);
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

    @Test
    void testSelectNextSteps() {
        testSelectFirstStep();
        byte ret = Smethods.select(sudoku, (byte)8, (byte)80, (byte)1);
        assertEquals((byte)2, ret);
        ret = Smethods.select(sudoku, (byte)0, (byte)24, (byte)2);
        assertEquals((byte)3, ret);
    }

    @Test
    void testTrySudokuSecondStep() {
        testSelectFirstStep();
        Smethods.trysudoku(sudoku, (byte)1);
        int count = 0;
        int[][] board = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
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
                assertEquals(1,row[i]);
                assertEquals(1,col[i]);
                assertEquals(1,grid[i]);
            }
        }
    }

    @Test
    void testTrySudokuNegativeStart() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Smethods.trysudoku(sudoku, (byte)-1));
    }

    @Test
    void testTrySudoku81Step() {
        Smethods.start(sudoku);
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        assertArrayEquals(copy,sudoku);
        Smethods.trysudoku(sudoku, (byte)81);
        assertEquals(81,MySudoku.step);
        assertArrayEquals(copy,sudoku);
    }

    @Test
    void testTrySudokuOver81Step() {
        Smethods.start(sudoku);
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        assertArrayEquals(copy,sudoku);
        Smethods.trysudoku(sudoku, (byte)82);
        assertEquals(82,MySudoku.step);
        assertArrayEquals(copy,sudoku);
    }

    @Test
    void testTrySudokuInvalidStartStep() {
        Smethods.start(sudoku);
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        assertArrayEquals(copy,sudoku);
        Smethods.trysudoku(sudoku, (byte)1);
        assertArrayEquals(copy,sudoku);
    }

    @Test
    void testTrySudoku() {
        Smethods.start(sudoku);
        Smethods.trysudoku(sudoku, (byte)0);
        int count = 0;
        int[][] board = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
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
                assertEquals(1,row[i]);
                assertEquals(1,col[i]);
                assertEquals(1,grid[i]);
            }
        }
    }

    // add timeout
    @Test
    void testTrySudokuInvalidBoard() {
        for(int i = 0; i < 729; i++) {
            sudoku[i][0] = (byte)i;
        }
        Smethods.trysudoku(sudoku, (byte)0);
        int count = 0;
        int[][] board = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
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
                assertEquals(1,row[i]);
                assertEquals(1,col[i]);
                assertEquals(1,grid[i]);
            }
        }
    }

    @Test
    void testTrySudokuResolve() {
        testTrySudoku();
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        int count = 0;
        int[][] board1 = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board1[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
        Smethods.trysudoku(sudoku, (byte)0);  // should be same bc only 1 left to fill out
        count = 0;
        int[][] board2 = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board2[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
        assertArrayEquals(copy,sudoku);
        assertArrayEquals(board1, board2);
    }

    @Test
    void testTrySudokuChangeResolveOne() {
        testTrySudoku();
        byte[][] copy = new byte[729][82];
        for (int i = 0; i < 729; i++) {
            System.arraycopy( sudoku[i], 0, copy[i], 0, sudoku[i].length );
        }
        int count = 0;
        int[][] board1 = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board1[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
        Smethods.trysudoku(sudoku, (byte)80);  // should be same bc only 1 left to fill out
        count = 0;
        int[][] board2 = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board2[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
        assertArrayEquals(copy,sudoku);
        assertArrayEquals(board1, board2);
    }

    @Test
    void testTrySudokuChangeResolveHalf() {
        testTrySudoku();
        Smethods.trysudoku(sudoku, (byte)40); // not necessarily same solution but will be valid solution
        int count = 0;
        int[][] board = new int[9][9];
        for (int i = 0; i < 729; i++) {
            if (sudoku[i][81] != 0) {
                board[count/9][count%9] = sudoku[i][81] - 11;
                count++;
            }
        }
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
                assertEquals(1,row[i]);
                assertEquals(1,col[i]);
                assertEquals(1,grid[i]);
            }
        }
    }

}
