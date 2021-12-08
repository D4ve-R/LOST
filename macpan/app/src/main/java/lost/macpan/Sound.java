package lost.macpan;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

/**
 * Class for handling audio
 * @author Leon Wigro
 */
public class Sound {
    Clip clip;                      //class for buffered audio handling
    URL soundURL[] = new URL[10];   //utilized audio files, only 16-bit .wav files supported
    int current;                    //currently playing / suspended track
    long pausedAt;                  //progress into suspended track

    public Sound() {    //constructor loads audio files !CURRENTLY FILLED WITH TEST TRACKS ONLY, TO BE REPLACED!
        soundURL[0] = getClass().getResource("/music/MenuMusic.wav");
        soundURL[1] = getClass().getResource("/music/Emil's Shop.wav");
        soundURL[2] = getClass().getResource("/music/Birth of a Wish.wav");
        soundURL[3] = getClass().getResource("/music/Windows XP Shutdown.wav");
        soundURL[4] = getClass().getResource("/sound effects/coin.wav");
    }

    /**
     * sets clip to the selected track !NOT TO BE DIRECTLY USED!
     * @param track track index
     * @param interruptingOther whether current should be overwritten
     */
    public void setFile(int track, boolean interruptingOther) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[track]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        if (!interruptingOther)
            current = track;
    }

    /**
     * starts playback !NOT TO BE DIRECTLY USED!
     */
    public void play() {
        clip.start();
    }

    /**
     * loops current track !NOT TO BE DIRECTLY USED!
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * stops playback !NOT TO BE DIRECTLY USED!
     */
    public void stop() {
        clip.stop();
    }

    /**
     * pauses playback !NOT TO BE DIRECTLY USED!
     */
    public void pause() {
        pausedAt = clip.getMicrosecondPosition();
        clip.stop();
    }

    /**
     *Resumes paused playback !NOT TO BE DIRECTLY USED!
     */
    public void resume() {
        setFile(current, true);
        clip.start();
        clip.setMicrosecondPosition(pausedAt);
    }
}
