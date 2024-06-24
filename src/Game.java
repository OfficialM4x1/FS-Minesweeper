package src;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//The game class represents the JFrame which opens after you selected your gamemode
public class Game extends JFrame {
    ImageIcon menuicon = new ImageIcon("src/images/menu.png");
    ImageIcon hinticon = new ImageIcon("src/images/hint.png");
    ImageIcon tutorialicon = new ImageIcon("src/images/tutorial.png");
    protected Board board;

    /**
     * The class game, which gets called from the main frame creates the game. The following is the constructor of the game
     * @param ROWS amount of rows
     * @param COLS amount of columns
     * @param MINES amount of mines
     * @param design the design you selected
     */
    public Game(int ROWS, int COLS, int MINES, String design) {

        //here you can adjust the frame how you like 
        String nameinput = JOptionPane.showInputDialog(board, "Enter your name please:");
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //how to close the frame 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        //change little icon of window 
        ImageIcon image = new ImageIcon("src/images/mine.png"); //Little Icon on the top left bar
        setIconImage(image.getImage());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout()); 
        board = new Board(ROWS, COLS, MINES, design);
        board.setusername(nameinput);
        gamePanel.add(board, BorderLayout.CENTER);
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        // panels around the gameboard 
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE); 
        leftPanel.setPreferredSize(new Dimension(350, 0));
        mainPanel.add(leftPanel, BorderLayout.WEST);

        //The right panel
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

        //Back to menu button
        JButton backtomenu = new JButton();
        rightPanel.add(backtomenu);
        backtomenu.setIcon(menuicon);
        backtomenu.setOpaque(false);
        backtomenu.setContentAreaFilled(false);
        backtomenu.setBorderPainted(false);
        
        //ActionListener for the back to menu button
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

        //Give a hint button
        JButton giveahint = new JButton();
        rightPanel.add(giveahint);
        giveahint.setIcon(hinticon);
        giveahint.setOpaque(false);
        giveahint.setContentAreaFilled(false);
        giveahint.setBorderPainted(false);

        //ActionListener for the hint button that reveals on cell 
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

        //Show the tutorial button
        JButton showtutorial = new JButton();
        rightPanel.add(showtutorial);
        showtutorial.setIcon(tutorialicon);
        showtutorial.setOpaque(false);
        showtutorial.setContentAreaFilled(false);
        showtutorial.setBorderPainted(false);
        //ActionListener for the show the tutorial button
        showtutorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            board.stoptimerexternal();
            String longMessage = "Welcome to the Minesweeper Classic!\r\n" + //
                                 "\r\n" + //
                                 "The notorious retro game where your objective is to reveal all fields without setting off any explosives. First introduced in 1989, this classic has hardly lost its charm and continues to enjoy great popularity. The renowned gameplay and rules of the classic game remain unchanged to this day. However, just in case you are not familiar with them, here is a quick tutorial:\r\n" + //
                                "\r\n" + //
                                "Picture a grid of squares, some concealing mines and others safe to uncover. Your objective is to reveal all fields of the minefield that are not bombs. You can make a guess by clicking on a field, uncovering either a mine or a number. The latter indicating how many mines lurk in adjacent squares. To mark potential mines, simply right-click to place a flag. This will help you remembering the mines you think you have uncovered. But be cautious! If you hit a mine, the game is over. Success hinges on strategic uncovering - start with less risky areas and progress logically. By analyzing the board and making calculated guesses, you'll gradually unveil the safe squares.\r\n" + //
                                "\r\n" + //
                                "Practice is key to honing your skills and mastering the art of deduction in this classic game. So, with these guidelines, dive in and enjoy the challenge of Minesweeper!\r\n" + //
                                 "";
            JTextArea textArea = new JTextArea(20, 50);
            JScrollPane scrollPane = new JScrollPane(textArea);

            textArea.setText(longMessage);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(board, scrollPane, "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            board.starttimerexternal();
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
}