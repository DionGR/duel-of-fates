package no.ntnu.dof.controller;

import com.badlogic.gdx.audio.Music;

public class SoundController {

    protected Music music;
    protected boolean isSoundOn;

    public SoundController() {
        this.isSoundOn = true;
    }

    public Music getMusic() {
        return music;
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public void setSoundBool(boolean soundBool) {
        isSoundOn = soundBool;
    }

    public void toggleSound() {
        if (isSoundOn) {
            music.pause();
        } else {
            music.play();
        }
        isSoundOn = !isSoundOn;
    }
}
