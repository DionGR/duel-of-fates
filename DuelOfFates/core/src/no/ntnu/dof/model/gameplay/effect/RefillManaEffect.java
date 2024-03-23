package no.ntnu.dof.model.gameplay.effect;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.event.TurnListener;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.mana.ManaEffect;

@SuperBuilder
public class RefillManaEffect extends Effect implements TurnListener {
    @Override
    public void apply(Player player) {
        int manaToFill = player.getPlayerClass().getMaxMana() - player.getMana();
        if (manaToFill > 0) ManaEffect.builder().delta(-manaToFill).build().apply(player);
    }

    @Override
    public void onTurn(Player player) {
        this.apply(player);
    }
}
