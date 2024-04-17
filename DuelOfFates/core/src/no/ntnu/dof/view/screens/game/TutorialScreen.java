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
    private Actor ClickableArea;
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
        ClickableArea = new Actor();
        ClickableArea.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        activeLabel = new Label("", skin, "default");
        float width = (gameView.getHostPlayerView().getX()+gameView.getHostPlayerView().getWidth()) - gameView.getOpponentPlayerView().getX();
        float height = Gdx.graphics.getHeight()-gameView.getHostPlayerView().getY();
        activeLabel.setBounds(Gdx.graphics.getWidth()/2f-width/2, Gdx.graphics.getHeight()-height, width, height);
        activeLabel.setPosition(Gdx.graphics.getWidth()/2f - activeLabel.getWidth()/2f, Gdx.graphics.getHeight() - activeLabel.getHeight());
        activeLabel.setAlignment(Align.top, Align.center);

        stage.addActor(gameView);
        stage.addActor(HighlightedArea);
        stage.addActor(ClickableArea);
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
        activeLabel.remove();
        HighlightedArea.remove();
        ClickableArea.remove();
        skin.dispose();
    }

    public void GamePresentation()
    {
        activeLabel.setText("Welcome to Duel of Fates! \n Here your are in the tutorial \n where you will learn the basics of the game");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ClickableArea.remove();
                UIPresentation();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
    }

    public void UIPresentation()
    {
        activeLabel.setText("We will go over the UI elements of the game");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ClickableArea.remove();
                ShowOpponent();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
    }

    public void ShowOpponent() {
        activeLabel.setText("This is your opponent");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ClickableArea.remove();
                ShowOpponentHealthBar();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        //Highlight health bar
        HighlightActor(gameView.getOpponentPlayerView().getGraphics());
    }

    public void ShowOpponentHealthBar()
    {
        activeLabel.setText("This the health bar of your opponent,\nwhen it reaches 0 and you are still alive,\nyou win the game");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ClickableArea.remove();
                ShowPlayer();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        //Highlight health bar
        HighlightActor(gameView.getOpponentPlayerView().getHealthBarView());
    }

    public void ShowPlayer() {
        activeLabel.setText("This is you");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ClickableArea.remove();
                ShowHealthBar();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        //Highlight health bar
        HighlightActor(gameView.getHostPlayerView().getGraphics());
    }

    public void ShowHealthBar()
    {
        activeLabel.setText("This is your health bar,\nif it reaches 0 you lose the game");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ClickableArea.remove();
                ShowMana();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        //Highlight health bar
        HighlightActor(gameView.getHostPlayerView().getHealthBarView());
    }

    public void ShowMana()
    {
        activeLabel.setText("This is your Mana pool,\neach card played will cost you mana");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ClickableArea.remove();
                ShowHand();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        //Highlight mana pool
        HighlightActor(gameView.getHostPlayerView().getManaPool());
    }

    public void ShowHand()
    {
        activeLabel.setText("This is your hand,\nyou can play cards from here by clicking on them\nTry playing a card !!");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                HighlightedArea.remove();
                ClickableArea.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        //Highlight hand
        HighlightActor(gameView.getHostPlayerView().getHandView());
    }

    public void ShowManaConsumption()
    {
        stage.addActor(HighlightedArea);
        activeLabel.setText("When you play a card,\nit's mana cost is removed from your mana pool");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ClickableArea.remove();
                ShowEndTurn();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        HighlightActor(gameView.getHostPlayerView().getManaPool());
    }

    public void ShowEndTurn()
    {
        activeLabel.setText("This is the end turn button,\nYou can click on it whenever you want\nIt will pass turn to your opponent");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                HighlightedArea.remove();
                ClickableArea.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        //Highlight end turn button
        HighlightActor(gameView.getHostPlayerView().getEndTurnButton());
    }

    public void ShowPlayedCard() {
        int i =0;
        while(gameView.getOpponentPlayerView().getLastPlayedCard() == null) {
            i++;
            System.out.println(i);
        }
        stage.addActor(HighlightedArea);
        activeLabel.setText("Your opponent have played a card,\nyou can see it here");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ClickableArea.remove();
                ShowArmor();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        //Highlight hand
        HighlightActor(gameView.getOpponentPlayerView().getLastPlayedCard());
    }

    public void ShowArmor()
    {
        activeLabel.setText("This is your armor,\nit will take damage before your health\nBut in contrary to health,\nit cannot be regenerated");
        ClickableArea.getListeners().clear();
        ClickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                HighlightedArea.remove();
                ClickableArea.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        stage.addActor(ClickableArea);
        //Highlight armor
        HighlightActor(gameView.getHostPlayerView().getArmorPool());
    }

    private void HighlightActor(Actor actor) {
        Vector2 stageCoordinates = actor.localToStageCoordinates(new Vector2(0, 0));
        HighlightedArea.setX(stageCoordinates.x);
        HighlightedArea.setY(stageCoordinates.y);
        HighlightedArea.setWidth(actor.getWidth());
        HighlightedArea.setHeight(actor.getHeight());
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
