package no.ntnu.dof.model.gameplay.player;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.gameclass.PlayerClass;
import no.ntnu.dof.model.stats.Stats;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Player extends GameplayEntity {

    private final PlayerClass playerClass;
    private final Stats liveStats;
    private final Hand hand;

    public void applyEffects(final List<Effect> effects) {
        for (Effect effect: effects) {
            effect.apply(this);
        }
    }

    public void applyCost(final Stats cost) {
        this.liveStats.subtract(cost);
    }

    public void refillHand() {
        this.hand.refill(this.playerClass.getDeck());
    }

    public boolean isDead() {
        return this.liveStats.getHealth() <= 0;
    }
}
