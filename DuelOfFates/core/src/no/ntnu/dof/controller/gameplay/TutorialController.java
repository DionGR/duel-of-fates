package no.ntnu.dof.controller.gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.player.BotTutorialController;
import no.ntnu.dof.controller.gameplay.player.ClickHostPlayerController;
import no.ntnu.dof.controller.gameplay.player.ClickPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.screens.game.TutorialScreen;

public class TutorialController {
    @Getter
    private final Game game;
    private final TutorialScreen screen;
    private final Map<Player, PlayerController> playerControllers;

    public TutorialController(Game game, TutorialScreen screen) {
        this.game = game;
        this.playerControllers = new HashMap<>();
        this.screen = screen;

        Player host = game.getPlayers().get(0);
        Player bot = game.getPlayers().get(1);

        ClickPlayerController hostController = new ClickPlayerController();
        this.playerControllers.put(host, hostController);
        this.playerControllers.put(bot, new BotTutorialController());
    }

    public void gameLoop() {
        int turn = 1;
        screen.GamePresentation();
        while (!game.isOver()) {
            System.out.println("Turn of " + game.getNextPlayer().getName() + " (" + game.getNextPlayer().getHealth().getValue() + " HP)");
            Player currentPlayer = game.getNextPlayer();
            PlayerController currentPlayerController = playerControllers.get(currentPlayer);

            Optional<Card> turnCard;


            if (currentPlayer.canPlay()) {
                turnCard = currentPlayerController.choosePlay();
            } else {
                turnCard = Optional.empty();
                System.out.println("Player cannot afford to play any card, finalizing turn.");
            }

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
                System.out.println("Turn finalized.");
            }
        }
    }


    public void TutorialTurn(int turnNumber)
    {
        System.out.println("Turn " + turnNumber);
        screen.TutorialTurn(turnNumber);
    }

}
