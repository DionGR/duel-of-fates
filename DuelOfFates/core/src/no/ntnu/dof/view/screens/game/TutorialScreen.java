package no.ntnu.dof.view.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;
import java.util.Vector;


import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.Image;
import no.ntnu.dof.view.entity.label.TextLabel;
import no.ntnu.dof.view.entity.view.GameView;
import no.ntnu.dof.view.gameplay.HighlightingArea;

public class TutorialScreen implements Screen {
    private Stage stage;
    private final GameView gameView;
    private final Game game;
    private Label activeLabel;
    private HighlightingArea HighlightedArea;
    private final Skin skin = new Skin(Gdx.files.internal("UISkin.json"));
    private boolean gameEndScreenShown;

    public TutorialScreen(Game game) {
        this.gameView = new GameView(game);
        this.activeLabel = null;
        this.game = game;
        this.gameEndScreenShown = false;
        stage = new Stage();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        HighlightedArea = new HighlightingArea(0, 0, 0, 0);

        stage.addActor(gameView);
        stage.addActor(HighlightedArea);
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
        stage.dispose();
    }

    public void ActiveLabelPosition()
    {
        activeLabel.setPosition(Gdx.graphics.getWidth()/2f - activeLabel.getWidth()/2f, Gdx.graphics.getHeight()*0.9f - activeLabel.getHeight());
        activeLabel.setAlignment(Align.center);
    }

    public void GamePresentation()
    {
        activeLabel = new Label("Welcome to Duel of Fates! \n Here your are in the tutorial where you will learn the basics of the game \n The goal is to reduce the opponents health to 0", skin, "default");
        float width = (gameView.getHostPlayerView().getX()+gameView.getHostPlayerView().getWidth()) - gameView.getOpponentPlayerView().getX();
        float height = Gdx.graphics.getHeight()*0.9f-gameView.getHostPlayerView().getY();
        activeLabel.setBounds(Gdx.graphics.getWidth()/2f - width/2, Gdx.graphics.getHeight()*0.9f - height, width, height);
        activeLabel.setAlignment(Align.center);
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                UIPresentation();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        HighlightActor(activeLabel);
    }

    public void UIPresentation()
    {
        activeLabel.setText("We will go over the UI elements of the game");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowOpponent();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
    }

    public void ShowOpponent() {
        activeLabel.setText("This is your opponent");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowOpponentHealthBar();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        //Highlight health bar
        HighlightActor(gameView.getOpponentPlayerView().getGraphics());
    }

    public void ShowOpponentHealthBar()
    {
        activeLabel.setText("This the health bar of your opponent, when it reaches 0 and you are still alive, you win the game");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowPlayer();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        //Highlight health bar
        HighlightActor(gameView.getOpponentPlayerView().getHealthBarView());
    }

    public void ShowPlayer() {
        activeLabel.setText("This is you");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowHealthBar();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        //Highlight health bar
        HighlightActor(gameView.getHostPlayerView().getGraphics());
    }

    public void ShowHealthBar()
    {
        activeLabel.setText("This is your health bar, if it reaches 0 you lose the game");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowMana();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        //Highlight health bar
        HighlightActor(gameView.getHostPlayerView().getHealthBarView());
    }

    public void ShowMana()
    {
        activeLabel.setText("This is your Mana pool, each card has a mana cost \n You regain some mana at the start of your turn");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowHand();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        //Highlight mana pool
        HighlightActor(gameView.getHostPlayerView().getManaPool());
    }

    public void ShowHand()
    {
        activeLabel.setText("This is your hand, you can play cards from here by clicking on them \n Try playing a card");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                HighlightedArea.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);

        //Highlight hand
        HighlightActor(gameView.getHostPlayerView().getHandView());
    }

    public void ShowManaConsumption()
    {
        stage.addActor(HighlightedArea);
        activeLabel.setText("When you play a card, the mana cost is removed from your mana pool");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowEndTurn();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);

        HighlightActor(gameView.getHostPlayerView().getManaPool());
    }

    public void ShowEndTurn()
    {
        activeLabel.setText("This is the end turn button, click it when you are done with your turn");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                HighlightedArea.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);

        //Highlight end turn button
        HighlightActor(gameView.getHostPlayerView().getEndTurnButton());
    }

    public void ShowPlayedCard() {
        while(gameView.getOpponentPlayerView().getLastPlayedCard() == null) {
            System.out.println("Waiting");
        }
        stage.addActor(HighlightedArea);
        activeLabel.setText("Your opponent have played a card, you can see it here");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowArmor();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);

        //Highlight hand
        HighlightActor(gameView.getOpponentPlayerView().getLastPlayedCard());
    }

    public void ShowArmor()
    {
        activeLabel.setText("This is the armor, it will take damage before your health \n But in contrary to health, it cannot be regenerated");
        ActiveLabelPosition();
        activeLabel.getListeners().clear();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                HighlightedArea.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);

        //Highlight armor
        HighlightActor(gameView.getHostPlayerView().getArmorPool());
    }

    private void HighlightActor(Actor actor) {
        Vector2 stageCoordinates = actor.localToStageCoordinates(new Vector2(0, 0));
        System.out.println(actor);
        System.out.println(HighlightedArea.getWidth() + " " + HighlightedArea.getHeight() + " " + HighlightedArea.getX() + " " + HighlightedArea.getY());
        HighlightedArea.setX(stageCoordinates.x);
        HighlightedArea.setY(stageCoordinates.y);
        HighlightedArea.setWidth(actor.getWidth());
        HighlightedArea.setHeight(actor.getHeight());
        System.out.println(HighlightedArea.getWidth() + " " + HighlightedArea.getHeight() + " " + HighlightedArea.getX() + " " + HighlightedArea.getY());
    }

    public void showEndScreen() {
        float midX = Gdx.graphics.getWidth() / 2.0f;
        float midY = Gdx.graphics.getHeight() / 2.0f;

        Image resultWindow = new Image("resultWindow.png", 0.5f);
        resultWindow.setPosition(midX, midY, Align.center);
        stage.addActor(resultWindow);

        TextLabel endLabel = new TextLabel(
                game.getHost().isDead() ? "You lost" : "You won",
                0, midY / 10.0f,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                2,
                game.getHost().isDead() ? Color.RED : Color.GREEN
        );
        stage.addActor(endLabel.getText());

        Skin skin = new Skin(Gdx.files.internal("UISkin.json"));
        TextButton returnToMenu = new TextButton("Exit", skin, "default");
        returnToMenu.setPosition(midX, midY - returnToMenu.getHeight() * 2, Align.center);
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
