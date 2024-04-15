package no.ntnu.dof.model.gameplay.event;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

public class CardPlayedEvent extends GameEvent<CardPlayedListener> {
    private final Player player;

    public CardPlayedEvent(Player player) {
        this.player = player;
    }

    public void fire(Card card) {
        listeners.removeIf(l -> l.onCardPlayed(card, player));
    }
}
