package no.ntnu.dof.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import no.ntnu.dof.controller.application.ScreenController;

public abstract class ReturnableScreen extends BaseScreen {
    protected Sprite backBtn;
    protected Rectangle backBtnBounds;

    public ReturnableScreen() {
        // Initialize the back button and its bounds
        Texture backBtnTexture = new Texture(Gdx.files.internal("backBtn.png"));
        backBtn = new Sprite(backBtnTexture);
        backBtn.setSize(getScreenWidth()*0.05f, getScreenHeight()*0.16f);
        backBtn.setPosition(20, Gdx.graphics.getHeight() - backBtn.getHeight() - 20);
        backBtnBounds = new Rectangle(backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());
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
        batch.begin();
        background.draw(batch); // Draw the background
        backBtn.draw(batch); // Draw the back button
        batch.end();
        super.renderSoundButton(delta);
        handleBackButtonInput();
    }

    @Override
    public void dispose() {
        super.dispose();
        backBtn.getTexture().dispose();
    }
}
