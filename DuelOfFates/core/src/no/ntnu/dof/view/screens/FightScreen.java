package no.ntnu.dof.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.player.PlayerClass;
import no.ntnu.dof.model.gameplay.stats.Stats;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.view.Card_View;


public class FightScreen implements Screen {
    private Stage stage;
    private DuelOfFates game;

    private Player[] Players;


    public FightScreen(DuelOfFates game) {
        this.game = game;
    }

    @Override
    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Hand playerhand = Hand.builder().name("Hand1").maxSize(10).build();
        Card createcard = AttackCard.builder().name("Card1").cost(Stats.builder().mana(2).health(0).armor(0).build()).build();
        playerhand.getCards().add(createcard);
        Stats playerstats = Stats.builder().name("Stat1").health(30).mana(0).armor(0).build();
        Deck playerdeck = Deck.builder().name("Deck1").build();
        PlayerClass PC = PlayerClass.builder().deck(playerdeck).maxStats(playerstats).build();
        Players = new Player[2];
        Players[0] = Player.builder().name("Player 1").playerClass(PC).liveStats(playerstats).hand(playerhand).build();
        Players[1] = Player.builder().name("Player 2").playerClass(PC).liveStats(playerstats).hand(playerhand).build();

        Group Hand = new Group();
        for (int i = 0; i < Players[0].getHand().getCards().size(); i++) {
            Card_View card = new Card_View(0.2f, Players[0].getHand().getCards().get(i));
            card.setPosition(100 + i * 200, 100);
            Hand.addActor(card);
        }
        stage.addActor(Hand);

        Hand.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("down");
                return true;
            }
        } );

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
