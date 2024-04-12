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

public abstract class ReturnableScreen extends BaseScreen{
    protected Sprite backBtn;
    protected Rectangle backBtnBounds;

    protected Rectangle soundBtnBounds;
    protected Sprite soundBtn;

    protected DuelOfFates game;

    public ReturnableScreen(DuelOfFates game) {
        super(game);

        // Initialize the back button and its bounds
        Texture backBtnTexture = new Texture(Gdx.files.internal("backBtn.png"));
        backBtn = new Sprite(backBtnTexture);
        backBtn.setSize(30, 50);
        backBtn.setPosition(20, Gdx.graphics.getHeight() - backBtn.getHeight() - 20);
        backBtnBounds = new Rectangle(backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());

        Texture soundBtnTexture = new Texture(Gdx.files.internal("soundOn.png"));
        soundBtn = new Sprite(soundBtnTexture);
        soundBtn.setSize(60, 60);
        soundBtn.setPosition(10, 10);
        soundBtnBounds = new Rectangle(soundBtn.getX(), soundBtn.getY(), soundBtn.getWidth(), soundBtn.getHeight());
    }

    protected void handleBackButtonInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (backBtnBounds.contains(touchX, touchY)) {
                ScreenController.popScreen();
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch); // Draw the background
        backBtn.draw(batch); // Draw the back button
        soundBtn.draw(batch);
        batch.end();

        handleBackButtonInput();
        handleSoundButtonInput();
    }

    @Override
    public void dispose() {
        super.dispose();
        backBtn.getTexture().dispose();
    }
}
