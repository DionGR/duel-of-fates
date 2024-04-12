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

    protected DuelOfFates game;
    protected Sprite soundBtn;


    public ReturnableScreen(DuelOfFates game) {
        super(game);
        this.batch = new SpriteBatch();

        // Initialize the back button and its bounds
        Texture backBtnTexture = new Texture(Gdx.files.internal("backBtn.png"));
        backBtn = new Sprite(backBtnTexture);
        backBtn.setSize(30, 50);
        backBtn.setPosition(20, Gdx.graphics.getHeight() - backBtn.getHeight() - 20);
        backBtnBounds = new Rectangle(backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());

        // Initialize sound button
        soundBtn = new Sprite(game.getSoundBtn());
    }

    protected void handleBackButtonInput() {
        if (Gdx.input.justTouched()) {
            float touchX1 = Gdx.input.getX();
            float touchY1 = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (backBtnBounds.contains(touchX1, touchY1)) {
                ScreenController.popScreen();
            }
        }
    }
//
//    protected void handleSoundButtonInput() {
//        if (Gdx.input.justTouched()) {
//            float touchX = Gdx.input.getX();
//            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
//            if (game.getSoundBtnBounds().contains(touchX,touchY)) {
//                game.toggleSound();
//            }
//        }
//    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch); // Draw the background
        backBtn.draw(batch); // Draw the back button
        soundBtn.draw(batch);
        batch.end();

        handleBackButtonInput();
//        handleSoundButtonInput();
    }

    @Override
    public void dispose() {
        super.dispose();
        backBtn.getTexture().dispose();
        soundBtn.getTexture().dispose();
    }
}
