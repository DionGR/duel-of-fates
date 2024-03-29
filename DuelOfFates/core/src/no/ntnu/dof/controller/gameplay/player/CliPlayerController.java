package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;
import java.util.Scanner;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

public class CliPlayerController implements PlayerController {
    private final Player player;
    private final Scanner scanner = new Scanner(System.in);

    public CliPlayerController(Player player) {
        this.player = player;
    }

    @Override
    public Optional<Card> choosePlay() {
        Optional<Card> toPlay = Optional.empty();
        while (!toPlay.isPresent()) {
            System.out.println(player.getName() + "'s turn " + player);
            System.out.println("Choose a card from 0 to " + (player.getHand().getCards().size() - 1) + " (x to end turn):");
            for (int i = 0; i < player.getHand().getCards().size(); ++i) {
                Card card = player.getHand().getCards().get(i);
                System.out.println("\t" + i + ": " + card.getName() + " (" + card.getCost().getValue() + " mana)");
            }

            String input = scanner.next();
            if ("x".equals(input)) break;

            int cardIndex = Integer.parseInt(input);
            if (cardIndex < 0 || cardIndex >= player.getHand().getCards().size()) {
                System.out.println("Index out of range");
                continue;
            }

            Card card = player.getHand().getCards().get(cardIndex);
            toPlay = Optional.of(card);
        }
        return toPlay;
    }
}
