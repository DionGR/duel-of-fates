package no.ntnu.dof.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.dof.view.screens.LobbiesScreen;
import no.ntnu.dof.view.screens.LobbyScreen;
import no.ntnu.dof.controller.gameplay.GameController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.screens.FightScreen;
import no.ntnu.dof.view.screens.LoginScreen;
import no.ntnu.dof.view.screens.MenuScreen;

public class DuelOfFates extends com.badlogic.gdx.Game {
    SpriteBatch batch;

	public DuelOfFates() {}

    @Override
    public void create() {
        batch = new SpriteBatch();

        // TODO remove CLI gameplay demo
        GameController gameController = new GameController(Game.demoPlayer("p1"), Game.demoPlayer("p2"));

        // Initialize the fight screen as the first screen
        this.setScreen(new FightScreen(gameController.getGame()));
        new Thread(gameController::gameLoop).start();
    }

    @Override
    public void render() {
        super.render(); // Important to call this to ensure the screen is rendered
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose(); // Dispose the screen and other resources
    }
}
