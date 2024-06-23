package src;
/**
 * Various imports e.g. ActionListener for our buttons and SQL to support the interaction with our Database in SQLite
 */
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * The MainFrame represents our JFrame for the menu
 */
public class MainFrame extends JFrame implements ActionListener {
    //add an AudioClass music for background music
    AudioClass music = new AudioClass();

    //We initially used a CardLayout to build different styled menus. In the cardLayout you are able to switch between different Layouts. Unforunately time wasn't in our favour, but we left it to further enhance our program (enabler)
    private CardLayout cardLayout;
    private JPanel cardPanel;

    //Here we define some of the main objects for our menu (e.g. the buttons to select the gamemode)
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton competitiveButton;

    //Here we define the icons for the main menu, but only the icons that don't change when switching the design
    private ImageIcon advertiseicon = new ImageIcon("src/images/advertiseicon.png");
    private ImageIcon firsticon = new ImageIcon("src/images/first.png");
    private ImageIcon secondicon = new ImageIcon("src/images/second.png");
    private ImageIcon thirdicon = new ImageIcon("src/images/third.png");

    String[] options = {"Standard", "EM 2024", "Frankfurt School"}; //This is the String that contains keys to select the different designs, which is later used in the JComboBox
    JComboBox<String> comboBox = new JComboBox<>(options); //JComboBox to select the design for the game in the main menu

    public MainFrame() {
        //Here we edit the style of the JFrame
        setTitle("MINESWEEPER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        //CardLayout for potential different menus
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //Further definitions that are later used in the Code
        GridLayout gridLayout = new GridLayout(1, 3);
        gridLayout.setHgap(30);
        Font standardfont = new Font("Arial", Font.PLAIN, 20);
        Font bigfont = new Font("Arial", Font.PLAIN, 40);
        Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE, 8, true);

        //Creation of different JPanels for the main menu
        JPanel menuTitlePanel = new JPanel();
        JPanel menu = new JPanel(gridLayout);
        JPanel leftmenu = new JPanel();
        JPanel middlemenu = new JPanel();
        JPanel rightmenu = new JPanel();
        JPanel middlemenuoutside = new JPanel();
        JButton advertisementbutton = new JButton();
        JLabel text = new JLabel("MENU");
        JPanel leaderboardpanel = new JPanel();
        JPanel leaderboardpaneloutside = new JPanel();
        JLabel text2 = new JLabel("LEADERBOARD");

        //Editing the Style of the different JPanels for the main menu to match our needs

        //Adding layouts to the JPanels and JButtons
        middlemenu.setLayout(new BorderLayout());
        rightmenu.setLayout(new GridLayout(4,1));
        leftmenu.setLayout(new GridLayout(1,1));
        middlemenuoutside.setLayout(new GridLayout(10,1));
        rightmenu.setLayout(new BorderLayout());
        leaderboardpaneloutside.setLayout(new GridLayout(7,1));

        //Adding borders to the JPanels and JButtons
        menu.setBorder(new EmptyBorder(100, 40, 100, 40));
        advertisementbutton.setBorderPainted(false);

        //Changing the background of the objects to match our design
        menu.setBackground(Color.DARK_GRAY);
        leftmenu.setBackground(Color.DARK_GRAY);
        middlemenu.setBackground(Color.DARK_GRAY);
        rightmenu.setBackground(Color.DARK_GRAY);
        middlemenuoutside.setBackground(Color.DARK_GRAY);
        menuTitlePanel.setBackground(Color.DARK_GRAY);
        leaderboardpaneloutside.setBackground(Color.DARK_GRAY);
        leaderboardpanel.setBackground(Color.DARK_GRAY);

        //Adding different objects to other objects
        menu.add(leftmenu);
        menu.add(middlemenu);
        menu.add(rightmenu);
        leftmenu.add(advertisementbutton);
        menuTitlePanel.add(text);
        middlemenu.add(middlemenuoutside);
        middlemenuoutside.add(menuTitlePanel);
        rightmenu.add(leaderboardpaneloutside);
        leaderboardpanel.add(text2);
        leaderboardpaneloutside.add(leaderboardpanel);

        //Adding icons to different buttons
        advertisementbutton.setIcon(advertiseicon);

        //Little Icon on the top left bar
        ImageIcon image = new ImageIcon("src/images/mine.png"); 
        setIconImage(image.getImage());

        //Changing the style of objects in general (e.g. opaque, focusable)
        advertisementbutton.setOpaque(false);
        advertisementbutton.setContentAreaFilled(false);
        advertisementbutton.setFocusPainted(false);
        menuTitlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setForeground(Color.WHITE);
        text.setFont(bigfont);
        leaderboardpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        text2.setFont(bigfont);
        text2.setForeground(Color.WHITE);
        
        //easyButton gets edited
        easyButton = new JButton("CLASSIC EASY");
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyButton.addActionListener(this);
        easyButton.setOpaque(false);
        easyButton.setContentAreaFilled(false);
        easyButton.setBorderPainted(true);
        easyButton.setBorder(whiteBorder);
        easyButton.setFont(standardfont);
        easyButton.setForeground(Color.WHITE);
        middlemenuoutside.add(easyButton);
        middlemenuoutside.add(Box.createVerticalStrut(5));

        //mediumButton gets edited
        mediumButton = new JButton("CLASSIC MEDIUM");
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton.addActionListener(this);
        mediumButton.setOpaque(false);
        mediumButton.setContentAreaFilled(false);
        mediumButton.setBorderPainted(true);
        mediumButton.setBorder(whiteBorder);
        mediumButton.setFont(standardfont);
        mediumButton.setForeground(Color.WHITE);
        middlemenuoutside.add(mediumButton);
        middlemenuoutside.add(Box.createVerticalStrut(5));

        //hardButton gets edited
        hardButton = new JButton("CLASSIC HARD");
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.addActionListener(this);
        hardButton.setOpaque(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setBorderPainted(true);
        hardButton.setBorder(whiteBorder);
        hardButton.setFont(standardfont);
        hardButton.setForeground(Color.WHITE);
        middlemenuoutside.add(hardButton);
        middlemenuoutside.add(Box.createVerticalStrut(5));
        
        //competitiveButton gets edited
        competitiveButton = new JButton("COMPETITIVE MODE");
        competitiveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        competitiveButton.addActionListener(this);
        competitiveButton.setOpaque(false);
        competitiveButton.setContentAreaFilled(false);
        competitiveButton.setBorderPainted(true);
        competitiveButton.setBorder(whiteBorder);
        competitiveButton.setFont(standardfont);
        competitiveButton.setForeground(Color.WHITE);
        middlemenuoutside.add(competitiveButton);
        middlemenuoutside.add(Box.createVerticalStrut(5));

        //comboBox gets edited
        comboBox.setBackground(Color.DARK_GRAY);
        comboBox.setBorder(whiteBorder);
        comboBox.setFont(standardfont);
        comboBox.setForeground(Color.WHITE);
        middlemenuoutside.add(comboBox);

        //play background music
        //source for the music https://pixabay.com/de/music/search/free%20songs/
        //music.playMusic("src/Audio/chill-mood-178691.wav");

        //Here starts the part for the database
        //This part repeats three times, which is due to the fact that SQLite only supports forward only result sets. By choosing another technology stack we would have implemented it "smarter" :)
        //Initialization of different variables for the connection and statements
        Connection c = null;
        Statement stmt = null;
        Connection c2 = null;
        Statement stmt2 = null;
        Connection c3 = null;
        Statement stmt3 = null;


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/leaderboard.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            Class.forName("org.sqlite.JDBC");
            c2 = DriverManager.getConnection("jdbc:sqlite:src/leaderboard.db");
            c2.setAutoCommit(false);
            stmt2 = c2.createStatement();
            Class.forName("org.sqlite.JDBC");
            c3 = DriverManager.getConnection("jdbc:sqlite:src/leaderboard.db");
            c3.setAutoCommit(false);
            stmt3 = c3.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PLAYERS;");
            ResultSet rs2 = stmt2.executeQuery( "SELECT * FROM PLAYERS;");
            ResultSet rs3 = stmt3.executeQuery( "SELECT * FROM PLAYERS;");

            //The IDs and times get assigned random extremely high numbers, just to get the top three
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

            //JButton that displays the icon for the first place
            JButton first = new JButton();
            first.setIcon(firsticon);
            leaderboardpaneloutside.add(first);
            first.setOpaque(false);
            first.setContentAreaFilled(false);
            first.setBorderPainted(false);

            //JPanel and JLabel that displays the first place
            JPanel leaderboardpanel1 = new JPanel();
            leaderboardpanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel text3 = new JLabel("First Place: "+ NAME1 + " with "+ TIME1 + " seconds");
            leaderboardpanel1.add(text3);
            leaderboardpaneloutside.add(leaderboardpanel1);
            leaderboardpanel1.setBackground(Color.DARK_GRAY);
            text3.setFont(standardfont);
            text3.setForeground(Color.WHITE);

            //JButton that displays the icon for the second place
            JButton second = new JButton();
            second.setIcon(secondicon);
            leaderboardpaneloutside.add(second);
            second.setOpaque(false);
            second.setContentAreaFilled(false);
            second.setBorderPainted(false);
            
        //JPanel and JLabel that displays the second place
            JPanel leaderboardpanel2 = new JPanel();
            leaderboardpanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel text4 = new JLabel("Second Place: "+ NAME2 + " with "+ TIME2 + " seconds");
            leaderboardpanel2.add(text4);
            leaderboardpaneloutside.add(leaderboardpanel2);
            leaderboardpanel2.setBackground(Color.DARK_GRAY);
            text4.setFont(standardfont);
            text4.setForeground(Color.WHITE);

            //JButton that displays the icon for the third place
            JButton third = new JButton();
            third.setIcon(thirdicon);
            leaderboardpaneloutside.add(third);
            third.setOpaque(false);
            third.setContentAreaFilled(false);
            third.setBorderPainted(false);

            //JPanel and JLabel that displays the third place
            JPanel leaderboardpanel3 = new JPanel();
            leaderboardpanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel text5 = new JLabel("Third Place: "+ NAME3 + " with "+ TIME3 + " seconds");
            leaderboardpanel3.add(text5);
            leaderboardpaneloutside.add(leaderboardpanel3);
            leaderboardpanel3.setBackground(Color.DARK_GRAY);
            text5.setFont(standardfont);
            text5.setForeground(Color.WHITE);
            
            
            //Closing and comitting all database requests as well as result sets
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
                //Throwing an exception in case of an invalid usage
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        
        //As mentioned earlier, here "main" JPanel which is called manu gets added to the cardPanel. Potentially you could implement code here to switch between different menus
        cardPanel.add(menu, "MENU");

        // Display buttons in the main panel
        getContentPane().add(cardPanel);
    }

    @Override
    /**
     * Actionlistener for the buttons for the different gamemodes aswell as getting the selected value of the JComboBox
     */
    public void actionPerformed(ActionEvent e) {
        String selectedValue = (String) comboBox.getSelectedItem();
        if (e.getSource() == easyButton) {
            Game mygame = new Game(18, 18, 35, selectedValue);
        } else if (e.getSource() == mediumButton) {
            Game mygame2 = new Game(25, 25, 90, selectedValue);
        } else if (e.getSource() == hardButton) {
            Game mygame3 = new Game(32, 32, 150, selectedValue);
        } else if (e.getSource() == competitiveButton){
            CompGame compgame = new CompGame(18, 18, 35, selectedValue);
        }
    }

    /**
     * Main Method for the class MainFrame
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            
        });
    }   
}