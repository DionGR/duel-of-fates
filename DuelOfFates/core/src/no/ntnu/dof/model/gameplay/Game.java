package no.ntnu.dof.model.gameplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.effect.card.DamageEffect;
import no.ntnu.dof.model.gameplay.effect.card.RefillHandEffect;
import no.ntnu.dof.model.gameplay.effect.card.RefillManaEffect;
import no.ntnu.dof.model.gameplay.effect.card.RemoveCardFromHandEffect;
import no.ntnu.dof.model.gameplay.player.exception.InsufficientResourcesException;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

public class Game {
    private final LinkedList<Player> players;

    public Game(Player player1, Player player2) {
        players = new LinkedList<>();
        players.add(player1);
        players.add(player2);

        players.forEach(p -> p.beginTurnEvent.register(RefillHandEffect.builder().build()));
        players.forEach(p -> p.beginTurnEvent.register(RefillManaEffect.builder().build()));
        players.forEach(p -> p.cardPlayedEvent.register(RemoveCardFromHandEffect.builder().build()));

        players.forEach(p -> p.beginTurnEvent.fire());
    }

    public boolean isOver() {
        return players.stream().anyMatch(Player::isDead);
    }

    public Player getNextPlayer() {
        return players.peek();
    }

    public void playCard(Card card) throws InsufficientResourcesException {
        final Player host = players.peek();
        final Player opponent = players.peekLast();

        if (host.getMana().compareTo(card.getCost()) < 0)
            throw new InsufficientResourcesException(host, card);

        host.cardPlayedEvent.fire(card);
        card.getHostEffects().forEach(e -> e.apply(host));
        card.getOpponentEffects().forEach(e -> e.apply(opponent));
    }

    public void finalizeTurn() {
        Player current = players.poll();
        Player next = players.peek();

        current.endTurnEvent.fire();
        next.beginTurnEvent.fire();

        players.add(current);
    }

    public static Player demoPlayer(String name) {
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            cards.add(AttackCard.builder()
                    .name("Card" + i)
                    .cost(new Mana(3))
                    .opponentEffect(DamageEffect.builder()
                            .damage(4)
                            .build()
                    ).build());
        }

        PlayerClass playerClass = PlayerClass.builder()
                .deck(Deck.builder().activeCards(cards).build())
                .maxHealth(new Health(10))
                .maxArmor(new Armor(0))
                .maxMana(new Mana(5))
                .build();

        return Player.builder()
                .name(name)
                .playerClass(playerClass)
                .hand(Hand.builder().maxSize(3).build())
                .build();
    }
}
