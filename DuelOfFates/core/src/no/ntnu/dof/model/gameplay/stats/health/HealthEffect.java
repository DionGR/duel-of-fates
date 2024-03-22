package no.ntnu.dof.model.gameplay.stats.health;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class HealthEffect extends Effect {
    private final int delta;

    @Override
    public void apply(Player player) {
        player.getLiveStats().getHealth().value -= delta;
    }
}
