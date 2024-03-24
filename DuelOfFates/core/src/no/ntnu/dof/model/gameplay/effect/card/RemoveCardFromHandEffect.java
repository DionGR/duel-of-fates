package no.ntnu.dof.model.gameplay.effect.card;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.event.CardPlayedListener;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class RemoveCardFromHandEffect implements CardPlayedListener {

    @Override
    public void onCardPlayed(@NonNull final Card card, @NonNull final Player player) {
        player.getHand().remove(card);
    }
}
