package no.ntnu.dof.view.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;


import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.entity.view.GameView;
import no.ntnu.dof.view.gameplay.HighlightingArea;

public class TutorialScreen implements Screen {
    private Stage stage;
    private final GameView gameView;
    private Label activeLabel;
    private HighlightingArea HighlightedArea;
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    private ArrayList<Label> tutorialLabels;

    public TutorialScreen(Game game) {
        this.gameView = new GameView(game);
        this.activeLabel = null;
        this.tutorialLabels = new ArrayList<>();
        stage = new Stage();

        this.tutorialLabels.add(new Label("You can play cards from your hand by clicking on them", skin, "default"));
        this.tutorialLabels.add(new Label("When you can't play anymore cards, your turn is finished automatically \n At the beginning of your turn, you get all your mana point \n and draw until your hand reach is maximum hand size", skin, "default"));
        this.tutorialLabels.add(new Label("On his turn, your opponent will also play card to decrease your health point \n You can play card that get you armor for 1 turn. \n Armor is the grey part of health bar \n And it will take damage before your health", skin, "default"));
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
        stage.dispose();
    }

    public void ActiveLabelPosition()
    {
        activeLabel.setPosition(Gdx.graphics.getWidth()/2f - activeLabel.getWidth()/2f, Gdx.graphics.getHeight()/2f - activeLabel.getHeight()/2f);
        activeLabel.setAlignment(Align.center);
    }

    public void TutorialTurn(int turnNumber)
    {
        turnNumber = turnNumber/2;
        if(turnNumber < tutorialLabels.size()) {
            if (activeLabel != null && !stage.getActors().contains(activeLabel, true)) {
                activeLabel = tutorialLabels.get(turnNumber);
                activeLabel.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                activeLabel.setAlignment(Align.center);
                activeLabel.addListener(new ClickListener() {
                    @Override
                    public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        activeLabel.remove();
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
                stage.addActor(activeLabel);
            }
        }
        else gameView.getGame().getHost().getHand().setMaxSize(3);
    }

    public void GamePresentation()
    {
        activeLabel = new Label("Welcome to Duel of Fates! \n Here your are in the tutorial where you will learn the basics of the game \n The goal is to reduce the opponents health to 0", skin, "default");
        activeLabel.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        activeLabel.setAlignment(Align.center);
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                UIPresentation();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
    }

    public void UIPresentation()
    {
        activeLabel.setText("We will go over the UI elements of the game");
        ActiveLabelPosition();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowOpponentHealthBar();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
    }

    public void ShowOpponentHealthBar()
    {
        activeLabel.setText("This the health bar of your opponent, when it reaches 0 you win the game");
        ActiveLabelPosition();
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
        HighlightActor(gameView.getOpponentPlayerView().getHealthBarView());
    }

    public void ShowHealthBar()
    {
        activeLabel.setText("This is your health bar, when it reaches 0 you lose the game");
        ActiveLabelPosition();
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
        activeLabel.setText("This is your Mana pool, each card has a mana cost \n You regain all your mana at the start of your turn");
        ActiveLabelPosition();
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
        activeLabel.setText("This is your hand, you can play cards from here by clicking on them");
        ActiveLabelPosition();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowDeck();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);

        //Highlight hand
        HighlightActor(gameView.getHostPlayerView().getHandView());
    }

    public void ShowDeck()
    {
        activeLabel.setText("This is your deck, you draw cards from here");
        ActiveLabelPosition();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowDiscard();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        //Highlight deck
        HighlightActor(gameView.getHostPlayerView().getDeckView());
    }

    public void ShowDiscard()
    {
        activeLabel.setText("This is your discard pile, cards you play go here then \n When your deck is empty the discard pile is shuffled back into the deck");
        ActiveLabelPosition();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                ShowEndTurn();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);
        //Highlight discard
        HighlightActor(gameView.getHostPlayerView().getDiscardView());
    }

    public void ShowEndTurn()
    {
        activeLabel.setText("This is the end turn button, click it when you are done with your turn");
        ActiveLabelPosition();
        activeLabel.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                activeLabel.remove();
                StartPlay();
                return super.touchDown(event, x, y, pointer, button);
            }
        } );
        stage.addActor(activeLabel);

        //Highlight end turn button
        HighlightActor(gameView.getHostPlayerView().getEndTurnButton());
    }

    public void StartPlay()
    {
        activeLabel.setText("You can play cards from your hand by clicking on them");
        ActiveLabelPosition();
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

    private void HighlightActor(Actor actor) {
        HighlightedArea.setX(actor.getX());
        HighlightedArea.setY(actor.getY());
        HighlightedArea.setWidth(actor.getWidth());
        HighlightedArea.setHeight(actor.getHeight());
    }

}
