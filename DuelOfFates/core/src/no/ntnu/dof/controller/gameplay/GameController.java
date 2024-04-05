package no.ntnu.dof.controller.gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import no.ntnu.dof.controller.gameplay.di.DaggerGameControllerComponent;
import no.ntnu.dof.controller.gameplay.di.GameControllerComponent;
import no.ntnu.dof.controller.gameplay.player.CliPlayerController;
import no.ntnu.dof.controller.gameplay.player.HostPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.controller.gameplay.player.RemotePlayerController;
import lombok.Getter;
import no.ntnu.dof.controller.gameplay.player.CliPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.controller.gameplay.player.TestClickPlayerController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.player.exception.InsufficientManaException;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;

public class GameController {
    @Getter
    private final Game game;
    private final Map<Player, PlayerController> playerControllers;

    public GameController(Player host, Player opponent) {
        this.game = new Game(host, opponent);
        this.playerControllers = new HashMap<>();

        this.playerControllers.put(host, new HostPlayerController(host));
        this.playerControllers.put(opponent, new RemotePlayerController(opponent));
    }

    public void gameLoop() {
        while (!game.isOver()) {
            System.out.println("Turn of " + game.getNextPlayer().getName() + " (" + game.getNextPlayer().getHealth() + " HP)");
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
    }
}
