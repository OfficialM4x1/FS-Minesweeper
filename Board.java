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

    //Constructor of a board 
    //pass number of mines, rows and columns depending on the difficulty
    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        
        //Layout for gameboard
        setLayout(new GridLayout(rows, cols));

        buttons = new JButton[rows][cols];
        isMine = new boolean[rows][cols];
        isRevealed = new boolean[rows][cols];

        placeMines();

        //loops though the rows 
        for (int i = 0; i < rows; i++) {
            //loops through the whole column before going to the next row
            for (int j = 0; j < cols; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j] = new JButton();
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorderPainted(true);
                //make chess patern as gameboard
                colourSquare(i, j, false);

                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        revealCell(row, col);
                    }
                });
                add(buttons[i][j]);
            }
        }
    
    }

    private void placeMines() {
        //Place mines randomly
        int minesPlaced = 0;
        while (minesPlaced < mines) {
            int row = (int)(Math.random() * rows);
            int col = (int)(Math.random() * cols);
            if (!isMine[row][col]) {
                isMine[row][col] = true;
                minesPlaced++;
            }
        }
    }

    private void revealCell(int row, int col) {
        if (isRevealed[row][col]) return; // Already revealed
        isRevealed[row][col] = true;
        colourSquare(row, col, true);
        
        //check if clicked field is a mine 
        if (isMine[row][col]) {
            buttons[row][col].setBackground(Color.red);
            JOptionPane.showMessageDialog(this, "Game Over! You clicked on a mine.");
            revealAllMines();
        } else {
            //count distance to mine
            int adjacentMines = countAdjacentMines(row, col);
            buttons[row][col].setText(Integer.toString(adjacentMines));
            //colour 
            colourSquare(row, col, true);
 
        }
    }
    // check how many mines are around a field 
    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                if (isMine[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    //reveals all mines 
    private void revealAllMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isMine[i][j]) {
                    buttons[i][j].setText("*");
                    buttons[i][j].setBackground(Color.red);
                    
                }
            }
        }
    }
    //give row i and coloumn j plus True incase the field is revield (to adjust the colour)
    private void colourSquare(int i, int j, boolean revealed) {
        if (i % 2 == 0) {
            if (j % 2 ==0) { 
                if (revealed) {
                    buttons[i][j].setBackground(Color.DARK_GRAY);
                }
                else {
                    buttons[i][j].setBackground(Color.BLUE);
                }
            }
            else {
                
                if (revealed) {
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                }
                else {
                    buttons[i][j].setBackground(Color.cyan);
                }
            }
        }
        else {
            if (j % 2 !=0) {
                if (revealed) {
                    buttons[i][j].setBackground(Color.DARK_GRAY);
                }
                else {
                    buttons[i][j].setBackground(Color.BLUE);
                }
            }
            else {
                if (revealed) {
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                }
                else {
                    buttons[i][j].setBackground(Color.cyan);
                }
            }
        }
    }
}
