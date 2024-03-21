package no.ntnu.dof.model.gameplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.effect.DamageEffect;
import no.ntnu.dof.model.gameplay.gameclass.PlayerClass;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.stats.Stats;

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

        if (host.getLiveStats().compareTo(card.getCost()) > 0) {
            host.applyCost(card.getCost());

            host.getHand().remove(card);

            // TODO: APPLY effects
//            card.getHostEffects().forEach(effect -> effect.apply(host));
//            card.getOpponentEffects().forEach(effect -> effect.apply(opponent));
        }
    }

    public void finalizeTurn() {
        Player current = players.poll();
        Player next = players.peek();

        next.refillHand();
        // TODO: Refill Mana Effect?

        players.add(current);
    }

    public static Player demoPlayer(String name) {
        List<Card> cards = new ArrayList<>();

        Stats cost = Stats.builder()
                .health(0)
                .mana(3)
                .armor(0)
                .build();

        for (int i = 0; i < 10; ++i) {
            cards.add(AttackCard.builder()
                    .name("Card" + i)
                    .cost(cost)
                    .opponentEffect(DamageEffect.builder()
                            .damage(4)
                            .build()
                    ).build());
        }

        PlayerClass playerClass = PlayerClass.builder()
                .deck(Deck.builder().activeCards(cards).build())
                .maxStats(Stats.builder()
                        .health(10)
                        .mana(5)
                        .armor(0)
                        .build()
                ).build();

        Stats playerStats = Stats.builder()
                .health(10)
                .mana(5)
                .armor(0)
                .build();

        return Player.builder()
                .name(name)
                .playerClass(playerClass)
                .liveStats(playerStats)
                .hand(Hand.builder().maxSize(3).build())
                .build();
    }
}
