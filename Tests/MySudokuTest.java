import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MySudokuTest {
    private MySudoku sudoku;

    @BeforeEach
    void init() {
        sudoku = new MySudoku();
    }

    @Test
    void testInitialSudokuArray(){
        assertEquals(729,sudoku.sudoku.length);
        assertEquals(82,sudoku.sudoku[0].length);
    }

    @Test
    void testInitialStep() {
        assertEquals(0, sudoku.step);
    }

    @Test
    void testShowGUI() {
        sudoku.ShowGUI();
    }

    @Test
    void testMain() {
        String[] args = null;
        sudoku.main(args);
    }
}
