//@author class Lennart Bosse
package src;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    @Test
    public void testBoardInitialization() {
        Board board = new Board(10, 10, 10, "Standard");
        assertEquals(10, board.getRows());
        assertEquals(10, board.getCols());
        assertEquals(10, board.getMines());
        assertFalse(board.getGameOver());
    }

    @Test
    public void testPlaceMines() {
        Board board = new Board(10, 10, 10, "Standard");
        int mineCount = 0;
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (board.checkmine(i, j)) {
                    mineCount++;
                }
            }
        }
        assertEquals(10, mineCount);
    }

    @Test
    public void testRevealCell() {
        Board board = new Board(10, 10, 10, "Standard");
        board.revealCell(0, 0);
        assertTrue(board.checkrevealed(0, 0));
    }

    @Test
    public void testGameOverOnMine() {
        Board board = new Board(10, 10, 10, "Standard");
        // Find a mine to click on
        int mineRow = -1, mineCol = -1;
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (board.checkmine(i, j)) {
                    mineRow = i;
                    mineCol = j;
                    break;
                }
            }
            if (mineRow != -1) break;
        }
        assertNotEquals(-1, mineRow);
        assertNotEquals(-1, mineCol);

        board.revealCell(mineRow, mineCol);
        assertTrue(board.getGameOver());
    }

    @Test
    public void testSetUsername() {
        Board board = new Board(10, 10, 10, "Standard");
        board.setusername("TestUser");
        assertEquals("TestUser", board.usernamLabel.getText());
    }

    @Test
    public void testSolved() {
        Board board = new Board(3, 3, 1, "Standard");
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (!board.checkmine(i, j)) {
                    board.revealCell(i, j);
                }
            }
        }
        assertTrue(board.solved(3, 3));
    }

    @Test
    public void testSolvedMines() {
        Board board = new Board(3, 3, 1, "Standard");

        // Get the positions of the mines
        boolean[][] isMine = new boolean[board.getRows()][board.getCols()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                isMine[i][j] = board.checkmine(i, j);
            }
        }

        // Flag all mines
        boolean[][] isFlaged = board.getisFlaged();
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (isMine[i][j]) {
                    isFlaged[i][j] = true;
                }
            }
        }

        // Check if all mines are flagged
        assertFalse(board.solvedmines(3, 3));
    }
}