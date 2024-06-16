import org.junit.Test;
import org.w3c.dom.events.MouseEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.swing.JButton;
import java.awt.Color;
import org.junit.BeforeEach;

public class BoardTest {
    private Board board;
    private int rows = 10;
    private int cols = 10;
    private int mines = 10;

    @BeforeEach
    public void setUp() {
        board = new Board(rows, cols, mines);
    }

    @Test
    public void testInitialBoardConfiguration() {
        assertEquals(rows, board.getRows());
        assertEquals(cols, board.getCols());
        assertEquals(mines, board.getMines());
        assertTrue(!board.getGameOver());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                assertTrue(board.checkrevealed(i, j) == false);
            }
        }
    }

    @Test
    public void testRevealCell() {
        board.revealCell(0, 0);
        assertTrue(board.checkrevealed(0, 0));
    }

    @Test
    public void testRevealMineCell() {
        // Directly manipulate the isMine field for testing
        try {
            var isMineField = Board.class.getDeclaredField("isMine");
            isMineField.setAccessible(true);
            boolean[][] isMine = (boolean[][]) isMineField.get(board);
            isMine[1][1] = true;

            board.revealCell(1, 1);
            assertEquals(Color.LIGHT_GRAY, board.buttons[1][1].getBackground());
            assertEquals(board.mineicon, board.buttons[1][1].getIcon());
            assertTrue(board.getGameOver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRevealEmptyCellWithAdjacentMines() {
        // Directly manipulate the isMine field for testing
        try {
            var isMineField = Board.class.getDeclaredField("isMine");
            isMineField.setAccessible(true);
            boolean[][] isMine = (boolean[][]) isMineField.get(board);
            isMine[0][1] = true;
            isMine[1][0] = true;
            isMine[1][1] = true;

            board.revealCell(0, 0);
            assertEquals(Color.LIGHT_GRAY, board.buttons[0][0].getBackground());
            assertEquals(board.threeicon, board.buttons[0][0].getIcon());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRevealEmptyCellWithoutAdjacentMines() {
        board.revealCell(2, 2);
        assertEquals(Color.LIGHT_GRAY, board.buttons[2][2].getBackground());

        // Verify recursive call by checking adjacent cells
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (!(i == 0 && j == 0)) {
                        assertTrue(board.checkrevealed(2 + i, 2 + j));
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
    }

    @Test
    public void testFlagCell() {
        board.buttons[0][0].doClick(MouseEvent.BUTTON3); // Right-click to flag
        assertEquals(board.flaggeicon, board.buttons[0][0].getIcon());
        assertTrue(board.isFlaged[0][0]);
    }

    @Test
    public void testUnflagCell() {
        board.buttons[0][0].doClick(MouseEvent.BUTTON3); // Right-click to flag
        board.buttons[0][0].doClick(MouseEvent.BUTTON3); // Right-click to unflag
        assertEquals(null, board.buttons[0][0].getIcon());
        assertTrue(!board.isFlaged[0][0]);
    }

    @Test
    public void testSetUsername() {
        board.setusername("TestUser");
        assertEquals("TestUser", board.usernamLabel.getText());
    }
}