package no.ntnu.dof.model.gameplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import no.ntnu.dof.controller.gameplay.di.DaggerGameComponent;
import no.ntnu.dof.controller.gameplay.di.DaggerGameControllerComponent;
import no.ntnu.dof.controller.gameplay.di.GameComponent;
import no.ntnu.dof.controller.gameplay.di.GameControllerComponent;
import lombok.Getter;
import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.effect.EffectInvoker;
import no.ntnu.dof.model.gameplay.effect.card.RefillHandEffect;
import no.ntnu.dof.model.gameplay.effect.card.RefillManaEffect;
import no.ntnu.dof.model.gameplay.effect.card.RemoveCardFromHandEffect;
import no.ntnu.dof.model.gameplay.player.exception.InsufficientManaException;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Getter
public class Game {

    private final LinkedList<Player> players;

    @Inject
    @Named("effectInvoker")
    EffectInvoker<String, Effect> effectInvoker;

    public Game(Player player1, Player player2) {
        GameComponent gameComponent = DaggerGameComponent.create();
        gameComponent.inject(this);

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

    public void playCard(Card card) {
        final Player host = players.peek();
        final Player opponent = players.peekLast();

        host.cardPlayedEvent.fire(card);

        card.getHostEffectNames().forEach(e -> effectInvoker.invoke(e, host));
        card.getOpponentEffectNames().forEach(e -> effectInvoker.invoke(e, opponent));
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
                    .opponentEffectName("damage")
                    .build());
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
