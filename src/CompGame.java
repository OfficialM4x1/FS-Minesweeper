package src;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;



public class CompGame extends JFrame {
    ImageIcon menuicon = new ImageIcon("src/images/menu.png");
    ImageIcon hinticon = new ImageIcon("src/images/hint.png");
    ImageIcon tutorialicon = new ImageIcon("src/images/tutorial.png");
    private CompBoard board;
    protected String nameinput1;
    protected String nameinput2;

    /**
     * Compgame creates a compboard and design a game for the two players as a competitive game
     * @param ROWS number of rows on the board
     * @param COLS number of columns on the bord
     * @param MINES number of mines you want to place on the board
     */
    public CompGame(int ROWS, int COLS, int MINES) {
 
        //here you can adjust the frame how you like 
        setTitle("Minesweeper COMPETITIVE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //how to close the frame 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); 
        //change little icon of window 
        ImageIcon image = new ImageIcon("src/images/mine.png"); //hier könnt ihr auch nochmal ein tolles Bild raus suchen für das kleine Icon oben links
        setIconImage(image.getImage());

        //player names
        nameinput1 = JOptionPane.showInputDialog(board, "Player 1, please enter your name:"); //user 1
        nameinput2 = JOptionPane.showInputDialog(board, "Player 2, please enter your name:"); //user 2
        
        //lowest panel where all the other panels sit on
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout()); 
        board = new CompBoard(ROWS, COLS, MINES, nameinput1, nameinput2);
        gamePanel.add(board, BorderLayout.CENTER);
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        // panels around the gameboard 
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE); 
        leftPanel.setPreferredSize(new Dimension(350, 0));
        mainPanel.add(leftPanel, BorderLayout.WEST);


        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(350, 0));
        mainPanel.add(rightPanel, BorderLayout.EAST);
        GridLayout c = new GridLayout(0,1);
            c.setVgap(10);
            rightPanel.setLayout(c);

        JPanel designpanel = new JPanel();
        designpanel.setBackground(Color.WHITE);
        rightPanel.add(designpanel);

        JButton backtomenu = new JButton();
        rightPanel.add(backtomenu);
        backtomenu.setIcon(menuicon);
        backtomenu.setOpaque(false);
        backtomenu.setContentAreaFilled(false);
        backtomenu.setBorderPainted(false);
        
        
        backtomenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        JPanel menutext = new JPanel();
        JLabel labelmenu = new JLabel("Back to menu");
        labelmenu.setFont(new Font("Verdana", Font.PLAIN, 16)); 
        menutext.add(labelmenu);
        menutext.setBackground(Color.WHITE);
        rightPanel.add(menutext);

/*
        JButton giveahint = new JButton();
        rightPanel.add(giveahint);
        giveahint.setIcon(hinticon);
        giveahint.setOpaque(false);
        giveahint.setContentAreaFilled(false);
        giveahint.setBorderPainted(false);

        giveahint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!board.getGameOver()){
                    if (!board.solved(ROWS, COLS)) {
                        int row = (int)(Math.random() * ROWS);
                        int col = (int)(Math.random() * COLS);
                        boolean done = false;
                        while (done == false) {
                            if (board.checkrevealed(row, col)) {
                                row = (int)(Math.random() * ROWS);
                                col = (int)(Math.random() * COLS);                         
                            } else if (board.checkmine(row, col)) {
                                row = (int)(Math.random() * ROWS);
                                col = (int)(Math.random() * COLS);                   
                            } else {
                                done = true;
                                board.revealCell(row, col); 
                            }
                        }
                    }
                }
            }
        });


        JPanel hinttext = new JPanel();
        JLabel labelhint = new JLabel("Get a hint");
        labelhint.setFont(new Font("Verdana", Font.PLAIN, 16)); 
        hinttext.add(labelhint);
        hinttext.setBackground(Color.WHITE);
        rightPanel.add(hinttext);        
*/
        JButton showtutorial = new JButton();
        rightPanel.add(showtutorial);
        showtutorial.setIcon(tutorialicon);
        showtutorial.setOpaque(false);
        showtutorial.setContentAreaFilled(false);
        showtutorial.setBorderPainted(false);
        
        showtutorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String longMessage = "Welcome to the local Minesweeper Competitive Mode!\r\n" + //
                                 "\r\n" + //
                                 "This mode combines the familiar gameplay with the thrill of competing against your friends. The objective of every player is to reveal all fields without setting off any explosives. The renowned gameplay and rules of the classic game remain unchanged. However, in the two player mode they were expanded by the following rules:\r\n" + //
                                "\r\n" + //
                                "You can alternately click on the fields to reveal them. Moreover the timer is counting backwards, so that both players try to solve the game in the given time. If the time is up or you click on a bomb you loose. For every click that reveils a field that is not a mine, the current player is granted three seconds more on the timer. \r\n" + //
                                "\r\n" + //
                                "The combination of speed and accuracy is key to defeat your friends and mastering this version of the notorious classic. So, with these guidelines, dive in and enjoy the challenge of the new Minesweeper Competitive Mode!\r\n" + //
                                 "";
            JTextArea textArea = new JTextArea(20, 50);
            JScrollPane scrollPane = new JScrollPane(textArea);

            textArea.setText(longMessage);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(board, scrollPane, "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel tutorialtext = new JPanel();
        JLabel tutorialhint = new JLabel("View the tutorial");
        tutorialhint.setFont(new Font("Verdana", Font.PLAIN, 16)); 
        tutorialtext.add(tutorialhint);
        tutorialtext.setBackground(Color.WHITE);
        rightPanel.add(tutorialtext);

        add(mainPanel);
        setVisible(true);
    }

    public String getNameinput1() {
        return nameinput1;
    }

    public String getNameinput2() {
        return nameinput2;
    }
}