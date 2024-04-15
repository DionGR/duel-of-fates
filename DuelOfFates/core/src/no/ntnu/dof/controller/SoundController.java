package no.ntnu.dof.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoundController extends ClickListener {

    protected Music music;
    protected boolean isSoundOn;
    private static SoundController soundController;

    private SoundController() {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        this.music.play();
        this.music.setLooping(true);
        this.music.setVolume(0.3f);
        this.isSoundOn = true;
    }

    public static SoundController getInstance(){
        if (soundController == null) {
            soundController = new SoundController();
        }

        return soundController;
    }

    public void toggleSound() {
        if (isSoundOn) {
            music.pause();
        } else {
            music.play();
        }
        isSoundOn = !isSoundOn;
    }

    public void dispose() {
        music.dispose();
    }
}
