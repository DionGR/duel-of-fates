package no.ntnu.dof.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.Getter;
import lombok.Setter;
import no.ntnu.dof.model.User;

public class DuelOfFates extends com.badlogic.gdx.Game {
    private SpriteBatch batch;
    private AssetManager assetManager;

    @Getter
    @Setter
    private User currentUser;

	public DuelOfFates() {}

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("menuBackground.png", Texture.class);
        assetManager.load("backBtn.png", Texture.class);
        assetManager.finishLoading(); // Blocks until all assets are loaded

        // Initialize first screen and ScreenManager
        ScreenController.initialize(this, batch, assetManager);
        ScreenController.transitionToLogin();
	}

    @Override
    public void render() {
        super.render(); // Important to call this to ensure the screen is rendered
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
        SoundController.getInstance().dispose();
        super.dispose(); // Dispose the screen and other resources
    }
}
