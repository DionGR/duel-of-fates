package no.ntnu.dof.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.dof.view.screens.LobbiesScreen;
import no.ntnu.dof.view.screens.LoginScreen;
import no.ntnu.dof.view.screens.MenuScreen;

public class DuelOfFates extends Game {
	SpriteBatch batch;
	Texture img;

	public DuelOfFates() {}

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

//		this.setScreen(new LoginScreen(this, auth));
		this.setScreen(new LobbiesScreen(this));
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
