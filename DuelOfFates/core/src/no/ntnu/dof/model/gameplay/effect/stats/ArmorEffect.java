package no.ntnu.dof.model.gameplay.effect.stats;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;

@SuperBuilder
public class ArmorEffect extends Effect {
    private final int delta;

    @Override
    public void apply(@NonNull final Player player) {
        player.setArmor(new Armor(player.getArmor() + delta));
    }
}
