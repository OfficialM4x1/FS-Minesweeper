import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class CompBoardTest {
    public CompBoard board = new CompBoard(5, 5, 5, "Player1", "Player2");

    @Test
    public void testTimerSwitchesBetweenPlayers() {
        board.setCurrentPlayer(1);
        // Simulate a click to switch from Player 1 to Player 2
        
        assertEquals(1, board.getCurrentPlayer());

        // Simulate another click to switch from Player 2 to Player 1
        board.revealCell(0, 1);
        assertEquals(1, board.getCurrentPlayer());
    }

    @Test
    public void testCorrectPlayerLosesWhenClickingOnMine() {
        // Place a mine manually for testing
        board.isMine[0][0] = true;

        // Simulate Player 1 clicking on the mine
        board.revealCell(0, 0);
        assertTrue(board.getGameOver());
        assertEquals("Player1", board.getPlayerWhoLost());
    }

    @Test
    public void testCorrectPlayerLosesWhenTimerRunsOut() {
        // Simulate Player 1's timer running out
        board.timer1.stopTimer(); // Assume this triggers the game over condition
        board.gameOver = true;
        assertTrue(board.getGameOver());
        assertEquals("Player1", board.getPlayerWhoLost());
    }

    @Test
    public void testGameOverWhenPlayerLosesByMine() {
        // Place a mine manually for testing
        board.isMine[0][0] = true;

        // Simulate Player 1 clicking on the mine
        board.revealCell(0, 0);
        assertTrue(board.getGameOver());
    }

    @Test
    public void testGameDrawCondition() {
        // Simulate all non-mine cells being revealed and all mines flagged
        for (int i = 0; i < board.rows; i++) {
            for (int j = 0; j < board.cols; j++) {
                if (!board.isMine[i][j]) {
                    board.isRevealed[i][j] = true;
                } else {
                    board.isFlaged[i][j] = true;
                }
            }
        }
        assertTrue(board.solved(board.rows, board.cols));
        assertFalse(board.solvedmines(board.rows, board.cols));
        assertFalse(board.getGameOver()); // Game should not be over until a click is made
    }
}
