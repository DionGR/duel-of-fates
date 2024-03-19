package no.ntnu.dof.model.gameplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.effect.DamageEffect;
import no.ntnu.dof.model.gameplay.player.Player;

public class Game {
    private final LinkedList<Player> players;

    public Game(Player player1, Player player2) {
        players = new LinkedList<>();
        players.add(player1);
        players.add(player2);

        players.forEach(Player::refillHand);
    }

    public boolean isOver() {
        return players.stream().anyMatch(Player::isDead);
    }

    public Player getNextPlayer() {
        return players.peek();
    }

    public void playCard(Card card) {
        Player host = players.peek();
        Player opponent = players.peekLast();

        host.applyEffects(card.getHostEffects());
        opponent.applyEffects(card.getOpponentEffects());

        host.setMana(host.getMana() - card.getManaCost());
        host.getHand().getCards().remove(card);
    }

    public void finalizeTurn() {
        Player current = players.poll();
        Player next = players.peek();

        next.refillHand();
        next.refillMana();

        players.add(current);
    }

    public static Player demoPlayer(String name) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            cards.add(AttackCard.builder()
                    .name("Card" + i)
                    .manaCost(3)
                    .opponentEffect(DamageEffect.builder()
                            .damage(4)
                            .build()
                    ).build());
        }

        return Player.builder()
                .name(name)
                .health(10)
                .MAX_MANA(5)
                .mana(5)
                .defense(0)
                .deck(Deck.builder().cards(cards).build())
                .hand(Hand.builder().maxSize(5).build())
                .build();
    }
}
