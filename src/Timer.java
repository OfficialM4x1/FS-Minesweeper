package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//basic timer with label that counts upwards is created with ChatGPT, the addional functionality is all hand-made
public class Timer extends JPanel {
    private JLabel timerLabel;
    private javax.swing.Timer timer;
    private int secondsPassed;
    private boolean comp;
    private boolean timerActive;
    private int timerStep; 

    /**
     * Constructor of the timer
     * @param username inputted username by the player
     * @param b Competitive board
     */
    public Timer(String username, CompBoard b) { 
        timerLabel = new JLabel("00:00");
        add(timerLabel);
        timerActive = true;
        this.timerStep = 1;

        // timer to update the timer label-- this created with the help of ChatGPT
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if timer is active to avoid that the timer is ticking after the game is over -- this part is all hand-made
                if (timerActive) {
                    //when it is competitive game the timer counts down and shows a msgBox when the timer is 0
                    if (comp) {
                        secondsPassed = secondsPassed - timerStep;
                        if (secondsPassed ==0) {
                            stopTimer();
                            JOptionPane.showMessageDialog(null, username+" you lost! Your time ran out!");
                            b.revealAllMines();                         
                            b.setGameOver(true);
                        }
                    }
                    //normal game the time is just counted -- this was orignally created with GhatGPT
                    else {
                        secondsPassed++;
                    }
                    updateTimerLabel();
                }
            }
        });
    }
    /**
     * 
     * @param i is the integer how fast the timer counts down
     */
    public void setTimerStep() {
        this.timerStep++;
    }

    /**
     * Method to activate the timer
     * @param b set timer active
     */
    public void setTimerActive(boolean b) {
        this.timerActive = b;
    }
    /**
     * start the timer
     */
    public void startTimer() {
        timer.start();
    }
    
    public int getSeconds() {
        return secondsPassed;
    }

   /**
    * stop the timer
    */
    public void stopTimer() {
        timer.stop();
    }

  /**
   * Method to set the timer -- hand-made
   * @param secondsPassed number of seconds at which the starttime will begin
   */
    public void setTimer(int secondsPassed) { //set the starttime in seconds 
        this.secondsPassed = secondsPassed;
        int minutes = secondsPassed / 60;
        int seconds = secondsPassed % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(timeString);
        this.comp = true;
    }

   /**
    * method for updating the timer label
    */
    private void updateTimerLabel() { // created with ChatGPT
        int minutes = secondsPassed / 60;
        int seconds = secondsPassed % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(timeString);
    }

    public void addTime() {
        this.secondsPassed = this.secondsPassed + 4;
        updateTimerLabel();
    }
}
