package no.ntnu.dof.model.gameplay.stats.armor;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class ArmorEffect extends Effect {
    private int delta;

    @Override
    public void apply(Player player) {
        player.getLiveStats().getArmor().value -= delta;
    }
}
