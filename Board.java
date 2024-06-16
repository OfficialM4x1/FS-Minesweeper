//@author class Silas Abler
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Board extends JPanel {

    // Initialize content game board 
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
    // HIER NOCH QUELLE ZU DEN BILDERN EINFUEGEN - done
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

    /**
     * Constructor of a board
     *
     * @param rows   depending on difficulty
     * @param cols   depending on difficulty
     * @param mines  pass the number of mines
     * Constructor of a board starting with a non-gameover game
     */
    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.gameOver = false;
        
        /**
         * Set up the info board for username, size, layout, timer etc.
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

        // Layout for gameboard
        bottomBoardPanel = new JPanel();
        bottomBoardPanel.setPreferredSize(new Dimension(700, 700)); 
        add(bottomBoardPanel, BorderLayout.CENTER);

        // Bottom panel to have some space between the edge of the screen and the game
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(700, 10)); 
        bottomPanel.setBackground(Color.WHITE);
        add(bottomPanel, BorderLayout.SOUTH);

        bottomBoardPanel.setLayout(new GridLayout(rows, cols));

        /**
         * Set up buttons
         */
        buttons = new JButton[rows][cols];
        isMine = new boolean[rows][cols];
        isRevealed = new boolean[rows][cols];
        isFlaged = new boolean[rows][cols];
        placeMines();
        timer.startTimer();

        /**
         * Loop through the rows
         */
        for (int i = 0; i < rows; i++) {
            /**
             * Double loop for making first the whole column and then the next one
             */
            for (int j = 0; j < cols; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j] = new JButton();
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorderPainted(true);
                // Make chess pattern as gameboard
                colourSquare(i, j, false);
                
                buttons[i][j].setBackground(Color.DARK_GRAY);

                buttons[i][j].addMouseListener(new MouseAdapter() { // Mouse listener to differentiate between left and right click on mouse
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            // Checks if the game is over and if mine is flagged 
                            if (!gameOver && !isFlaged[row][col]) {
                                revealCell(row, col);
                                if (solved(rows, cols) && solvedmines(rows, cols)) {
                                    JOptionPane.showMessageDialog(Board.this, "You won the game!");
                                    gameOver = true;
                                    timer.stopTimer();
                                }
                            }
                        }
                        // Ability to flag mines with a right click and deflag 
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            if (!gameOver) {
                                if (solved(rows, cols) && solvedmines(rows, cols)) {
                                    JOptionPane.showMessageDialog(Board.this, "You won the game!");
                                    gameOver = true;
                                    timer.stopTimer();
                                }
                                // Unflag field 
                                if (isFlaged[row][col] == true) {
                                    colourSquare(row, col, isRevealed[row][col]);
                                    isFlaged[row][col] = false;
                                    buttons[row][col].setIcon(null);
                                    buttons[row][col].setBackground(Color.DARK_GRAY);
                                } else {
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
     * @param row row number
     * @param col column number
     * @return true or false if a mine is placed on the specified field
     */
    public boolean checkmine(int row, int col) {
        return isMine[row][col];
    }

    /**
     * 
     * @param row row number
     * @param col column number 
     * @return true or false if a mine is revealed on the specified field
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
     * @param row number of rows
     * @param col number of columns
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
        return mines == row * col - counter;
    }

    /**
     * 
     * @param row number of rows
     * @param col number of columns
     * @return true if all flagged cells are mines and one mine is left, false otherwise
     */
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

    /**
     * Places mines randomly on the game board
     */
    private void placeMines() {
        // Place mines randomly
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

    /**
     * Reveals the cell at the specified location
     * 
     * @param row row number
     * @param col column number
     */
    public void revealCell(int row, int col) {
        buttons[row][col].setBackground(Color.LIGHT_GRAY);
        // Check if field is already clicked
        if (isRevealed[row][col]) return; 

        // Set field clicked to true
        isRevealed[row][col] = true;
        
        // Check if clicked field is a mine 
        if (isMine[row][col]) {
            buttons[row][col].setBackground(Color.LIGHT_GRAY);
            buttons[row][col].setIcon(mineicon);
            JOptionPane.showMessageDialog(this, "Game Over! You clicked on a mine.");
            gameOver = true;
            timer.stopTimer();

            revealAllMines();
            
        } else {
            // Get number of bordering mines 
            int adjacentMines = countAdjacentMines(row, col);
            
            // Change color to clicked field color 
            colourSquare(row, col, true);

            // In case the field does not border any mine the fields around it are opened              
            if (countAdjacentMines(row, col) == 0) {
                buttons[row][col].setIcon(null);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        // Implement catch method in case the field is not on the board and Java throws a java.lang.ArrayIndexOutOfBoundsException 
                        try {
                            revealCell(row + i, col + j);
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    } 
                }
            } else {
                // Set Text of Distance 
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

    /**
     * Checks how many mines are around a field
     * 
     * @param row row number
     * @param col column number
     * @return the number of mines adjacent to the specified field
     */
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

    /**
     * Reveals all mines on the board
     */
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

    /**
     * 
     * @return getter for gameOver
     */
    public boolean getGameOver() {
        return this.gameOver;
    }

    /**
     * Colors the square at the specified location
     * 
     * @param i row number
     * @param j column number 
     * @param revealed true if the cell is revealed, false otherwise
     */
    private void colourSquare(int i, int j, boolean revealed) {
        buttons[i][j].setBackground(Color.LIGHT_GRAY);       
    }
}
