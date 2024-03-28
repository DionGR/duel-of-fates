package no.ntnu.dof.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.dof.view.screens.LobbiesScreen;
import no.ntnu.dof.view.screens.LobbyScreen;
import no.ntnu.dof.controller.gameplay.GameController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.screens.LoginScreen;
import no.ntnu.dof.view.screens.MenuScreen;

public class DuelOfFates extends com.badlogic.gdx.Game {
    SpriteBatch batch;

	public DuelOfFates() {}

    @Override
    public void create() {
        batch = new SpriteBatch();
      
		this.setScreen(new MenuScreen(this));

        // TODO remove CLI gameplay demo
        new GameController(Game.demoPlayer("p1"), Game.demoPlayer("p2")).gameLoop();
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
