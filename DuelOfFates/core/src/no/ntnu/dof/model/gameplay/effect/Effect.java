package no.ntnu.dof.model.gameplay.effect;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public abstract class Effect extends GameplayEntity {
    public abstract void apply(Player player);
}
