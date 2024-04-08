package no.ntnu.dof.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import no.ntnu.dof.controller.gameplay.GameController;
import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameLobbies;
import no.ntnu.dof.model.User;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;
import no.ntnu.dof.view.screens.FightScreen;
import no.ntnu.dof.view.screens.LoginScreen;

@Data
public class DuelOfFates extends com.badlogic.gdx.Game {
    private SpriteBatch batch;
    private AssetManager assetManager;
    private User currentUser;
    private GameLobbies gameLobbies;
    private List<PlayerClass> playerClasses;

	public DuelOfFates() {}

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("menuBackground.png", Texture.class);
        assetManager.load("backBtn.png", Texture.class);
        assetManager.finishLoading(); // Blocks until all assets are loaded

        // Initialize first screen and ScreenManager
        ScreenManager.initialize(this, batch, assetManager);
        ScreenManager.transitionToLogin();


        // Fetch game lobbies
        this.gameLobbies = new GameLobbies();
        // Set MockPlayerClasses:
        setMockPlayerClasses();
        // TODO remove CLI gameplay demo
        /*
        ServiceLocator.getAuthService().signIn("p1", "p1", new AuthCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String message) {

            }
        });

        GameController gameController = new GameController(Game.demoPlayer("p1"), Game.demoPlayer("p2"));

        // Initialize the fight screen as the first screen
        this.setScreen(new FightScreen(gameController.getGame()));
        new Thread(gameController::gameLoop).start();
        */
	}

    // TODO: Replace setMockPlayerClasses with actual set of PlayerClasses
    private void setMockPlayerClasses() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            cards.add(AttackCard.builder()
                    .name("Card" + i)
                    .cost(new Mana(3))
                    .opponentEffectName("damage")
                    .build());
        }

        PlayerClass warrior = PlayerClass.builder()
                .name("warrior")
                .deck(Deck.builder().activeCards(cards).build())
                .maxHealth(new Health(10))
                .maxArmor(new Armor(0))
                .maxMana(new Mana(5))
                .build();

        PlayerClass mage = PlayerClass.builder()
                .name("mage")
                .deck(Deck.builder().activeCards(cards).build())
                .maxHealth(new Health(10))
                .maxArmor(new Armor(0))
                .maxMana(new Mana(5))
                .build();

        PlayerClass rogue = PlayerClass.builder()
                .name("rogue")
                .deck(Deck.builder().activeCards(cards).build())
                .maxHealth(new Health(10))
                .maxArmor(new Armor(0))
                .maxMana(new Mana(5))
                .build();

        this.playerClasses = Arrays.asList(warrior, mage, rogue);
    }

    @Override
    public void render() {
        super.render(); // Important to call this to ensure the screen is rendered
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
        super.dispose(); // Dispose the screen and other resources
    }
}
