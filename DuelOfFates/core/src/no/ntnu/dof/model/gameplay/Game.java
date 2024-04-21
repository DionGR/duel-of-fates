package no.ntnu.dof.model.gameplay;

import java.util.LinkedList;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.di.DaggerGameComponent;
import no.ntnu.dof.controller.gameplay.di.GameComponent;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.effect.EffectInvoker;
import no.ntnu.dof.model.gameplay.effect.card.RefillHandEffect;
import no.ntnu.dof.model.gameplay.effect.card.RefillManaEffect;
import no.ntnu.dof.model.gameplay.effect.card.RemoveCardFromHandEffect;
import no.ntnu.dof.model.gameplay.player.Player;

public class Game {
    private final LinkedList<Player> players;
    @Getter
    private final Player host;
    @Getter
    private final Player opponent;

    @Inject
    @Named("effectInvoker")
    EffectInvoker<String, Effect> effectInvoker;

    public Game(Player host, Player opponent) {
        GameComponent gameComponent = DaggerGameComponent.create();
        gameComponent.inject(this);

        this.host = host;
        this.opponent = opponent;
        players = new LinkedList<>();
        players.add(host);
        players.add(opponent);

        players.forEach(p -> p.beginTurnEvent.register(RefillHandEffect.builder().build()));
        players.forEach(p -> p.beginTurnEvent.register(RefillManaEffect.builder().build()));
        players.forEach(p -> p.cardPlayedEvent.register(RemoveCardFromHandEffect.builder().build()));

        players.forEach(p -> p.beginTurnEvent.fire());
    }

    public boolean isOver() {
        return host.isDead() || opponent.isDead();
    }

    public Player getNextPlayer() {
        return players.peek();
    }

    public void playCard(Card card) {
        final Player host = players.peek();
        final Player opponent = players.peekLast();

        host.cardPlayedEvent.fire(card);

        card.getHostEffectNames().forEach(e -> effectInvoker.invoke(e).apply(host));
        card.getOpponentEffectNames().forEach(e -> effectInvoker.invoke(e).apply(opponent));
    }

    public void finalizeTurn() {
        Player current = players.poll();
        Player next = players.peek();

        current.endTurnEvent.fire();
        next.beginTurnEvent.fire();

        players.add(current);
    }
}
