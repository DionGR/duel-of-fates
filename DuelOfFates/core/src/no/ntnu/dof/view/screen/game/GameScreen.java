package no.ntnu.dof.view.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import no.ntnu.dof.controller.application.ScreenController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.gameplay.entity.GameView;
import no.ntnu.dof.view.gameplay.label.TextLabel;
import no.ntnu.dof.view.gameplay.texture.Image;


public class GameScreen implements Screen {
    private final GameView gameView;
    private final Game game;
    private Stage stage;
    private boolean gameEndScreenShown;

    public GameScreen(Game game) {
        this.gameView = new GameView(game);
        this.game = game;
        this.gameEndScreenShown = false;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.addActor(gameView);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameEndScreenShown && game.isOver()) {
            showEndScreen();
            gameEndScreenShown = true;
        }

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
        gameView.dispose();
        stage.dispose();
    }

    public void showEndScreen() {
        float midX = Gdx.graphics.getWidth() / 2.0f;
        float midY = Gdx.graphics.getHeight() / 2.0f;

        Image resultWindow = new Image("resultWindow.png", 0.5f);
        resultWindow.setPosition(midX, midY, Align.center);
        stage.addActor(resultWindow);

        TextLabel endLabel = new TextLabel(
                game.getHost().isDead() ? "You Lost" : "You Won",
                0, midY / 10.0f,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                Gdx.graphics.getHeight() * 0.006f,
                game.getHost().isDead() ? Color.RED : Color.GREEN
        );
        stage.addActor(endLabel.getText());

        Skin skin = new Skin(Gdx.files.internal("UISkin.json"));
        TextButton returnToMenu = new TextButton("Exit", skin, "default");
        returnToMenu.setWidth(Gdx.graphics.getWidth() * 0.10f);
        returnToMenu.setHeight(Gdx.graphics.getHeight() * 0.10f);
        returnToMenu.setPosition(midX, midY - returnToMenu.getHeight() * 1.25f, Align.center);
        returnToMenu.getStyle().font.getData().setScale(Gdx.graphics.getHeight() * 0.004f);

        returnToMenu.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.postRunnable(ScreenController::popScreen);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(returnToMenu);
    }
}
