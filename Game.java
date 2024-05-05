import javax.swing.*;

public class Game extends JFrame {
    private final int ROWS = 10;
    private final int COLS = 10;
    private final int MINES = 20;

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