package no.ntnu.dof.model.gameplay.event;

import no.ntnu.dof.model.gameplay.player.Player;

public class TurnEvent extends GameEvent<TurnListener> {
    private final Player player;

    public TurnEvent(Player player) {
        this.player = player;
    }

    public void fire() {
        listeners.removeIf(l -> l.onTurn(player));
    }
}
