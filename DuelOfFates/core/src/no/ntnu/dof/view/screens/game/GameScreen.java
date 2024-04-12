package no.ntnu.dof.view.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import no.ntnu.dof.controller.gameplay.player.ClickHostPlayerController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.entity.label.TextLabel;
import no.ntnu.dof.view.entity.view.GameView;


public class GameScreen implements Screen {
    private Stage stage;

    private final GameView gameView;
    private final Game game;

    public GameScreen(Game game) {
        this.gameView = new GameView(game);
        this.game = game;
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

        if(!game.isOver()) {
            stage.act(delta);
            stage.draw();
        } else {
            EndScreen();
        }

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

    public void EndScreen() {
        TextLabel endLabel = new TextLabel("WINNER", 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1, Color.GREEN);
        stage.addActor(endLabel.getText());
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        TextButton ReturnToMenu = new TextButton("End Turn", skin, "default");
        //ReturnToMenu.addListener();
    }


}
