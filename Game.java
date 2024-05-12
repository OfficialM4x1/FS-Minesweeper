import javax.swing.*;

public class Game extends JFrame {
    private final int ROWS = 17;
    private final int COLS = 17;
    private final int MINES = 40;

    private Board board;

    public Game() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(680, 680);
        setLocationRelativeTo(null);

        board = new Board(ROWS, COLS, MINES);
        add(board);

        setVisible(true);
    }
}