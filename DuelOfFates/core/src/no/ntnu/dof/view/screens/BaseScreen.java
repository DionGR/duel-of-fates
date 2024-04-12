package no.ntnu.dof.view.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.ScreenController;

public abstract class BaseScreen extends ScreenAdapter {
    protected SpriteBatch batch;
    protected Sprite background;

    protected DuelOfFates game;

    private Sprite soundBtn;
    private Texture soundOnTexture;
    private Texture soundOffTexture;

    private Rectangle soundBtnBounds;


    public BaseScreen() {}
    public BaseScreen(DuelOfFates game) {
        super();
        this.batch = new SpriteBatch();
        this.game = game;

        // Initialize the background
        Texture backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        background = new Sprite(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Music button
        soundOnTexture = new Texture(Gdx.files.internal("soundOn.png"));
        soundOffTexture = new Texture(Gdx.files.internal("soundOff.png"));
        if (game.getSoundBool()) {
            soundBtn = new Sprite(soundOnTexture);
        } else {
            soundBtn = new Sprite(soundOffTexture);
        }
        soundBtn.setSize(60, 60);
        soundBtn.setPosition(10, 10);
        soundBtnBounds = new Rectangle(soundBtn.getX(), soundBtn.getY(), soundBtn.getWidth(), soundBtn.getHeight());
    }

    protected void handleSoundButtonInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (getSoundBtnBounds().contains(touchX,touchY)) {
                toggleSound();
            }
        }
    }

    public void toggleSound() {
        if (game.getSoundBool()) {
            game.pauseMusic();
            // Copy the attributes from the existing Sprite to the new one
            Sprite newSprite = new Sprite(soundOffTexture);
            newSprite.setPosition(soundBtn.getX(), soundBtn.getY());
            newSprite.setSize(soundBtn.getWidth(), soundBtn.getHeight());
            soundBtn = newSprite;
        } else {
            game.playMusic();
            // Copy the attributes from the existing Sprite to the new one
            Sprite newSprite = new Sprite(soundOnTexture);
            newSprite.setPosition(soundBtn.getX(), soundBtn.getY());
            newSprite.setSize(soundBtn.getWidth(), soundBtn.getHeight());
            soundBtn = newSprite;
        }
        game.setSoundBool(!game.getSoundBool());
    }

    public Sprite getSoundBtn() {
        return soundBtn;
    }

    public Rectangle getSoundBtnBounds () {
        return soundBtnBounds;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        batch.begin();
        background.draw(batch); // Draw the background
        getSoundBtn().draw(batch);
        batch.end();
        handleSoundButtonInput();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        background.getTexture().dispose();
        getSoundBtn().getTexture().dispose();
    }
}