import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private final int ROWS = 18;
    private final int COLS = 18;
    private final int MINES = 40;

    private Board board;

    public Game() {
        //here you can adjust the frame how you like 
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //how to close the frame 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        leftPanel.setPreferredSize(new Dimension(140, 0));
        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.GREEN); //Farbe gerne anpasses !!!
        rightPanel.setPreferredSize(new Dimension(140, 0));
        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);

   

    }

}