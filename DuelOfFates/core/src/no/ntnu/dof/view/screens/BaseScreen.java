package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import no.ntnu.dof.controller.ScreenManager;

public abstract class BaseScreen extends ScreenAdapter {
    protected SpriteBatch batch;
    protected Sprite backBtn;
    protected Rectangle backBtnBounds;
    protected Sprite background;

    public BaseScreen() {
        this.batch = new SpriteBatch();

        // Initialize the background
        Texture backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        background = new Sprite(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize the back button and its bounds
        Texture backBtnTexture = new Texture(Gdx.files.internal("backBtn.png"));
        backBtn = new Sprite(backBtnTexture);
        backBtn.setSize(50, 50);
        backBtn.setPosition(20, Gdx.graphics.getHeight() - backBtn.getHeight() - 20);
        backBtnBounds = new Rectangle(backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());
    }

    protected void handleBackButtonInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (backBtnBounds.contains(touchX, touchY)) {
                ScreenManager.popScreen();
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        batch.begin();
        background.draw(batch); // Draw the background
        backBtn.draw(batch); // Draw the back button
        batch.end();

        handleBackButtonInput();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backBtn.getTexture().dispose();
        background.getTexture().dispose();
    }
}
