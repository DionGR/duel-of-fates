package no.ntnu.dof.model.gameplay.stats.mana;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class ManaEffect extends Effect {
    private int delta;

    @Override
    public void apply(Player player) {
        player.getLiveStats().getMana().value -= delta;
    }
}
