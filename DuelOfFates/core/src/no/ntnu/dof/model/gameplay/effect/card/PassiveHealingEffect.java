package no.ntnu.dof.model.gameplay.effect.card;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.event.TurnListener;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.health.HealthEffect;

@SuperBuilder
public class PassiveHealingEffect extends Effect implements TurnListener {

    private int health;
    private int duration;

    @Override
    public void apply(@NonNull Player player) {
        player.beginTurnEvent.register(this);
        this.heal(player);
    }

    @Override
    public void onTurn(@NonNull Player player) {
        if (duration == 0){
            player.beginTurnEvent.deregister(this);
        } else {
            duration--;
            this.heal(player);
        }
    }

    private void heal(Player player){
        HealthEffect effect = HealthEffect.builder().delta(-health).build();
        effect.apply(player);
    }
}