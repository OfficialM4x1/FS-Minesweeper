//@author class Silas Abler
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Board extends JPanel {
    
    //Intilize content game board 
    private int rows;
    private int cols;
    private int mines;

    private JButton[][] buttons;
    private boolean[][] isMine;
    private boolean[][] isRevealed;

    //board size 
    private final int ROWS = 18;
    private final int COLS = 18;
    private final int MINES = 10;

    //Constructor
    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        
        //Layout for gameboard
        setLayout(new GridLayout(rows, cols));

        buttons = new JButton[rows][cols];
        isMine = new boolean[rows][cols];
        isRevealed = new boolean[rows][cols];

        initializeGame();

        for (int i = 0; i < rows; i = i+2) {
            for 
        }
    
    }

}
