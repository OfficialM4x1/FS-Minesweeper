import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.swing.*;
import java.awt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;
    private int rows = 10;
    private int cols = 10;
    private int mines = 10;
    private JButton[][] buttons;
    private boolean[][] isMine;
    private boolean[][] isRevealed;
    private boolean[][] isFlaged;

    @BeforeEach
    public void setUp() {
        board = spy(new Board(rows, cols, mines));
        buttons = new JButton[rows][cols];
        isMine = new boolean[rows][cols];
        isRevealed = new boolean[rows][cols];
        isFlaged = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new JButton();
            }
        }

        // Set private fields using reflection
        try {
            var buttonsField = Board.class.getDeclaredField("buttons");
            buttonsField.setAccessible(true);
            buttonsField.set(board, buttons);

            var isMineField = Board.class.getDeclaredField("isMine");
            isMineField.setAccessible(true);
            isMineField.set(board, isMine);

            var isRevealedField = Board.class.getDeclaredField("isRevealed");
            isRevealedField.setAccessible(true);
            isRevealedField.set(board, isRevealed);

            var isFlagedField = Board.class.getDeclaredField("isFlaged");
            isFlagedField.setAccessible(true);
            isFlagedField.set(board, isFlaged);

            var timerField = Board.class.getDeclaredField("timer");
            timerField.setAccessible(true);
            Timer mockTimer = mock(Timer.class);
            timerField.set(board, mockTimer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    // Überprüft, dass das Aufdecken eines bereits aufgedeckten Feldes keine weiteren Aktionen ausführt. Dies stellt sicher, dass keine unnötige rekursive Aufrufe oder Änderungen stattfinden.
    public void testRevealAlreadyRevealedCell() {
        isRevealed[0][0] = true;
        board.revealCell(0, 0);
        assertEquals(Color.LIGHT_GRAY, buttons[0][0].getBackground());
        verify(board, never()).revealCell(anyInt(), anyInt());
    }

    @Test
    //Überprüft, dass beim Aufdecken einer Mine das Spiel beendet wird, das Minen-Icon angezeigt wird, und der Timer gestoppt wird.
    public void testRevealMineCell() {
        isMine[1][1] = true;
        board.revealCell(1, 1);
        assertEquals(Color.LIGHT_GRAY, buttons[1][1].getBackground());
        assertEquals(board.mineicon, buttons[1][1].getIcon());
        assertTrue(board.getGameOver());
        verify(board.timer).stopTimer();
    }

    @Test
    //Überprüft, dass beim Aufdecken eines leeren Feldes mit angrenzenden Minen die Anzahl der angrenzenden Minen korrekt angezeigt wird. Dies simuliert ein Feld, das von Minen umgeben ist, ohne diese zu berühren.
    public void testRevealEmptyCellWithAdjacentMines() {
        when(board.countAdjacentMines(2, 2)).thenReturn(3);
        board.revealCell(2, 2);
        assertEquals(Color.LIGHT_GRAY, buttons[2][2].getBackground());
        assertEquals(board.threeicon, buttons[2][2].getIcon());
    }

    @Test
    //Überprüft, dass beim Aufdecken eines leeren Feldes ohne angrenzende Minen die rekursive Aufdeckung der angrenzenden Felder korrekt ausgeführt wird. Dies stellt sicher, dass die rekursive Aufdeckung ordnungsgemäß funktioniert.
    public void testRevealEmptyCellWithoutAdjacentMines() {
        when(board.countAdjacentMines(3, 3)).thenReturn(0);
        board.revealCell(3, 3);
        assertEquals(Color.LIGHT_GRAY, buttons[3][3].getBackground());
        // Verify recursive call by checking adjacent cells
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (!(i == 0 && j == 0)) {
                        verify(board).revealCell(3 + i, 3 + j);
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
    }
}