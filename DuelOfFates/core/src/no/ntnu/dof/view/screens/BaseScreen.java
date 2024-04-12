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

    protected Sprite soundBtn;


    public BaseScreen() {}
    public BaseScreen(DuelOfFates game) {
        super();
        this.batch = new SpriteBatch();
        this.game = game;

        // Initialize the background
        Texture backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        background = new Sprite(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize sound button
        soundBtn = new Sprite(game.getSoundBtn());
    }

    protected void handleSoundButtonInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (game.getSoundBtnBounds().contains(touchX,touchY)) {
                game.toggleSound();
            }
        }
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