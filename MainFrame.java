import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton competitiveButton;
    private JPanel leaderboard1;

    public MainFrame() {
        setTitle("MINESWEEPER");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //One Panel is a site for the different gamemodes
        JPanel menuTitlePanel = new JPanel();
        JPanel menu = new JPanel();
        menuTitlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.add(Box.createVerticalStrut(100));
        menuTitlePanel.add(new JLabel("MENU"));
        menu.add(menuTitlePanel);
        menu.add(Box.createVerticalStrut(50));
        
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(Box.createVerticalStrut(50));
        easyButton = new JButton("CLASSIC EASY");
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyButton.addActionListener(this);
        menu.add(easyButton);

        menu.add(Box.createVerticalStrut(20));
        mediumButton = new JButton("CLASSIC MEDIUM");
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton.addActionListener(this);
        menu.add(mediumButton);

        menu.add(Box.createVerticalStrut(20));
        hardButton = new JButton("CLASSIC HARD");
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.addActionListener(this);
        menu.add(hardButton);

        menu.add(Box.createVerticalStrut(20));
        competitiveButton = new JButton("COMPETITIVE MODE");
        competitiveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        competitiveButton.addActionListener(this);
        menu.add(competitiveButton);

        JPanel leaderboardpanel = new JPanel();
        leaderboardpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.add(Box.createVerticalStrut(100));
        leaderboardpanel.add(new JLabel("LEADERBOARD (ALL MODES)"));
        menu.add(leaderboardpanel);
        menu.add(Box.createVerticalStrut(10));


        /**
         * This part repeats three times, which is due to the fact that SQLite only supports forward only result sets. By choosing another technology stack we would have implemented it "smarter" :)
         */
        Connection c = null;
        Statement stmt = null;
        Connection c2 = null;
        Statement stmt2 = null;
        Connection c3 = null;
        Statement stmt3 = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leaderboard.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            Class.forName("org.sqlite.JDBC");
            c2 = DriverManager.getConnection("jdbc:sqlite:leaderboard.db");
            c2.setAutoCommit(false);
            stmt2 = c2.createStatement();
            Class.forName("org.sqlite.JDBC");
            c3 = DriverManager.getConnection("jdbc:sqlite:leaderboard.db");
            c3.setAutoCommit(false);
            stmt3 = c3.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PLAYERS;");
            ResultSet rs2 = stmt2.executeQuery( "SELECT * FROM PLAYERS;");
            ResultSet rs3 = stmt3.executeQuery( "SELECT * FROM PLAYERS;");
            int ID1 = 40000;
            String NAME1 = "";
            int TIME1 = 50000;
            int ID2 = 40001;
            String NAME2 = "";
            int TIME2 = 50000;
            int ID3 = 40002;
            String NAME3 = "";
            int TIME3 = 50000;
            while ( rs.next()) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int time  = rs.getInt("time");
                if (time < TIME1) {
                     ID1 = id;
                     NAME1 = name;
                     TIME1 = time;
                }
            }
            while ( rs2.next()) {
                int id = rs2.getInt("id");
                String  name = rs2.getString("name");
                int time  = rs2.getInt("time");
                if (id != ID1 && time < TIME2) {
                     ID2 = id;
                     NAME2 = name;
                     TIME2 = time;
                }
            }
            while ( rs3.next()) {
                int id = rs3.getInt("id");
                String  name = rs3.getString("name");
                int time  = rs3.getInt("time");
                if (id != ID1 && id != ID2 && time < TIME3) {
                     ID3 = id;
                     NAME3 = name;
                     TIME3 = time;
                }
            }

            JPanel leaderboardpanel1 = new JPanel();
            leaderboardpanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
            leaderboardpanel1.add(new JLabel("First Place: "+ NAME1 + " with "+ TIME1 + " seconds"));
            menu.add(leaderboardpanel1);

            JPanel leaderboardpanel2 = new JPanel();
            leaderboardpanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
            leaderboardpanel2.add(new JLabel("Second Place: "+ NAME2 + " with "+ TIME2 + " seconds"));
            menu.add(leaderboardpanel2);

            JPanel leaderboardpanel3 = new JPanel();
            leaderboardpanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
            leaderboardpanel3.add(new JLabel("Third Place: "+ NAME3 + " with "+ TIME3 + " seconds"));
            menu.add(leaderboardpanel3);
                
            rs.close();
            rs2.close();
            rs3.close();
            stmt.close();
            stmt2.close();
            stmt3.close();
            c.close();
            c2.close();
            c3.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }

        cardPanel.add(menu, "MENU");

        // Display Buttons in the Main panel
        getContentPane().add(cardPanel, BorderLayout.PAGE_START);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easyButton) {
            Game mygame = new Game(18, 18, 35);
        } else if (e.getSource() == mediumButton) {
            Game mygame2 = new Game(25, 25, 90);
        } else if (e.getSource() == hardButton) {
            Game mygame3 = new Game(32, 32, 150);
        } else if (e.getSource() == competitiveButton){
            CompGame compgame = new CompGame(18, 18, 35);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            
        });
    }

    
}