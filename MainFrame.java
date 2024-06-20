import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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
    private ImageIcon advertiseicon = new ImageIcon("images/advertiseicon.png");
    private ImageIcon firsticon = new ImageIcon("images/first.png");
    private ImageIcon secondicon = new ImageIcon("images/second.png");
    private ImageIcon thirdicon = new ImageIcon("images/third.png");

    public MainFrame() {
        setTitle("MINESWEEPER");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        GridLayout gridLayout = new GridLayout(1, 3);
        gridLayout.setHgap(30);
        JPanel menuTitlePanel = new JPanel();
        JPanel menu = new JPanel(gridLayout);
        JPanel leftmenu = new JPanel();
        JPanel middlemenu = new JPanel();
        JPanel rightmenu = new JPanel();
        middlemenu.setLayout(new GridLayout(10,1));
        rightmenu.setLayout(new GridLayout(4,1));
        leftmenu.setLayout(new GridLayout(1,1));
        menu.setBorder(new EmptyBorder(100, 40, 100, 40));
        menu.add(leftmenu);
        menu.add(middlemenu);
        menu.add(rightmenu);
        menu.setBackground(Color.DARK_GRAY);
        leftmenu.setBackground(Color.DARK_GRAY);
        middlemenu.setBackground(Color.DARK_GRAY);
        rightmenu.setBackground(Color.DARK_GRAY);
        JButton advertisementbutton = new JButton();
        advertisementbutton.setIcon(advertiseicon);
        advertisementbutton.setOpaque(false);
        advertisementbutton.setContentAreaFilled(false);
        advertisementbutton.setBorderPainted(false);
        advertisementbutton.setFocusPainted(false);
        leftmenu.add(advertisementbutton);
        menuTitlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel text = new JLabel("MENU");
        menuTitlePanel.add(text);
        middlemenu.add(menuTitlePanel);
        menuTitlePanel.setBackground(Color.DARK_GRAY);
        Font standardfont = new Font("Arial", Font.PLAIN, 20);
        Font bigfont = new Font("Arial", Font.PLAIN, 40);
        text.setForeground(Color.WHITE);
        text.setFont(bigfont);

        Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE, 8, true);


        easyButton = new JButton("CLASSIC EASY");
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyButton.addActionListener(this);
        easyButton.setOpaque(false);
        easyButton.setContentAreaFilled(false);
        easyButton.setBorderPainted(true);
        easyButton.setBorder(whiteBorder);
        easyButton.setFont(standardfont);
        easyButton.setForeground(Color.WHITE);
        middlemenu.add(easyButton);
        middlemenu.add(Box.createVerticalStrut(5));

        mediumButton = new JButton("CLASSIC MEDIUM");
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton.addActionListener(this);
        mediumButton.setOpaque(false);
        mediumButton.setContentAreaFilled(false);
        mediumButton.setBorderPainted(true);
        mediumButton.setBorder(whiteBorder);
        mediumButton.setFont(standardfont);
        mediumButton.setForeground(Color.WHITE);
        middlemenu.add(mediumButton);
        middlemenu.add(Box.createVerticalStrut(5));

        hardButton = new JButton("CLASSIC HARD");
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.addActionListener(this);
        hardButton.setOpaque(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setBorderPainted(true);
        hardButton.setBorder(whiteBorder);
        hardButton.setFont(standardfont);
        hardButton.setForeground(Color.WHITE);
        middlemenu.add(hardButton);
        middlemenu.add(Box.createVerticalStrut(5));
        
        competitiveButton = new JButton("COMPETITIVE MODE");
        competitiveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        competitiveButton.addActionListener(this);
        competitiveButton.setOpaque(false);
        competitiveButton.setContentAreaFilled(false);
        competitiveButton.setBorderPainted(true);
        competitiveButton.setBorder(whiteBorder);
        competitiveButton.setFont(standardfont);
        competitiveButton.setForeground(Color.WHITE);
        middlemenu.add(competitiveButton);

        JPanel leaderboardpanel = new JPanel();
        rightmenu.setLayout(new BorderLayout());
        JPanel leaderboardpaneloutside = new JPanel();
        leaderboardpaneloutside.setLayout(new GridLayout(7,1));
        rightmenu.add(leaderboardpaneloutside);
        leaderboardpanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel text2 = new JLabel("LEADERBOARD");
        leaderboardpanel.add(text2);
        leaderboardpaneloutside.add(leaderboardpanel);
        leaderboardpaneloutside.setBackground(Color.DARK_GRAY);
        leaderboardpanel.setBackground(Color.DARK_GRAY);
        text2.setFont(bigfont);
        text2.setForeground(Color.WHITE);

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


            JButton first = new JButton();
            first.setIcon(firsticon);
            leaderboardpaneloutside.add(first);
            first.setOpaque(false);
            first.setContentAreaFilled(false);
            first.setBorderPainted(false);
            JPanel leaderboardpanel1 = new JPanel();
            leaderboardpanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel text3 = new JLabel("First Place: "+ NAME1 + " with "+ TIME1 + " seconds");
            leaderboardpanel1.add(text3);
            leaderboardpaneloutside.add(leaderboardpanel1);
            leaderboardpanel1.setBackground(Color.DARK_GRAY);
            text3.setFont(standardfont);
            text3.setForeground(Color.WHITE);

            JButton second = new JButton();
            second.setIcon(secondicon);
            leaderboardpaneloutside.add(second);
            second.setOpaque(false);
            second.setContentAreaFilled(false);
            second.setBorderPainted(false);
            

            JPanel leaderboardpanel2 = new JPanel();
            leaderboardpanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel text4 = new JLabel("Second Place: "+ NAME2 + " with "+ TIME2 + " seconds");
            leaderboardpanel2.add(text4);
            leaderboardpaneloutside.add(leaderboardpanel2);
            leaderboardpanel2.setBackground(Color.DARK_GRAY);
            text4.setFont(standardfont);
            text4.setForeground(Color.WHITE);

            JButton third = new JButton();
            third.setIcon(thirdicon);
            leaderboardpaneloutside.add(third);
            third.setOpaque(false);
            third.setContentAreaFilled(false);
            third.setBorderPainted(false);

            JPanel leaderboardpanel3 = new JPanel();
            leaderboardpanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel text5 = new JLabel("Third Place: "+ NAME3 + " with "+ TIME3 + " seconds");
            leaderboardpanel3.add(text5);
            leaderboardpaneloutside.add(leaderboardpanel3);
            leaderboardpanel3.setBackground(Color.DARK_GRAY);
            text5.setFont(standardfont);
            text5.setForeground(Color.WHITE);
                
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