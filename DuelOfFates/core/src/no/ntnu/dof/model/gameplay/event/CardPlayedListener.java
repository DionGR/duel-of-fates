package no.ntnu.dof.model.gameplay.event;

import lombok.NonNull;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

public interface CardPlayedListener extends GameEventListener {
    void onCardPlayed(@NonNull final Card card, @NonNull final Player player);
}
