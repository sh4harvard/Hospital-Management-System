package Hospital.GUI;

import javax.sound.sampled.*;
import java.io.File;

public class BackgroundMusic {

    private Clip clip;

    public void play() {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("src/Resource/music.wav"));

            clip = AudioSystem.getClip();
            clip.open(audio);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}