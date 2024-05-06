import javax.swing.*;

public class Game extends JFrame {
    private final int ROWS = 18;
    private final int COLS = 18;
    private final int MINES = 40;

    private Board board;

    public Game() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        board = new Board(ROWS, COLS, MINES);
        add(board);

        setVisible(true);
    }
}