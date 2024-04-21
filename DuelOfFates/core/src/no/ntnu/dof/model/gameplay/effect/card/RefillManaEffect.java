package no.ntnu.dof.model.gameplay.effect.card;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.event.TurnListener;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.mana.ManaEffect;

@SuperBuilder(toBuilder = true)
public class RefillManaEffect extends Effect implements TurnListener {
    private final int MANA_TO_FILL = 3;

    @Override
    public void apply(@NonNull final Player player) {
        ManaEffect.builder().delta(-MANA_TO_FILL).build().apply(player);
    }

    @Override
    public RefillManaEffect copy() {
        return this.toBuilder().build();
    }

    @Override
    public boolean onTurn(@NonNull final Player player) {
        this.apply(player);
        return false;
    }
}
