package no.ntnu.dof.controller.gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import no.ntnu.dof.controller.gameplay.di.DaggerGameControllerComponent;
import no.ntnu.dof.controller.gameplay.di.GameControllerComponent;
import no.ntnu.dof.controller.gameplay.player.HostPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.controller.gameplay.player.RemotePlayerController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.player.exception.InsufficientResourcesException;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;

public class GameController {
    private final Game game;
    private final Map<Player, PlayerController> playerControllers;

    @Inject
    PlayerClass playerClass;

    public GameController(Player host, Player opponent) {
        GameControllerComponent playerClassComponent = DaggerGameControllerComponent.create();
        playerClassComponent.inject(this);

        this.game = new Game(host, opponent);
        this.playerControllers = new HashMap<>();

        this.playerControllers.put(host, new HostPlayerController(host));
        this.playerControllers.put(opponent, new RemotePlayerController(opponent));
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
