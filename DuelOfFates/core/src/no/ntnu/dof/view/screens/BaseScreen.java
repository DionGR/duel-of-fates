package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.controller.SoundController;
import no.ntnu.dof.controller.gameplay.di.DaggerGameLobbyControllerComponent;
import no.ntnu.dof.controller.gameplay.di.GameLobbyControllerComponent;
import no.ntnu.dof.view.di.BaseScreenComponent;
import no.ntnu.dof.view.di.DaggerBaseScreenComponent;

public abstract class BaseScreen extends ScreenAdapter {
    public static Texture soundOnTexture = new Texture(Gdx.files.internal("soundOn.png"));
    public static Texture soundOffTexture = new Texture(Gdx.files.internal("soundOff.png"));

    protected SpriteBatch batch;
    protected Sprite background;
    protected Stage stage;

    // Sound
    private ImageButton soundBtn;

    @Inject
    @Named("soundController")
    protected SoundController soundController;

    public BaseScreen() {
        BaseScreenComponent baseScreenComponent = DaggerBaseScreenComponent.create();
        baseScreenComponent.inject(this);

        this.batch = new SpriteBatch();

        // Initialize the background
        Texture backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        background = new Sprite(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        soundBtn = new ImageButton(new TextureRegionDrawable(getSoundTexture()));

        soundBtn.setSize(60,60);
        soundBtn.setPosition(10,10);

        soundBtn.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Sound button clicked");
                soundController.toggleSound();
                soundBtn.setBackground(new TextureRegionDrawable(getSoundTexture()));

            }
        });


    }

    private Texture getSoundTexture(){
        if(soundController.isSoundOn()){
            return soundOnTexture;
        } else {
            return soundOffTexture;
        }
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), this.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(soundBtn);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        batch.begin();
        background.draw(batch); // Draw the background
        batch.end();
        renderSoundButton(delta);
    }

    public void renderSoundButton(float delta){
//        soundBtn.draw(batch,1);
        this.stage.act(delta);
        this.stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        background.getTexture().dispose();

        soundOnTexture.dispose();
        soundOffTexture.dispose();
    }
}
