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
    int pausedAt;                  //progress into suspended track

    public Sound() {    //constructor loads audio files !CURRENTLY FILLED WITH TEST TRACKS ONLY, TO BE REPLACED!
        soundURL[0] = getClass().getResource("/music/main menu music.wav");
        soundURL[1] = getClass().getResource("/music/victory music.wav");
        soundURL[2] = getClass().getResource("/music/in-game music.wav");
        soundURL[3] = getClass().getResource("/music/looser music.wav");
        soundURL[4] = getClass().getResource("/sound effects/coin.wav");
        soundURL[5] = getClass().getResource("/sound effects/exit opening.wav");
        soundURL[6] = getClass().getResource("/sound effects/item pickup.wav");
        soundURL[7] = getClass().getResource("/sound effects/kill enemy.wav");
        soundURL[8] = getClass().getResource("/sound effects/take damage.wav");
        soundURL[9] = getClass().getResource("/music/pause menu music.wav");
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
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
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
    public void loop(int track) {
        clip.setLoopPoints(getLoopOffset(track), -1);
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
        pausedAt = clip.getFramePosition();
        clip.stop();
        if (pausedAt > clip.getFrameLength()){
            pausedAt = (pausedAt - clip.getFrameLength()) % (clip.getFrameLength() - getLoopOffset(current)) + getLoopOffset(current);
        }
    }

    /**
     *Resumes paused playback !NOT TO BE DIRECTLY USED!
     */
    public void resume() {
        setFile(current, true);
        clip.start();
        clip.setFramePosition(pausedAt);
        System.out.println("resumed at " + pausedAt);
        loop(current);
    }

    /**
     * used specifically for playing the pause menu music
     */
    public void playPauseMenu() {
        setFile(9, true);
        clip.start();
        clip.setFramePosition(pausedAt);
    }

    /**
     * used specifically for stopping the pause menu music
     */
    public void stopPauseMenu() {
        pausedAt = clip.getFramePosition();
        clip.stop();
    }

    /**
     * plays a sound effect <br>
     * Available effects: <br>
     * 4: coin <br>
     * 5: exit opening <br>
     * 6: item pickup <br>
     * 7: kill enemy <br>
     * 8: take damage
     * @param effect selected effect
     */
    public void playSoundEffect(int effect){
        setFile(effect, false);
        clip.start();
        clip.loop(0);
    }

    /**
     * internal method used for setting the loop bounds for music
     * @param track the to-be-looped track
     * @return the starting point of the loop measured in audio sample frames
     */
    private int getLoopOffset(int track){
        return switch (track) {
            case 0 -> 70560;
            case 1 -> 211680;
            case 2, 9 -> 182854;
            default -> 0;
        };
    }
}
