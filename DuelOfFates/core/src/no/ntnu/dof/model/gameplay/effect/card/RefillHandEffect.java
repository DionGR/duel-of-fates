package no.ntnu.dof.model.gameplay.effect.card;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.event.TurnListener;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class RefillHandEffect extends Effect implements TurnListener {
    @Override
    public void apply(@NonNull final Player player) {
        player.refillHand();
    }

    @Override
    public void onTurn(@NonNull final Player player) {
        this.apply(player);
    }
}
