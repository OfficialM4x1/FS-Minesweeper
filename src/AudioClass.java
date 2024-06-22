//Imported from ChatGPT

package src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioClass {
    private Clip clip;

    public void playSound(String soundFilePath) {
        try {
            // Datei laden
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Clip erstellen
            clip = AudioSystem.getClip();
            clip.open(audioStream);

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
}
