package no.ntnu.dof.model.gameplay.event;

import lombok.NonNull;
import no.ntnu.dof.model.gameplay.player.Player;

public interface TurnListener extends GameEventListener {
    boolean onTurn(@NonNull final Player player);
}
