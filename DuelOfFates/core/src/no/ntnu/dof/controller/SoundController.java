package no.ntnu.dof.controller;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoundController extends ClickListener {

    protected Music music;
    protected boolean isSoundOn;

    public SoundController() {
        this.isSoundOn = true;
    }

    public void toggleSound() {
//        if (isSoundOn) {
//            music.pause();
//        } else {
//            music.play();
//        }
        isSoundOn = !isSoundOn;
    }

    public void dispose() {
        music.dispose();
    }
}
