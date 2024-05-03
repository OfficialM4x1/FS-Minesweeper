import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyJFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My First JFrame");
        JLabel label = new JLabel("Geeks Premier League 2023");
        frame.add(label);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}