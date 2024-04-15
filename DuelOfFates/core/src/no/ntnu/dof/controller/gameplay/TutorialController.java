package no.ntnu.dof.controller.gameplay;

import com.badlogic.gdx.Gdx;

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
    @Inject
    @Named("tutorialScreen")
    TutorialScreen screen;
    private Player host;
    private Player bot;


    public TutorialController() {
        ClickPlayerController hostController = new ClickPlayerController();
        HostPlayerView.provideClickListener(hostController);

        TutorialComponent tutorialComponent = DaggerTutorialComponent.create();
        tutorialComponent.inject(this);

        this.playerControllers = new HashMap<>();
        this.host = game.getHost();
        this.bot = game.getOpponent();
        this.playerControllers.put(this.host, hostController);
        this.playerControllers.put(this.bot, new BotTutorialController());
    }

    public void gameLoop() {
        int turn = 1;
        screen.GamePresentation();
        while (!game.isOver()) {
            System.out.println("Turn of " + game.getNextPlayer().getName() + " (" + game.getNextPlayer().getHealth().getValue() + " HP)");
            Player currentPlayer = game.getNextPlayer();
            PlayerController currentPlayerController = playerControllers.get(currentPlayer);

            Optional<Card> turnCard = currentPlayerController.choosePlay();

            if(turn == 2)
            {
                turnCard = Optional.empty();
            }

            if (turnCard.isPresent()) {
                game.playCard(turnCard.get());
            } else {
                turn++;
                TutorialTurn(turn);
                game.finalizeTurn();
                Gdx.app.log("Game", "Turn finalized.");
            }
        }
    }


    public void TutorialTurn(int turnNumber)
    {
        System.out.println("Turn " + turnNumber);
        screen.TutorialTurn(turnNumber);
    }

}
