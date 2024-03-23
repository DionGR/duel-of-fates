package no.ntnu.dof.model.gameplay.player;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.event.CardPlayedEvent;
import no.ntnu.dof.model.gameplay.event.TurnEvent;
import no.ntnu.dof.model.gameplay.stats.Stats;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Player extends GameplayEntity {

    private final PlayerClass playerClass;
    private final Stats liveStats;
    private final Hand hand;
    public final TurnEvent beginTurnEvent = new TurnEvent(this);
    public final TurnEvent endTurnEvent = new TurnEvent(this);
    public final CardPlayedEvent cardPlayedEvent = new CardPlayedEvent(this);

    public int getHealth() {
        return this.liveStats.getHealth().getValue();
    }

    public int getMana() {
        return this.liveStats.getMana().getValue();
    }

    public int getArmor() {
        return this.liveStats.getArmor().getValue();
    }

    public void refillHand() {
        this.hand.refill(this.playerClass.getDeck());
    }

    public boolean isDead() {
        return this.getHealth() <= 0;
    }
}
