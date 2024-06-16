import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;

    /**
     * 
     */
    public MainFrame() {
        setTitle("MINESWEEPER");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            
        });
    }
}