package no.ntnu.dof.model.gameplay.effect;

import lombok.NonNull;
import no.ntnu.dof.model.gameplay.player.Player;

public interface EffectStrategy {
    void apply(@NonNull final Player player);

}
