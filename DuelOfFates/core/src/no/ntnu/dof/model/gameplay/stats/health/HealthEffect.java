package no.ntnu.dof.model.gameplay.stats.health;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder(toBuilder = true)
public class HealthEffect extends Effect {
    private final int delta;

    @Override
    public void apply(@NonNull final Player player) {
        int effectedHealth = player.getHealth().value - delta;
        int maxHealth = player.getPlayerClass().getMaxHealth().value;
        player.getHealth().value = Integer.min(effectedHealth, maxHealth);
    }

    @Override
    public HealthEffect copy() {
        return this.toBuilder()
                .delta(delta)
                .build();
    }
}
