import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class CompGame extends JFrame {
    ImageIcon menuicon = new ImageIcon("images/menu.png");
    ImageIcon hinticon = new ImageIcon("images/hint.png");
    ImageIcon tutorialicon = new ImageIcon("images/tutorial.png");
    private CompBoard board;

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
        ImageIcon image = new ImageIcon("images/mine.png"); //hier könnt ihr auch nochmal ein tolles Bild raus suchen für das kleine Icon oben links
        setIconImage(image.getImage());

        //player names
        String nameinput1 = JOptionPane.showInputDialog(board, "Player 1, please enter your name:"); //user 1
        String nameinput2 = JOptionPane.showInputDialog(board, "Player 2, please enter your name:"); //user 2
        
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
        

        JButton showtutorial = new JButton();
        rightPanel.add(showtutorial);
        showtutorial.setIcon(tutorialicon);
        showtutorial.setOpaque(false);
        showtutorial.setContentAreaFilled(false);
        showtutorial.setBorderPainted(false);
        
        showtutorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String longMessage = "In Minesweeper, your goal is to navigate through a minefield without detonating any explosives. Imagine a grid of squares, some of which contain mines and others that can be safely uncovered. Each click reveals either a mine or a number indicating how many mines lurk in adjacent squares. To mark potential mines, simply right-click to place a flag. But be careful, detonating a mine will end the game. But this is the competitive mode! Your goal is to survive longer than your opponent. Just like in chess, you both have a timer that will make you lose if it runs out. Be careful, the timer will eventually run out, as the time runs faster after certain points. The other way to win is for your opponent to click on a mine.";
            JTextArea textArea = new JTextArea(10, 30);
            JScrollPane scrollPane = new JScrollPane(textArea);

            textArea.setText(longMessage);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(board, scrollPane, "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(mainPanel);
        setVisible(true);

   

    }
}