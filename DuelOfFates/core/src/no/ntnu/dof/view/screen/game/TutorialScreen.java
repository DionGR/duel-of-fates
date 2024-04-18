package no.ntnu.dof.view.screen.game;

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

import no.ntnu.dof.controller.application.ScreenController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.gameplay.texture.Image;
import no.ntnu.dof.view.gameplay.label.TextLabel;
import no.ntnu.dof.view.gameplay.entity.GameView;
import no.ntnu.dof.view.gameplay.control.HighlightingArea;


public class TutorialScreen implements Screen {
    private final Stage stage;
    private final GameView gameView;
    private final Game game;
    private Label activeLabel;
    private HighlightingArea highlightedArea;
    private Actor clickableArea;
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
        highlightedArea = new HighlightingArea(0, 0, 0, 0);
        clickableArea = new Actor();
        clickableArea.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        activeLabel = new Label("", skin, "default");
        activeLabel.setFontScale(Gdx.graphics.getHeight()*0.0025f);
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
        highlightedArea.remove();
        clickableArea.remove();
        skin.dispose();
    }

    public void showGamePresentation() {
        activeLabel.setText("Welcome to Duel of Fates! \n In this tutorial you will \n learn about the basics of the game");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                clickableArea.remove();
                showUIPresentation();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
    }

    public void showUIPresentation() {
        activeLabel.setText("First, we will go over the UI elements of the game");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                clickableArea.remove();
                showOpponent();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
    }

    public void showOpponent() {
        activeLabel.setText("This is your opponent!");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                clickableArea.remove();
                showOpponentHealthBar();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        //Highlight opponent
        highlightActor(gameView.getOpponentPlayerView().getGraphics());
    }

    public void showOpponentHealthBar() {
        activeLabel.setText("This the health bar of your opponent.\nYour goal is to drain\nit so you win the game!");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                clickableArea.remove();
                showPlayer();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        //Highlight health bar
        highlightActor(gameView.getOpponentPlayerView().getHealthBarView());
    }

    public void showPlayer() {
        activeLabel.setText("This is you!");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                clickableArea.remove();
                showHealthBar();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        //Highlight host
        highlightActor(gameView.getHostPlayerView().getGraphics());
    }

    public void showHealthBar() {
        activeLabel.setText("This is your health bar,\nyou lose the game when it is empty!");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                clickableArea.remove();
                showMana();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        //Highlight health bar
        highlightActor(gameView.getHostPlayerView().getHealthBarView());
    }

    public void showMana() {
        activeLabel.setText("This is your Mana pool,\na resource for playing cards\nEach card played will cost you mana.");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                clickableArea.remove();
                showHand();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        //Highlight mana pool
        highlightActor(gameView.getHostPlayerView().getManaPool());
    }

    public void showHand() {
        activeLabel.setText("This is your hand where you\ncan play cards by clicking on them\nTry playing a card!");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                highlightedArea.remove();
                clickableArea.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        //Highlight hand
        highlightActor(gameView.getHostPlayerView().getHandView());
    }

    public void showManaConsumption() {
        stage.addActor(highlightedArea);
        activeLabel.setText("When you play a card\nit's mana cost will be\nremoved from your mana pool");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                clickableArea.remove();
                showEndTurn();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        highlightActor(gameView.getHostPlayerView().getManaPool());
    }

    public void showEndTurn() {
        activeLabel.setText("This is the end turn button.\nYou can click on it whenever you want,\nit will pass the turn to your opponent");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                highlightedArea.remove();
                clickableArea.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        //Highlight end turn button
        highlightActor(gameView.getHostPlayerView().getEndTurnButton());
    }

    public void showPlayedCard() {
        while (gameView.getOpponentPlayerView().getLastPlayedCard() == null) {
            System.out.print(' ');
        }
        stage.addActor(highlightedArea);
        activeLabel.setText("Your opponent has played a card,\nthis is where you can see it.");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                clickableArea.remove();
                showArmor();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        //Highlight played card
        highlightActor(gameView.getOpponentPlayerView().getLastPlayedCard());
    }

    public void showArmor() {
        activeLabel.setText("This is your armor,\nit takes damage before your health\nbut in contrary to health,\nit cannot be regenerated!");
        clickableArea.getListeners().clear();
        clickableArea.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                highlightedArea.remove();
                clickableArea.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(activeLabel);
        stage.addActor(clickableArea);
        //Highlight armor
        highlightActor(gameView.getHostPlayerView().getArmorPool());
    }

    private void highlightActor(Actor actor) {
        Vector2 stageCoordinates = actor.localToStageCoordinates(new Vector2(0, 0));
        highlightedArea.setX(stageCoordinates.x);
        highlightedArea.setY(stageCoordinates.y);
        highlightedArea.setWidth(actor.getWidth());
        highlightedArea.setHeight(actor.getHeight());
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
                Gdx.graphics.getHeight()*0.006f,
                game.getHost().isDead() ? Color.RED : Color.GREEN
        );
        stage.addActor(endLabel.getText());

        Skin skin = new Skin(Gdx.files.internal("UISkin.json"));
        TextButton returnToMenu = new TextButton("Exit", skin, "default");
        returnToMenu.setWidth(Gdx.graphics.getWidth()*0.10f);
        returnToMenu.setHeight(Gdx.graphics.getHeight()*0.10f);
        returnToMenu.setPosition(midX, midY - returnToMenu.getHeight()*1.25f, Align.center);
        returnToMenu.getStyle().font.getData().setScale(Gdx.graphics.getHeight()*0.004f);

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
