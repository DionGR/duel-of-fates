package no.ntnu.dof.model.gameplay.event;

import no.ntnu.dof.model.gameplay.player.Player;

public interface TurnListener extends GameEventListener {
    void onTurn(Player player);
}
