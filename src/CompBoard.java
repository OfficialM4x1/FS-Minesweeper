package src;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 * The CompBoard class represents a Minesweeper game board for a competitive game with two players.
 * It handles the creation of the game board, placement of mines, and the interaction between players.
 * This class also manages the timers for both players and determines the winner based on the game rules.
 * 
 * Note: This class requires external image resources for the mine, flag, and number icons.
 * Make sure the images are located in the specified "images" folder.
 * 
 * @author Silas Abler
 */
class CompBoard extends JPanel {
    
    //Initiliaize content game board
    protected int rows;
    protected  int cols;
    private int mines;
    protected boolean gameOver;

    protected JButton[][] buttons;
    protected boolean[][] isMine;
    protected boolean[][] isRevealed;
    protected boolean[][] isFlaged;
    private JPanel topBoardPanel;
    private JPanel bottomBoardPanel;
    private JPanel bottomPanel;
    ImageIcon mineicon = new ImageIcon("src/images/mine.png");
    ImageIcon flaggeicon = new ImageIcon("src/images/flagge.png");
    ImageIcon oneicon = new ImageIcon("src/images/icon1.png");
    ImageIcon twoicon = new ImageIcon("src/images/icon2.png");
    ImageIcon threeicon = new ImageIcon("src/images/icon3.png");
    ImageIcon fouricon = new ImageIcon("src/images/icon4.png");
    ImageIcon fiveicon = new ImageIcon("src/images/icon5.png");
    ImageIcon sixicon = new ImageIcon("src/images/icon6.png");
    ImageIcon sevenicon = new ImageIcon("src/images/icon7.png");
    ImageIcon eighticon = new ImageIcon("src/images/icon8.png");
    String wontext = "The Game is over, you both survied the minefield!";
    String loosetext = "You clicked on a mine.";

    //Sounds for the game
    AudioClass sound = new AudioClass();

    private int currentPlayer;
    private Random random = new Random();
    private int counterRevealedcells;

    // Player 1 properties
    private String username1;
    private JLabel usernamLabel1;
    protected Timer timer1;

    // Player 2 properties
    private String username2;
    private JLabel usernamLabel2;
    private Timer timer2;

    /**
     * Constructor for CompBoard class.
     * 
     * @param rows number of rows on the game board
     * @param cols number of columns on the game board
     * @param mines number of mines on the game board
     * @param username1 name of player 1
     * @param username2 name of player 2
     */
    public CompBoard(int rows, int cols, int mines, String username1, String username2, String design) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.username1 = username1;
        this.username2 = username2;
        this.gameOver = false;
        this.counterRevealedcells = 0;

        //Calls method to switch the design
        changedesign(design);
        
        // Setup info panel for game with timer and usernames
        topBoardPanel = new JPanel();
        topBoardPanel.setPreferredSize(new Dimension(600, 30)); 
        topBoardPanel.setBackground(Color.WHITE);

        // Player 1 setup
        usernamLabel1 = new JLabel(username1); // Player 1 username
        this.timer1 = new Timer(username1, this);
        topBoardPanel.add(usernamLabel1);
        timer1.setBackground(Color.WHITE);
        timer1.setTimer(30); // Set start time 
        topBoardPanel.add(timer1);

        // Player 2 setup
        usernamLabel2 = new JLabel(username2);
        this.timer2 = new Timer(username2, this);
        topBoardPanel.add(usernamLabel2);
        timer2.setBackground(Color.WHITE);
        timer2.setTimer(30); // Set start time 
        topBoardPanel.add(timer2);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

        buttons = new JButton[rows][cols];
        isMine = new boolean[rows][cols];
        isRevealed = new boolean[rows][cols];
        isFlaged = new boolean[rows][cols];
        placeMines();
        
        currentPlayer = random.nextInt(2) + 1; // Decides randomly which player starts

        if (currentPlayer == 1) {
            JOptionPane.showMessageDialog(this, "Player 1: " + username1 + " starts", "Information", JOptionPane.INFORMATION_MESSAGE);
            timer1.startTimer();
        } else {
            JOptionPane.showMessageDialog(this, "Player 2: " + username2 + " starts", "Information", JOptionPane.INFORMATION_MESSAGE);
            timer2.startTimer();
        }

        // Loop through the rows and columns to set up buttons
        for (int i = 0; i < rows; i++) {
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
                            if (!gameOver && !isFlaged[row][col]) {
                                if (counterRevealedcells >= 100) {
                                    timer1.setTimerStep();
                                    timer2.setTimerStep();
                                }
                                // Switch between players and start/stop the timer
                                if (!isRevealed[row][col]) {
                                    if (currentPlayer == 1) {
                                        timer1.stopTimer();
                                        timer1.addTime();
                                        timer2.startTimer();
                                        currentPlayer = 2;
                                    } else {
                                        timer2.stopTimer();
                                        timer2.addTime();
                                        timer1.startTimer();
                                        currentPlayer = 1;
                                    }
                                }
                                // Normal revealing of cell
                                if (!isRevealed[row][col]) {
                                    sound.playSound("src/Audio/big-punch-short-with-male-moan-83735.wav"); //sound for clicking on a field 
                                }
                                revealCell(row, col);
                                counterRevealedcells ++;
                                if (solved(rows, cols) && solvedmines(rows, cols)) {
                                    JOptionPane.showMessageDialog(CompBoard.this, wontext);
                                    timer1.stopTimer();
                                    timer2.stopTimer();
                                    gameOver = true;
                                }
                            }
                            //right click to flag and unflag a field 
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            //check if the game is already over and if the cell is revealed
                            if (!gameOver && !isRevealed[row][col]) {
                                sound.playSound("src/Audio/big-punch-short-with-male-moan-83735.wav"); //sound for clicking on a field 
                                //when you flag all mines and revealed all cell the game is over
                                if (solved(rows, cols) && solvedmines(rows, cols)) {
                                    JOptionPane.showMessageDialog(CompBoard.this, wontext);
                                    timer1.stopTimer();
                                    timer2.stopTimer();
                                    gameOver = true;
                                }
                                // Unflag field
                                if (isFlaged[row][col]) {
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
     * Get the current player's number (1 or 2).
     * 
     * @return the current player's number
     */
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }
        /**
     * Get the name of the player who lost the game.
     * 
     * @return the name of the player who lost
     */
    public String getPlayerWhoLost() {
        return (currentPlayer == 1) ? username1 : username2;
    }

    /**
     * Check if a mine is located at the specified cell.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return true if the cell contains a mine, false otherwise
     */
    public boolean checkmine(int row, int col) {
        return isMine[row][col];
    }

    /**
     * Check if the specified cell is revealed.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return true if the cell is revealed, false otherwise
     */
    public boolean checkrevealed(int row, int col) {
        return isRevealed[row][col];
    }

    /**
     * Set the username for player 1.
     * 
     * @param username the username for player 1
     */
    public void setusername1(String username) {
        usernamLabel1.setText(username);
        this.username1 = username;
    }

    /**
     * Set the username for player 2.
     * 
     * @param username the username for player 2
     */
    public void setusername2(String username) {
        usernamLabel2.setText(username);
        this.username2 = username;
    }

    /**
     * Set the game over status.
     * 
     * @param b the game over status
     */
    public void setGameOver(boolean b) {
        this.gameOver = b;
    }

    /**
     * Check if the game is solved by revealing all non-mine cells.
     * 
     * @param row the number of rows in the game board
     * @param col the number of columns in the game board
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
     * Check if all mines are correctly flagged.
     * 
     * @param row the number of rows in the game board
     * @param col the number of columns in the game board
     * @return true if all mines are flagged correctly, false otherwise
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
     * Place mines randomly on the game board.
     */
    private void placeMines() {
        int minesPlaced = 0;
        while (minesPlaced < mines) {
            int row = (int) (Math.random() * rows);
            int col = (int) (Math.random() * cols);
            if (!isMine[row][col]) {
                isMine[row][col] = true;
                minesPlaced++;
            }
        }
    }

    /**
     * Method to change the design of the game
     * @param design contains the slected design
     */
    public void changedesign (String design) {
        if (design.equals("EM 2024")) {
            mineicon = new ImageIcon("src/images/emmine.png");
            flaggeicon = new ImageIcon("src/images/emflagge.png");
            wontext = "Close call! You made a draw!";
            loosetext = "Red Card! You were sent off field!";
        } else if (design.equals("Frankfurt School")) {
            mineicon = new ImageIcon("src/images/fsmine.png");
            flaggeicon = new ImageIcon("src/images/fsflagge.png");
            wontext = "WLAN crashed! You will need retake the exam!";
            loosetext = "LOWPERFORMER! IB not possible anymore!";
        } else {
            mineicon = new ImageIcon("src/images/mine.png");
            flaggeicon = new ImageIcon("src/images/flagge.png");
            
        }
    }

    /**
     * Reveal the cell at the specified row and column.
     * 
     * @param row the row of the cell to reveal
     * @param col the column of the cell to reveal
     */
    public void revealCell(int row, int col) {
        buttons[row][col].setBackground(Color.LIGHT_GRAY);
        if (isRevealed[row][col]) return; 

        isRevealed[row][col] = true;
        
        if (isMine[row][col]) {
            sound.playSound("src/Audio/medium-explosion-40472.wav");
            buttons[row][col].setIcon(mineicon);
            if (currentPlayer == 1) {
                JOptionPane.showMessageDialog(this, username2 + " lost! " + loosetext);
            } else {
                JOptionPane.showMessageDialog(this, username1 + " lost! " + loosetext);
            }
            gameOver = true;
            timer1.stopTimer();
            timer2.stopTimer();
            revealAllMines();
        } else {
            int adjacentMines = countAdjacentMines(row, col);
            colourSquare(row, col, true);

            if (adjacentMines == 0) {
                buttons[row][col].setIcon(null);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        try {
                            revealCell(row + i, col + j);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    } 
                }
            } else {
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
     * Count the number of mines adjacent to the specified cell.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the number of adjacent mines
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
     * Reveal all mines on the game board.
     * This method iterates over the game board and sets the icon and background color
     * of each button that represents a mine.
     */
    public void revealAllMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isMine[i][j]) {
                    buttons[i][j].setIcon(mineicon);
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    /**
     * Get the game over status.
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean getGameOver() {
        return this.gameOver;
    }

    //winning function

    //give row i and coloumn j plus True in case the field is revield (to adjust the colour)
        /**
     * Color the square at the specified location.
     * Sets the background color of the button at the given row and column
     * @param i        the row of the square
     * @param j        the column of the square
     * @param revealed true if the cell is revealed, false otherwise
     */
    private void colourSquare(int i, int j, boolean revealed) {
        buttons[i][j].setBackground(Color.LIGHT_GRAY);
    }
    /**
     * Set the current player.
     * Updates the current player index.
     *
     * @param i the index of the current player
     */
    public void setCurrentPlayer(int i) {
        this.currentPlayer = i;
    }
}
