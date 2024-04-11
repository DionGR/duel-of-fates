package no.ntnu.dof.view.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.entity.view.GameView;
import no.ntnu.dof.view.screens.BaseScreen;


public class FightScreen extends BaseScreen {
    private Stage stage;

    private final GameView gameView;

    public FightScreen(Game game) {
        this.gameView = new GameView(game);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.addActor(gameView);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
