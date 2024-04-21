package no.ntnu.dof.model.gameplay.stats.armor;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder(toBuilder = true)
public class ArmorEffect extends Effect {
    private final int delta;

    @Override
    public void apply(@NonNull final Player player) {
        int effectedArmor = player.getArmor().value - delta;
        int maxArmor = player.getPlayerClass().getMaxArmor().value;
        player.getArmor().value = Integer.min(effectedArmor, maxArmor);
    }

    @Override
    public ArmorEffect copy() {
        return this.toBuilder()
                .delta(delta)
                .build();
    }
}
