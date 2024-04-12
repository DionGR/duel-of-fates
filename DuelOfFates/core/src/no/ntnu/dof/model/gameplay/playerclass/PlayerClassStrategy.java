package no.ntnu.dof.model.gameplay.playerclass;

import lombok.NonNull;
import no.ntnu.dof.model.gameplay.player.Player;

public interface PlayerClassStrategy {
    void apply(@NonNull final Player player);

}
