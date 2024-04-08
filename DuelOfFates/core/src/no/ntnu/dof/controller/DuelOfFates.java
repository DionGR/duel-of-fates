package no.ntnu.dof.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.Data;
import no.ntnu.dof.controller.gameplay.GameController;
import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameLobbies;
import no.ntnu.dof.model.User;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.screens.FightScreen;
import no.ntnu.dof.view.screens.LoginScreen;

public class DuelOfFates extends com.badlogic.gdx.Game {
    private SpriteBatch batch;
    private AssetManager assetManager;

    @Getter
    @Setter
    private User currentUser;

    // Ensure this method returns a valid GameLobbies instance
    @Getter
    private GameLobbies gameLobbies;

	public DuelOfFates() {}

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("menuBackground.png", Texture.class);
        assetManager.load("backBtn.png", Texture.class);
        assetManager.finishLoading(); // Blocks until all assets are loaded

        // Initialize first screen and ScreenManager
        ScreenManager.initialize(this, batch, assetManager);
        ScreenManager.transitionToLogin();


        // Fetch game lobbies
        this.gameLobbies = new GameLobbies();
        // TODO remove CLI gameplay demo
        ServiceLocator.getAuthService().signIn("p1", "p1", new AuthCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String message) {

            }
        });

        GameController gameController = new GameController(Game.demoPlayer("p1"), Game.demoPlayer("p2"));

        // Initialize the fight screen as the first screen
        this.setScreen(new FightScreen(gameController.getGame()));
        new Thread(gameController::gameLoop).start();
	}


    public void loginSuccess() {
        // Simulate user creation based on email for this example
        this.currentUser = ServiceLocator.getAuthService().createGameUserFromFirebaseUser();

        // Navigate to the menu screen
        Gdx.app.postRunnable(() -> {
            ScreenManager.transitionToMenu();
        });
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
