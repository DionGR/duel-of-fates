package no.ntnu.dof.model.gameplay.effect;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.event.CardPlayedListener;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class RemoveCardFromHandEffect implements CardPlayedListener {
    @Override
    public void onCardPlayed(Card card, Player player) {
        player.getHand().remove(card);
    }
}
