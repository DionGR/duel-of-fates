package no.ntnu.dof;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.dof.State.Fight;
import no.ntnu.dof.State.GameStateManager;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	private GameStateManager gsm;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = GameStateManager.getInstance();
		gsm.push(new Fight());
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime()); //We update the state
		gsm.render(batch); //Then we render the state
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
