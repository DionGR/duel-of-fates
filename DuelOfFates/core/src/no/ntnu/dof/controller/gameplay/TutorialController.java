package no.ntnu.dof.controller.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.di.DaggerTutorialComponent;
import no.ntnu.dof.controller.gameplay.di.TutorialComponent;
import no.ntnu.dof.controller.gameplay.player.BotTutorialController;
import no.ntnu.dof.controller.gameplay.player.ClickPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.entity.view.HostPlayerView;
import no.ntnu.dof.view.screens.game.TutorialScreen;

@Getter
public class TutorialController {
    private final Map<Player, PlayerController> playerControllers;

    @Inject
    @Named("tutorialGame")
    Game game;
    private final TutorialScreen screen;
    private final Player host;
    private final Player bot;
    private boolean quit;

    public TutorialController() {
        TutorialComponent tutorialComponent = DaggerTutorialComponent.create();
        tutorialComponent.inject(this);

        ClickPlayerController hostController = new ClickPlayerController();
        HostPlayerView.provideClickListener(hostController);

        this.screen = new TutorialScreen(this.game);
        this.quit = false;

        this.playerControllers = new HashMap<>();
        this.host = game.getHost();
        this.bot = game.getOpponent();
        this.playerControllers.put(this.host, hostController);
        this.playerControllers.put(this.bot, new BotTutorialController());
    }

    public void startGame() {
        Thread gameThread = new Thread(() -> {
            try {
                gameLoop();
            } catch (InterruptedException e) {
                Gdx.app.log("Game", "Game over: host aborted");
            }
        });
        Gdx.app.addLifecycleListener(new LifecycleListener() {
            @Override
            public void pause() {}

            @Override
            public void resume() {}

            @Override
            public void dispose() {
                Gdx.app.log("Application", "Application quit.");
                quit = true;
                gameThread.interrupt();
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                    Gdx.app.error("Game", "Game ended ungracefully.");
                }
            }
        });
        gameThread.start();
    }

    public void gameLoop() throws InterruptedException {
        screen.GamePresentation();

        Optional<Card> turnCard = playerControllers.get(host).choosePlay(0);
        playOneCard(turnCard);
        screen.ShowManaConsumption();

        boolean playedCardTutorialShowed = false;

        while (!game.isOver()) {
            System.out.println("Turn of " + game.getNextPlayer().getName() + " (" + game.getNextPlayer().getHealth().getValue() + " HP)");
            Player currentPlayer = game.getNextPlayer();
            PlayerController currentPlayerController = playerControllers.get(currentPlayer);

            if (quit) throw new InterruptedException();
            turnCard = currentPlayerController.choosePlay(0);

            if (turnCard.isPresent()) {
                game.playCard(turnCard.get());
            } else {
                game.finalizeTurn();
                if(!playedCardTutorialShowed && currentPlayer == bot){
                    screen.ShowPlayedCard();
                    playedCardTutorialShowed = true;
                }
                Gdx.app.log("Game", "Turn finalized.");
            }
        }
    }

    public void playOneCard(Optional<Card> turnCard){
        if (turnCard.isPresent()) {
            game.playCard(turnCard.get());
        } else {
            game.finalizeTurn();
            Gdx.app.log("Game", "Turn finalized.");
        }
    }

}
