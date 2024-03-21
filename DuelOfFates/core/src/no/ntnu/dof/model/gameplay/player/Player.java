package no.ntnu.dof.model.gameplay.player;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.gameclass.GameClass;
import no.ntnu.dof.model.stats.Stats;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Player extends GameplayEntity {

    private final GameClass gameClass;
    private final Stats activeStats;
    private final Hand hand;
    private final List<Effect> activeEffects;

    public void applyEffects(final List<Effect> effects) {
        activeEffects.addAll(effects);

        for (Effect effect: effects) {
            effect.apply(this);
        }
    }

    public void refillHand() {
        this.hand.refill(this.gameClass.getDeck());
    }

    public boolean isDead() {
        return this.stats.getHealth() <= 0;
    }
}
