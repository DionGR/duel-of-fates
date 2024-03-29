package no.ntnu.dof.model.gameplay.stats.health;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class HealthEffect extends Effect {
    private final int delta;

    @Override
    public void apply(@NonNull final Player player) {
        player.getHealth().value -= delta;
    }
}
