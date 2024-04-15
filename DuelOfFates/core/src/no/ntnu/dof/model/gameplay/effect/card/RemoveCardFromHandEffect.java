package no.ntnu.dof.model.gameplay.effect.card;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.event.CardPlayedListener;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.mana.ManaEffect;

@SuperBuilder
public class RemoveCardFromHandEffect implements CardPlayedListener {
    @Override
    public boolean onCardPlayed(@NonNull final Card card, @NonNull final Player player) {
        ManaEffect manaEffect = ManaEffect.builder().delta(card.getCost().getValue()).build();
        manaEffect.apply(player);
        player.getHand().remove(card);
        player.getDeck().getPlayedCards().add(card);
        return false; // don't deregister from event
    }
}
