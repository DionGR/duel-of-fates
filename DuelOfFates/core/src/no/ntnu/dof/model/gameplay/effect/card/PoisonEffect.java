package no.ntnu.dof.model.gameplay.effect.card;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.event.TurnListener;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.health.HealthEffect;

@SuperBuilder
public class PoisonEffect extends Effect implements TurnListener {

    private int damage;
    private int duration;

    @Override
    public void apply(@NonNull Player player) {
        player.beginTurnEvent.register(this);
        this.dealDamage(player);
    }

    @Override
    public void onTurn(@NonNull Player player) {
        if (duration == 0){
            player.beginTurnEvent.deregister(this);
        } else {
            duration--;
            this.dealDamage(player);
        }
    }

    private void dealDamage(Player player){
        HealthEffect effect = HealthEffect.builder().delta(damage).build();
        effect.apply(player);
    }
}
