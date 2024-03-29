package no.ntnu.dof.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.dof.controller.gameplay.GameController;
import no.ntnu.dof.controller.network.AuthInterface;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.screens.FightScreen;
import no.ntnu.dof.view.screens.LoginScreen;

public class DuelOfFates extends com.badlogic.gdx.Game {
    private final AuthInterface auth;
    SpriteBatch batch;

    public DuelOfFates(AuthInterface auth) {
        this.auth = auth;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Initialize the login screen as the first screen
        this.setScreen(new FightScreen(new Game(Game.demoPlayer("p1"), Game.demoPlayer("p2"))));

        // TODO remove CLI gameplay demo
        //new GameController(Game.demoPlayer("p1"), Game.demoPlayer("p2")).gameLoop();
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
