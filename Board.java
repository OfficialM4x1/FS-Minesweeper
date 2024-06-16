//@author class Silas Abler
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Board extends JPanel {

    //Intilize content game board 
    private int rows;
    private int cols;
    private int mines;
    private boolean gameOver;

    private JButton[][] buttons;
    boolean[][] isMine;
    private boolean[][] isRevealed;
    private boolean[][] isFlaged;
    private JPanel topBoardPanel;
    private JPanel bottomBoardPanel;
    private JPanel bottomPanel;
    //HIER NOCH QUELLE ZU DEN BILDERN EINFUEGEN
    ImageIcon mineicon = new ImageIcon("images/mine.png");
    ImageIcon flaggeicon = new ImageIcon("images/flagge.png");
    ImageIcon oneicon = new ImageIcon("images/icon1.png");
    ImageIcon twoicon = new ImageIcon("images/icon2.png");
    ImageIcon threeicon = new ImageIcon("images/icon3.png");
    ImageIcon fouricon = new ImageIcon("images/icon4.png");
    ImageIcon fiveicon = new ImageIcon("images/icon5.png");
    ImageIcon sixicon = new ImageIcon("images/icon6.png");
    ImageIcon sevenicon = new ImageIcon("images/icon7.png");
    ImageIcon eighticon = new ImageIcon("images/icon8.png");
    
    String username;
    JLabel usernamLabel;
    Timer timer = new Timer(username, null);

    //Constructor of a board 
    //pass number of mines, rows and columns depending on the difficulty

    /**
     * 
     * @param rows depending on difficulty
     * @param cols depending on difficulty
     * @param mines pass the number of mines 
     * constructor of a board starting with a non-gameover game
     */
    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.gameOver = false;
        
        /**
         * set up the info board for username, size, layout, timer etc.
         */
        topBoardPanel = new JPanel();
        topBoardPanel.setPreferredSize(new Dimension(600, 30)); 
        topBoardPanel.setBackground(Color.WHITE);
        usernamLabel = new JLabel(username);
        topBoardPanel.add(usernamLabel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        timer.setBackground(Color.WHITE);
        topBoardPanel.add(timer);
        add(topBoardPanel, BorderLayout.NORTH);

        //Layout for gameboard
        bottomBoardPanel = new JPanel();
        bottomBoardPanel.setPreferredSize(new Dimension(700, 700)); 
        add(bottomBoardPanel, BorderLayout.CENTER);

        //bottom panel tto have some space between the edge of the screen and the game
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(700, 10)); 
        bottomPanel.setBackground(Color.WHITE);
        add(bottomPanel, BorderLayout.SOUTH);

            
        bottomBoardPanel.setLayout(new GridLayout(rows, cols));

        /**
         * set up buttons 
         */

        buttons = new JButton[rows][cols];
        isMine = new boolean[rows][cols];
        isRevealed = new boolean[rows][cols];
        isFlaged = new boolean[rows][cols];
        placeMines();
        timer.startTimer();

         
        /**
         *  loop through the rows
         */
        for (int i = 0; i < rows; i++) {
            /**
             * double loop for making first the whole column and then the next one
             */
            for (int j = 0; j < cols; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j] = new JButton();
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorderPainted(true);
                //make chess patern as gameboard
                colourSquare(i, j, false);
                
                buttons[i][j].setBackground(Color.DARK_GRAY);

                buttons[i][j].addMouseListener(new MouseAdapter() { //Mouse listener to differnetiate between left and right click on mouse
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            //checks if the game is over and if mine is flagged 
                            if (!gameOver && !isFlaged[row][col]) {
                                revealCell(row, col);
                                if (solved(rows, cols) && solvedmines(rows, cols)) {
                                    JOptionPane.showMessageDialog(Board.this, "You won the game!");
                                    gameOver = true;
                                    timer.stopTimer();
                                }
                            }
                        }
                        //ability to flag mines with aright click and deflag 
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            if (!gameOver) {
                                if (solved(rows, cols) && solvedmines(rows, cols)) {
                                    JOptionPane.showMessageDialog(Board.this, "You won the game!");
                                    gameOver = true;
                                    timer.stopTimer();
                                }
                                //unflag field 
                                if (isFlaged[row][col] == true) {
                                    colourSquare(row, col, isRevealed[row][col]);
                                    isFlaged[row][col] = false;
                                    buttons[row][col].setIcon(null);
                                    buttons[row][col].setBackground(Color.DARK_GRAY);
                                }
                                else {
                                    buttons[row][col].setBackground(Color.LIGHT_GRAY);
                                    buttons[row][col].setIcon(flaggeicon);
                                    isFlaged[row][col] = true;
                                }
                            }
                        }
                    } 
                });
                bottomBoardPanel.add(buttons[i][j]);
            }
        }
    
    }

    /**
     * 
     * @param row rownumber
     * @param col column number
     * @return true or false if a mine is place on the specified field
     */
    public boolean checkmine(int row, int col) {
        return isMine[row][col];
    }
/**
 * 
 * @param row row number
 * @param col col number 
 * @return checks whether the mionde is revealed on the specified field
 */
    public boolean checkrevealed(int row, int col) {
        return isRevealed[row][col];
    }
/**
 * 
 * @param username inputted name by the user at the beginning of the game
 */
    public void setusername(String username) {
        usernamLabel.setText(username);
    }
/**
 * 
 * @param row number
 * @param col number
 * @return true if all non-mine cells are revealed, false otherwise
 */
    public boolean solved(int row, int col) {
        int counter = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (isRevealed[i][j]) {
                    counter++;
                }
            }
        }
        return mines == row*col-counter;
    }

    public boolean solvedmines(int row, int col) {
        int counter = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (isFlaged[i][j] && isMine[i][j]) {
                    counter++;
                }
            }
        }
        return mines - 1 == counter;
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

    public void revealCell(int row, int col) {
        buttons[row][col].setBackground(Color.LIGHT_GRAY);
        //check if field is already clicked
        if (isRevealed[row][col]) return; 

        //set field clicked to true
        isRevealed[row][col] = true;
        
        //check if clicked field is a mine 
        if (isMine[row][col]) {
            buttons[row][col].setBackground(Color.LIGHT_GRAY);
            buttons[row][col].setIcon(mineicon);
            JOptionPane.showMessageDialog(this, "Game Over! You clicked on a mine.");
            gameOver = true;
            timer.stopTimer();

            revealAllMines();
            
        } else {
            //get number of bodering mines 
            int adjacentMines = countAdjacentMines(row, col);
            
            //change colour to clicked field colour 
            colourSquare(row, col, true);

            //incase the field does not border any mine the fiels around it are openend              
            if (countAdjacentMines(row, col)== 0) {
                buttons[row][col].setIcon(null);
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
                buttons[row][col].setIcon(null);
                switch (adjacentMines) {
                    case 1:
                        buttons[row][col].setIcon(oneicon);    
                    break;
                    case 2:
                        buttons[row][col].setIcon(twoicon); 
                    break;
                    case 3:
                        buttons[row][col].setIcon(threeicon); 
                    break;
                    case 4:
                        buttons[row][col].setIcon(fouricon);     
                    break;
                    case 5:
                        buttons[row][col].setIcon(fiveicon);     
                    break;
                    case 6:
                        buttons[row][col].setIcon(sixicon);     
                    break;
                    case 7:
                        buttons[row][col].setIcon(sevenicon);     
                    break;
                    case 8:
                        buttons[row][col].setIcon(eighticon);    
                    break;
                    default:
                        buttons[row][col].setIcon(null);
                    break;
                }
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
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                    buttons[i][j].setIcon(mineicon);
                    
                }
            }
        }
    }

    //get Method for GameOver
    /**
     * 
     * @return getter for gameover 
     */
    public boolean getGameOver() {
        return this.gameOver;
    }

    //winning function 

    //give row i and coloumn j plus True incase the field is revield (to adjust the colour)
    /**
     * 
     * @param i row number
     * @param j column number 
     * @param revealed
     */
    private void colourSquare(int i, int j, boolean revealed) {
        buttons[i][j].setBackground(Color.LIGHT_GRAY);       
    }
}