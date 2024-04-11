package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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

    protected Rectangle soundBtnBounds;
    protected Sprite soundBtn;

    protected DuelOfFates game;

    public BaseScreen() {
        super();
        this.batch = new SpriteBatch();

        // Initialize the background
        Texture backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        background = new Sprite(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Texture soundBtnTexture = new Texture(Gdx.files.internal("soundOn.png"));
        soundBtn = new Sprite(soundBtnTexture);
        soundBtn.setSize(220, 220);
        soundBtn.setPosition(10, 10);
        soundBtnBounds = new Rectangle(soundBtn.getX(), soundBtn.getY(), soundBtn.getWidth(), soundBtn.getHeight());
    }

    protected void handleSoundButtonInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (soundBtnBounds.contains(touchX,touchY)) {
                toggleSound();
            }
        }
    }

    protected void toggleSound() {
        if (game.getSoundBool()) {
            game.getMusic().pause();
        } else {
            game.getMusic().play();
        }
        game.setSoundBool(!game.getSoundBool());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        batch.begin();
        background.draw(batch); // Draw the background
        soundBtn.draw(batch);
        batch.end();
        handleSoundButtonInput();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        background.getTexture().dispose();
        soundBtn.getTexture().dispose();
    }
}
