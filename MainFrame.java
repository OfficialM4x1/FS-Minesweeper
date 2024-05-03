import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton nextPageButton;
    private JButton previousPageButton;

    public MainFrame() {
        setTitle("MINESWEEPER");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //One Panel is a site for the different gamemodes
        JPanel menu = new JPanel();
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
        nextPageButton = new JButton("Nächste Seite");
        nextPageButton.addActionListener(this);
        previousPageButton = new JButton("Vorherige Seite");
        previousPageButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(previousPageButton);
        buttonPanel.add(nextPageButton);

        // Füge Panels zum Hauptframe hinzu
        getContentPane().add(cardPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextPageButton) {
            cardLayout.next(cardPanel);
        } else if (e.getSource() == previousPageButton) {
            cardLayout.previous(cardPanel);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}