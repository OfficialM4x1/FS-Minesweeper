//@author class Silas Abler
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Board extends JPanel {
    
    //Intilize content game board 
    private int rows;
    private int cols;
    private int mines;
    private int len;
    private boolean test;

    private JButton[][] buttons;
    private boolean[][] isMine;
    private boolean[][] isRevealed;
    private boolean[][] isFlaged;

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
        isFlaged = new boolean[rows][cols];

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

                buttons[i][j].addMouseListener(new MouseAdapter() { //Mouse listener to differnetiate between left and right click on mouse
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            revealCell(row, col);
                        }
                        //ability to flag mines with aright click and deflag 
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            if (isFlaged[row][col] == true) {
                                colourSquare(row, col, isRevealed[row][col]);
                                isFlaged[row][col] = false;
                            }
                            else {
                                buttons[row][col].setBackground(Color.MAGENTA);
                                isFlaged[row][col] = true;
                            }
                        }
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
        //check if field is already clicked
        if (isRevealed[row][col]) return; 

        //set field clicked to true
        isRevealed[row][col] = true;
        
        //check if clicked field is a mine 
        if (isMine[row][col]) {
            buttons[row][col].setBackground(Color.red);
            JOptionPane.showMessageDialog(this, "Game Over! You clicked on a mine.");
            revealAllMines();
        } else {
            //get number of bodering mines 
            int adjacentMines = countAdjacentMines(row, col);

            //change colour to clicked field colour 
            colourSquare(row, col, true);

            //incase the field does not border any mine the fiels around it are openend              
            if (countAdjacentMines(row, col)== 0) {
                for (int i = -1; i <=1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        //Implement catch method in case the field is not on the board and java throws a java.lang.ArrayIndexOutOfBoundsException 
                        try {
                            revealCell(row + i, col + j);
                        }
                        catch (java.lang.ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    } 
                }
            }
            else {
                //set Text of Distance 
                buttons[row][col].setText(Integer.toString(adjacentMines));
            }
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
