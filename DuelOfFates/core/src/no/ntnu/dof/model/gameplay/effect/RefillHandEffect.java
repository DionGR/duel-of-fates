package no.ntnu.dof.model.gameplay.effect;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.event.TurnListener;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class RefillHandEffect extends Effect implements TurnListener {
    @Override
    public void apply(Player player) {
        player.refillHand();
    }

    public void onTurn(Player player) {
        this.apply(player);
    }
}
