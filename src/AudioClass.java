//Class generated with the help of ChatGPT

package src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The {@code AudioClass} provides functionality to play and manage audio files.
 * It supports playing music in a loop and playing sounds with adjustable volume control.
 */
public class AudioClass {
    private Clip clip;
    private boolean playing;
    private FloatControl volumeControl;

    /**
     * Plays the specified music file in a loop. The music starts from the beginning once it ends.
     *
     * @param soundFilePath the path to the audio file to be played
     * @throws IllegalArgumentException if the file path is invalid or the file cannot be played
     */
    public void playMusic(String soundFilePath) {
        playing = true;
        try {
            // Load file
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Create a clip
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(0.5f);

            // Add a listener to loop the music
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && playing) {
                    clip.setFramePosition(0);  // Zurück zum Anfang
                    clip.start();  // Wiedergabe neu starten
                }
            });

            // Start playback
            clip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /**
     * Plays the specified sound file once. The sound is not looped.
     *
     * @param soundFilePath the path to the audio file to be played
     * @throws IllegalArgumentException if the file path is invalid or the file cannot be played
     */
    public void playSound(String soundFilePath) {
        playing = false;
        try {
            // Load the audio file
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Create a clip
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(0.7f);

            // Start playback
            clip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing audio and releases resources.
     */
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * Sets the volume for the currently playing audio.
     *
     * @param volume a float value between 0.0 (muted) and 1.0 (maximum volume)
     * @throws IllegalArgumentException if the volume is outside the valid range
     */
    private void setVolume(float volume) {
        if (volumeControl != null) {
            float range = volumeControl.getMaximum() - volumeControl.getMinimum();
            float gain = (range * volume) + volumeControl.getMinimum();
            volumeControl.setValue(gain);
        }
    }
}
