package no.ntnu.dof.model.gameplay.effect;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.stats.Stats;

@Getter
@SuperBuilder
public abstract class Effect extends GameplayEntity {

    private final Stats stats;

    public abstract void apply(final Player player);
}
