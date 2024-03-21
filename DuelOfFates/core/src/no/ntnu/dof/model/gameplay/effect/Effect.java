package no.ntnu.dof.model.gameplay.effect;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public abstract class Effect extends GameplayEntity {

    private final int turnDuration;

    public void apply(Player player) {
        player.addEffect(this);
    }

}
