//@author class Silas Abler
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

class CompBoard extends JPanel {
    
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
    ImageIcon mineicon = new ImageIcon("mine.png");
    ImageIcon flaggeicon = new ImageIcon("flagge.png");
    ImageIcon oneicon = new ImageIcon("icon1.png");
    ImageIcon twoicon = new ImageIcon("icon2.png");
    ImageIcon threeicon = new ImageIcon("icon3.png");
    ImageIcon fouricon = new ImageIcon("icon4.png");
    ImageIcon fiveicon = new ImageIcon("icon5.png");
    ImageIcon sixicon = new ImageIcon("icon6.png");
    ImageIcon sevenicon = new ImageIcon("icon7.png");
    ImageIcon eighticon = new ImageIcon("icon8.png");

    private int currentPlayer;
    Random random = new Random();


    //Player 1 stuff
    String username1;
    JLabel usernamLabel1;
    Timer timer1;

    //Player 2 stuff
    String username2;
    JLabel usernamLabel2;
    Timer timer2;

    //Constructor of a board 
    //pass number of mines, rows and columns depending on the difficulty
    public CompBoard(int rows, int cols, int mines, String username1, String username2) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.username1 = username1;
        this.username2 = username2;
        this.gameOver = false;
        
        //InfoPanel for Game with Timer Username etc
        topBoardPanel = new JPanel();
        topBoardPanel.setPreferredSize(new Dimension(600, 30)); 
        topBoardPanel.setBackground(Color.WHITE);

        //player 1
        usernamLabel1 = new JLabel(username1); //Player 1 username
        System.out.println(username1);
        this.timer1 = new Timer(username1);
        topBoardPanel.add(usernamLabel1);
        timer1.setBackground(Color.WHITE);
        timer1.setTimer(20);
        topBoardPanel.add(timer1);

        //player 2
        usernamLabel2 = new JLabel(username2);
        this.timer2 = new Timer(username2);
        topBoardPanel.add(usernamLabel2);
        timer2.setBackground(Color.WHITE);
        timer2.setTimer(20);
        topBoardPanel.add(timer2);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

        buttons = new JButton[rows][cols];
        isMine = new boolean[rows][cols];
        isRevealed = new boolean[rows][cols];
        isFlaged = new boolean[rows][cols];
        placeMines();
        
        currentPlayer = random.nextInt(2) + 1; //decides random which player starts
        if (currentPlayer==1) {
            System.out.println("player one starts"); //hier coole message box
            timer1.startTimer();
        }
        else {
            timer2.startTimer();
        }

        //loops through the rows 
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
                
                buttons[i][j].setBackground(Color.DARK_GRAY);

                buttons[i][j].addMouseListener(new MouseAdapter() { //Mouse listener to differnetiate between left and right click on mouse
                    public void mouseClicked(MouseEvent e) {
                        

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (!gameOver) {
                                //switch between player and start and stop the timer, could add some logic to that like in chess
                                if (!isRevealed[row][col]) {
                                    if (currentPlayer == 1) {
                                        timer1.stopTimer();
                                        timer2.startTimer();
                                        currentPlayer = 2;
                                    }
                                    else {
                                        timer2.stopTimer();
                                        timer1.startTimer();
                                        currentPlayer = 1;
                                    }
                                }
                                //Normal revealing of cell
                                revealCell(row, col);
                                if (solved(rows, cols) && solvedmines(rows, cols)) {
                                    JOptionPane.showMessageDialog(CompBoard.this, "The game is over: draw");
                                    timer1.stopTimer();
                                    timer2.stopTimer();
                                    gameOver = true;
                                }
                            }
                        }
                        //ability to flag mines with aright click and deflag 
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            if (!gameOver) {
                                if (solved(rows, cols) && solvedmines(rows, cols)) {
                                    JOptionPane.showMessageDialog(CompBoard.this, "The game is over: draw");
                                    timer1.stopTimer();
                                    timer2.stopTimer();
                                    gameOver = true;
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
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }
    public String getPlayerWhoLost() {
        String s;
        if (currentPlayer == 1) {
            s = username1;

        }
        else {
            s = username2;
        }
        return s;
    }

    public boolean checkmine(int row, int col) {
        return isMine[row][col];
    }

    public boolean checkrevealed(int row, int col) {
        return isRevealed[row][col];
    }

    public void setusername1(String username) {
        usernamLabel1.setText(username);
        this.username1 = username;
    }

    public void setusername2(String username) {
        usernamLabel2.setText(username);
        this.username2 = username;
    }

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
            
            if (currentPlayer == 1) {
                JOptionPane.showMessageDialog(this, username1 +" you lost!");
            }
            else {
                JOptionPane.showMessageDialog(this, username2 +" you lost!");
            }
            gameOver = true;
            timer1.stopTimer();
            timer2.stopTimer();

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
    public boolean getGameOver() {
        return this.gameOver;
    }

    //winning function

    //give row i and coloumn j plus True incase the field is revield (to adjust the colour)
    private void colourSquare(int i, int j, boolean revealed) {
        buttons[i][j].setBackground(Color.LIGHT_GRAY);       
    }
}