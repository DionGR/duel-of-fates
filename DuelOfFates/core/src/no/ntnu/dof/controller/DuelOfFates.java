package no.ntnu.dof.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.ntnu.dof.controller.gameplay.GameController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.screens.LoginScreen;


public class DuelOfFates extends com.badlogic.gdx.Game {
    SpriteBatch batch;
    AssetManager assetManager;

	public DuelOfFates() {}

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("menuBackground.png", Texture.class);
        assetManager.finishLoading(); // Blocks until all assets are loaded

        // Initialize first screen and ScreenManager
        ScreenManager.initialize(this, batch, assetManager);
        ScreenManager.transitionToLogin();

        // TODO remove CLI gameplay demo
        // new GameController(Game.demoPlayer("p1"), Game.demoPlayer("p2")).gameLoop();
	}

    @Override
    public void render() {
        super.render(); // Important to call this to ensure the screen is rendered
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
        super.dispose(); // Dispose the screen and other resources
    }
}
