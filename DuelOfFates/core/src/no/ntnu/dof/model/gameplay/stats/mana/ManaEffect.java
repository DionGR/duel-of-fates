package no.ntnu.dof.model.gameplay.stats.mana;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class ManaEffect extends Effect {
    private final int delta;

    @Override
    public void apply(@NonNull final Player player) {
        int effectedMana = player.getMana().value - delta;
        int maxMana = player.getPlayerClass().getMaxMana().value;
        player.getMana().value = Integer.min(effectedMana, maxMana);
    }
}
