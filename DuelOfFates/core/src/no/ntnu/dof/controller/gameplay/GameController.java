package no.ntnu.dof.controller.gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.player.CliPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.controller.gameplay.player.TestClickPlayerController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.exception.InsufficientResourcesException;
import no.ntnu.dof.model.gameplay.player.Player;

public class GameController {
    @Getter
    private final Game game;
    private final Map<Player, PlayerController> playerControllers;

    public GameController(Player host, Player opponent) {
        this.game = new Game(host, opponent);
        this.playerControllers = new HashMap<>();
        this.playerControllers.put(host, TestClickPlayerController.get());
        this.playerControllers.put(opponent, new CliPlayerController(opponent));
    }

    public void gameLoop() {
        while (!game.isOver()) {
            PlayerController currentPlayerController = playerControllers.get(game.getNextPlayer());
            Optional<Card> turnCard = currentPlayerController.choosePlay();

            if (turnCard.isPresent()) {
                try {
                    game.playCard(turnCard.get());
                } catch (InsufficientResourcesException ime) {
                    // TODO handle error
                    System.out.println(ime);
                }
            } else {
                game.finalizeTurn();
            }
        }
    }
}
