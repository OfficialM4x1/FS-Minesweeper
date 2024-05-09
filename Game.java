import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame {
    private final int ROWS = 18;
    private final int COLS = 18;
    private final int MINES = 40;

    private Board board;
    
    //Stuff for the timer
    private Timer timer;
    private JLabel timerLabel;
    private int secondsPassed;

    public Game() {
        //here you can adjust the frame how you like 
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //how to close the frame 
        setSize(1000, 800); //size of the frame 
        setLocationRelativeTo(null); 
        //change little icon of window 
        ImageIcon image = new ImageIcon("PanzerMine.png"); //hier könnt ihr auch nochmal ein tolles Bild raus suchen für das kleine Icon oben links
        setIconImage(image.getImage());

    
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

       
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout()); 
        board = new Board(ROWS, COLS, MINES);
        gamePanel.add(board, BorderLayout.CENTER);
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        // panels around the gameboard 
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLUE); //Farbe gerne anpasses !!!
        leftPanel.setPreferredSize(new Dimension(200, 0));
        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.GREEN); //Farbe gerne anpasses !!!
        rightPanel.setPreferredSize(new Dimension(200, 0));
        mainPanel.add(rightPanel, BorderLayout.EAST);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.RED); //Farbe gerne anpasses !!!
        topPanel.setPreferredSize(new Dimension(0, 100)); 
        Timer timer = new Timer();
        topPanel.add(timer);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.RED); //Farbe gerne anpasses !!!
        bottomPanel.setPreferredSize(new Dimension(0, 100)); 
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        //all stuff for the timer
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                timer.startTimer();
            }
        });
        add(mainPanel);
        setVisible(true);
        
    }

}