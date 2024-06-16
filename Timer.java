import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//basic timer with label that counts upwards is created with ChatGPT, the addional functionality is all handmade
public class Timer extends JPanel {
    private JLabel timerLabel;
    private javax.swing.Timer timer;
    private int secondsPassed;
    private boolean comp;
    private boolean timerActive;

    public Timer(String username, CompBoard b) {
        timerLabel = new JLabel("00:00");
        add(timerLabel);
        timerActive = true;

        // Timer für das Aktualisieren des Timer-Labels erstellen
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if timer is active to avoid that the timer is ticking after the game is over
                if (timerActive) {
                    //when it is competitive game the timer counts down and shows a msgBox when the timer is 0
                    if (comp) {
                        secondsPassed--;
                        if (secondsPassed ==0) {
                            stopTimer();
                            JOptionPane.showMessageDialog(null, username+" you lost!");
                            b.setGameOver(true);
                        }
                    }
                    //normal game the time is just counted 
                    else {
                        secondsPassed++;
                    }
                    updateTimerLabel();
                }
            }
        });
    }

    //set timerActive
    public void setTimerActive(boolean b) {
        this.timerActive = b;
    }
    // Starte den Timer
    public void startTimer() {
        timer.start();
    }

    // Stoppe den Timer
    public void stopTimer() {
        timer.stop();
    }

    public int getSeconds() {
        return secondsPassed;
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
