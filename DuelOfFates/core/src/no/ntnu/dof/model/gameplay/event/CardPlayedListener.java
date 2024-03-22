package no.ntnu.dof.model.gameplay.event;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

public interface CardPlayedListener extends GameEventListener {
    void onCardPlayed(Card card, Player player);
}
