import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompGame extends JFrame {
    private int ROWS = 18;
    private int COLS = 18;
    private int MINES = 35;
    ImageIcon menuicon = new ImageIcon("menu.png");
    ImageIcon hinticon = new ImageIcon("hint.png");
    ImageIcon tutorialicon = new ImageIcon("tutorial.png");

    private CompBoard board;

    public CompGame(int ROWS, int COLS, int MINES) {
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.MINES = MINES;
        //here you can adjust the frame how you like 
        setTitle("Minesweeper COMPETITIVE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //how to close the frame 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); 
        //change little icon of window 
        ImageIcon image = new ImageIcon("mine.png"); //hier könnt ihr auch nochmal ein tolles Bild raus suchen für das kleine Icon oben links
        setIconImage(image.getImage());

        //player names
        String nameinput1 = JOptionPane.showInputDialog(board, "Player 1, please enter your name:"); //user 1
        String nameinput2 = JOptionPane.showInputDialog(board, "Player 2, please enter your name:"); //user 2
        
        //lowest panel where all the other panels sit on
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout()); 
        board = new CompBoard(ROWS, COLS, MINES);
        board.setusername1(nameinput1); //add usernames
        board.setusername2(nameinput2);
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
            String longMessage = "In Minesweeper, your objective is to navigate through a minefield without setting off any explosives. Picture a grid of squares, some concealing mines and others safe to uncover. Each click reveals either a mine or a number, indicating how many mines lurk in adjacent squares. To mark potential mines, simply right-click to place a flag. But be cautious, detonating a mine ends the game. Success hinges on strategic uncovering—start with less risky areas and progress logically. By analyzing the board and making calculated guesses, you'll gradually unveil the safe squares. Ultimately, winning requires uncovering all safe squares while avoiding any explosions. Practice is key to honing your skills and mastering the art of deduction in this classic game. So, with these guidelines, dive in and enjoy the challenge of Minesweeper!";
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