import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BoardTest {
    Board board1 = new Board(10,20,30);
    @Test
    public void testgetRows() {
        
        assertEquals(board1.getRows(), 10);
    }

    @Test
    public void testgetCols() {
        
        assertEquals(board1.getCols(), 20);
    }

    @Test
    public void testgetMines() {
        
        assertEquals(board1.getMines(), 30);
    }

    
}
