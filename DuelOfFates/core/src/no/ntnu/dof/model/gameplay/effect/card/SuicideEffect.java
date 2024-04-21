package no.ntnu.dof.model.gameplay.effect.card;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.health.HealthEffect;

@SuperBuilder(toBuilder = true)
public class SuicideEffect extends Effect {
    @Override
    public void apply(@NonNull Player player) {
        int currentHealth = player.getHealth().getValue();
        Effect death = HealthEffect.builder().delta(currentHealth).build();
        death.apply(player);
    }

    @Override
    public SuicideEffect copy() {
        return this.toBuilder().build();
    }
}
