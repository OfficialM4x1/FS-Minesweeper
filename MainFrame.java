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

    public MainFrame() {
        setTitle("MINESWEEPER");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //One Panel is a site for the different gamemodes
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(new JLabel("MENU"));
        JPanel easy = new JPanel();
        easy.add(new JLabel("CLASSIC EASY"));
        JPanel medium = new JPanel();
        medium.add(new JLabel("CLASSIC MEDIUM"));
        JPanel hard = new JPanel();
        hard.add(new JLabel("CLASSIC HARD"));

        cardPanel.add(menu, "MENU");
        cardPanel.add(easy, "CLASSIC EASY");
        cardPanel.add(medium, "CLASSIC MEDIUM");
        cardPanel.add(hard, "CLASSIC HARD");

        // Buttons to select the gamemode
        easyButton = new JButton("CLASSIC EASY");
        mediumButton = new JButton("CLASSIC MEDIUM");
        hardButton = new JButton("CLASSIC HARD");
        easyButton.addActionListener(this);
        mediumButton.addActionListener(this);
        hardButton.addActionListener(this);

        cardPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.

        menu.add(easyButton, c);
        menu.add(mediumButton, c);
        menu.add(hardButton, c);

        // Display Buttons in the Main panel
        getContentPane().add(cardPanel, BorderLayout.PAGE_START);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easyButton) {
            cardLayout.next(cardPanel);
        } else if (e.getSource() == mediumButton) {
            cardLayout.next(cardPanel);
            cardLayout.next(cardPanel);
        } else if (e.getSource() == hardButton) {
            cardLayout.next(cardPanel);
            cardLayout.next(cardPanel);
            cardLayout.next(cardPanel);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}