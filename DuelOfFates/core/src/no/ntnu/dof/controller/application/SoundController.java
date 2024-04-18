package no.ntnu.dof.controller.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import lombok.Getter;

public class SoundController extends ClickListener {
    protected Music music;
    @Getter protected boolean isSoundOn;
    private static final SoundController soundController = new SoundController();

    private SoundController() {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        this.music.setLooping(true);
        this.music.setVolume(0.3f);
        this.isSoundOn = true;
    }

    public static SoundController getInstance(){
        return soundController;
    }

    public void toggleSound() {
        if (isSoundOn) {
            music.pause();
        } else {
            music.play();
        }
        isSoundOn = !isSoundOn;
        Gdx.app.log("Application", "Sound turned " + (isSoundOn ? "on" : "off"));
    }

    void start() {
        music.stop();
        if (isSoundOn) music.play();
    }

    void stop() {
        music.stop();
    }

    public void dispose() {
        music.dispose();
    }
}
