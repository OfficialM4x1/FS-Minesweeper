import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//aus Chat import
public class Timer extends JPanel {
    private JLabel timerLabel;
    private javax.swing.Timer timer;
    private int secondsPassed;
    private boolean comp;

    public Timer() {
        timerLabel = new JLabel("00:00");
        add(timerLabel);

        // Timer für das Aktualisieren des Timer-Labels erstellen
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comp) {
                    secondsPassed--;
                }
                else {
                    secondsPassed++;
                }
                updateTimerLabel();
            }
        });
    }

    // Starte den Timer
    public void startTimer() {
        timer.start();
    }

    // Stoppe den Timer
    public void stopTimer() {
        timer.stop();
    }

    // set time
    public void setTimer(int secondsPassed) { //set the starttime in seconds 
        this.secondsPassed = secondsPassed;
        int minutes = secondsPassed / 60;
        int seconds = secondsPassed % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(timeString);
        this.comp = true;
    }

    // Methode zum Aktualisieren des Timer-Labels
    private void updateTimerLabel() {
        int minutes = secondsPassed / 60;
        int seconds = secondsPassed % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(timeString);
    }
}
