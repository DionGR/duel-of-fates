package no.ntnu.dof.controller.gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.controller.gameplay.player.ClickHostPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.controller.gameplay.player.RemotePlayerController;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

public class GameController {
    @Getter
    private final Game game;
    private final Map<Player, PlayerController> playerControllers;
    private final Player ourPlayer;

    public GameController(Player host, Player opponent, GameComms comms) {
        this.game = new Game(host, opponent);
        this.ourPlayer = host;
        this.playerControllers = new HashMap<>();

        ClickHostPlayerController hostController = ClickHostPlayerController.get();
        hostController.setPlayer(host, comms);
        this.playerControllers.put(host, hostController);
        this.playerControllers.put(opponent, new RemotePlayerController(opponent, comms));
    }

    public void gameLoop() {
        while (!game.isOver()) {
            System.out.println("Turn of " + game.getNextPlayer().getName() + " " + game.getNextPlayer());
            Player currentPlayer = game.getNextPlayer();
            PlayerController currentPlayerController = playerControllers.get(currentPlayer);

            Optional<Card> turnCard;

            if (currentPlayer.canPlay()) {
                turnCard = currentPlayerController.choosePlay();
            } else {
                turnCard = Optional.empty();
                System.out.println("Player cannot afford to play any card, finalizing turn.");
            }

            if (turnCard.isPresent()) {
                game.playCard(turnCard.get());
            } else {
                game.finalizeTurn();
                System.out.println("Turn finalized.");
            }
        }

        // TODO: Callbacks and so on?
        if (this.ourPlayer.isDead()) {
            System.out.println("You lost");
        } else {
            System.out.println("You won");
        }

        System.out.println("Game over");
        ScreenController.popScreen(); // TODO display game result
    }
}
