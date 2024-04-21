package no.ntnu.dof.model.gameplay.effect;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.player.Player;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class Effect extends GameplayEntity {
    public abstract void apply(@NonNull final Player player);

    public abstract Effect copy();
}
