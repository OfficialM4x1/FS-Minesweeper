//Class generated with the help of ChatGPT

package src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioClass {
    private Clip clip;
    private boolean playing;
    private FloatControl volumeControl;

    public void playMusic(String soundFilePath) {
        playing = true;
        try {
            // Datei laden
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Clip erstellen
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(0.5f);

            // Listener hinzufügen, um das Ende des Tracks zu erkennen
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && playing) {
                    clip.setFramePosition(0);  // Zurück zum Anfang
                    clip.start();  // Wiedergabe neu starten
                }
            });

            // Sound abspielen
            clip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playSound(String soundFilePath) {
        playing = false;
        try {
            // Datei laden
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Clip erstellen
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(0.7f);

            // Sound abspielen
            clip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    // method to asjust the volume
    private void setVolume(float volume) {
        if (volumeControl != null) {
            float range = volumeControl.getMaximum() - volumeControl.getMinimum();
            float gain = (range * volume) + volumeControl.getMinimum();
            volumeControl.setValue(gain);
        }
    }
}
