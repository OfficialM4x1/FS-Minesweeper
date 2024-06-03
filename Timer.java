import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//basic structure from chat gpt, adjustments for competitive mode handmade
public class Timer extends JPanel {
    private JLabel timerLabel;
    private javax.swing.Timer timer;
    private int secondsPassed;

    public Timer(int mode) {
        if (mode==0) {
            timerLabel = new JLabel("00:00");
        }
        else if(mode==1) {
            timerLabel = new JLabel("01:00");
            secondsPassed = 60;
        }   
        add(timerLabel);

        // Timer für das Aktualisieren des Timer-Labels erstellen
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mode==0) {
                    secondsPassed++; //normal mode timer just stops the time how long the user needed to solve the field 
                }
                else if (mode ==1) { //when competitive mode the timer counts dow because the player only has a limited amount of time 
                    secondsPassed--;
                }
                else {
                    System.out.println("Not a valid gamemode");
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

    // Methode zum Aktualisieren des Timer-Labels
    private void updateTimerLabel() {
        int minutes = secondsPassed / 60;
        int seconds = secondsPassed % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(timeString);
    }
}
