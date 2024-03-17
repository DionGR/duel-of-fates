package no.ntnu.dof.entity.gameplay.effect;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.entity.gameplay.GameplayEntity;
import no.ntnu.dof.entity.gameplay.player.Player;

@SuperBuilder
public abstract class Effect extends GameplayEntity {

    private final int turnDuration;

    public void apply(Player player) {
        player.addEffect(this);
    }

}
