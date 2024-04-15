package no.ntnu.dof.model.gameplay.effect.card;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.event.TurnListener;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.mana.ManaEffect;

@SuperBuilder
public class RefillManaEffect extends Effect implements TurnListener {

    @Override
    public void apply(@NonNull final Player player) {
        int manaToFill = player.getPlayerClass().getMaxMana().getValue() - player.getMana().getValue();
        if (manaToFill > 0) ManaEffect.builder().delta(-manaToFill).build().apply(player);
    }

    @Override
    public boolean onTurn(@NonNull final Player player) {
        this.apply(player);
        return false; // don't deregister from event
    }
}
