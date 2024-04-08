package no.ntnu.dof.controller.gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.player.BotTutorialController;
import no.ntnu.dof.controller.gameplay.player.ClickHostPlayerController;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.controller.gameplay.player.RemotePlayerController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

public class TutorialController {
    @Getter
    private final Game game;
    private final Map<Player, PlayerController> playerControllers;

    public TutorialController(Player host) {
        Player bot = Game.demoPlayer("bot");

        this.game = new Game(host, bot);
        this.playerControllers = new HashMap<>();

        ClickHostPlayerController hostController = ClickHostPlayerController.get();
        hostController.setPlayer(host);
        this.playerControllers.put(host, hostController);
        this.playerControllers.put(bot, new BotTutorialController());
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
