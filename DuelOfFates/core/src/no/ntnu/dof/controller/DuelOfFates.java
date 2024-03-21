package no.ntnu.dof.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.dof.controller.network.AuthInterface;
import no.ntnu.dof.view.screens.LoginScreen;

public class DuelOfFates extends Game {
	private AuthInterface auth;
	SpriteBatch batch;
	Texture img;

	public DuelOfFates(AuthInterface auth) {
		this.auth = auth;
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		// Initialize the login screen as the first screen
		this.setScreen(new LoginScreen(this, auth));
	}

	@Override
	public void render() {
		super.render(); // Important to call this to ensure the screen is rendered
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		super.dispose(); // Dispose the screen and other resources
	}
}
