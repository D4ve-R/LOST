package lost.macpan;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[10];
    int current;
    long pausedAt;

    public Sound() {
        soundURL[0] = getClass().getResource("/music/MenuMusic.wav");
        soundURL[1] = getClass().getResource("/music/Emil's Shop");
        soundURL[2] = getClass().getResource("/music/Birth of a Wish");
    }

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

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void pause() {
        pausedAt = clip.getMicrosecondPosition();
        clip.stop();
    }

    public void resume() {
        setFile(current, true);
        clip.start();
    }
}
