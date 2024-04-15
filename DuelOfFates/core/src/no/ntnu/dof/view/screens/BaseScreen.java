package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import no.ntnu.dof.controller.ScreenController;

public abstract class BaseScreen extends ScreenAdapter {
    protected SpriteBatch batch;
    protected Sprite background;

    // Sound
    private Sprite soundBtn;
    private Texture soundOnTexture;
    private Texture soundOffTexture;
    private Rectangle soundBtnBounds;

    public BaseScreen() {
        this.batch = new SpriteBatch();

        // Initialize the background
        Texture backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        background = new Sprite(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        // Setting sound buttons
        soundOnTexture = new Texture(Gdx.files.internal("soundOn.png"));
        soundOffTexture = new Texture(Gdx.files.internal("soundOff.png"));

        soundBtn.setSize(60,60);
        soundBtn.setPosition(10,10);
        soundBtnBounds = new Rectangle(soundBtn.getX(),soundBtn.getY(),soundBtn.getWidth(),soundBtn.getHeight());

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        batch.begin();
        background.draw(batch); // Draw the background
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.getTexture().dispose();
    }
}
