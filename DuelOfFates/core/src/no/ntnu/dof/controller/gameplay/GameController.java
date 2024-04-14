package no.ntnu.dof.controller.gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.controller.gameplay.player.HostPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.controller.gameplay.player.RemotePlayerController;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.entity.view.HostPlayerView;

public class GameController {
    @Getter
    private final Game game;
    private final Map<Player, PlayerController> playerControllers;

    public GameController(Player host, Player opponent, GameComms comms) {
        this.game = new Game(host, opponent);
        if (game.getNextPlayer().getName().equals(comms.getPlayerLastTurn())) {
            game.finalizeTurn();
        }

        this.playerControllers = new HashMap<>();
        HostPlayerController hostController = new HostPlayerController(host, comms);
        this.playerControllers.put(host, hostController);
        HostPlayerView.provideClickListener(hostController);
        this.playerControllers.put(opponent, new RemotePlayerController(opponent, comms));
    }

    public void gameLoop() {
        while (!game.isOver()) {
            System.out.println("Turn of " + game.getNextPlayer().getName() + " " + game.getNextPlayer());
            Player currentPlayer = game.getNextPlayer();
            PlayerController currentPlayerController = playerControllers.get(currentPlayer);

            Optional<Card> turnCard = currentPlayerController.choosePlay();
            if (turnCard.isPresent()) {
                game.playCard(turnCard.get());
            } else {
                game.finalizeTurn();
                System.out.println("Turn finalized.");
            }
        }

        System.out.println("Game over");
        ScreenController.popScreen(); // TODO display game result
    }
}
