package no.ntnu.dof.model.gameplay.effect.card;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.stats.Effect;
import no.ntnu.dof.model.gameplay.event.TurnListener;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.effect.stats.ManaEffect;

@SuperBuilder
public class RefillManaEffect extends Effect implements TurnListener {
    @Override
    public void apply(@NonNull final Player player) {
        int manaToFill = player.getPlayerClass().getMaxMana() - player.getMana();
        if (manaToFill > 0) ManaEffect.builder().delta(-manaToFill).build().apply(player);
    }

    @Override
    public void onTurn(@NonNull final Player player) {
        this.apply(player);
    }
}
