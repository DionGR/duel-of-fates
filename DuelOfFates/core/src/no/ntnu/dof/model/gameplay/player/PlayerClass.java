package no.ntnu.dof.model.gameplay.player;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.stats.Stats;

@Getter
@SuperBuilder
public class PlayerClass extends GameplayEntity {
    private final Deck deck;
    private final Stats maxStats;

    public int getMaxHealth() {
        return this.maxStats.getHealth().getValue();
    }

    public int getMaxArmor() {
        return this.maxStats.getArmor().getValue();
    }

    public int getMaxMana() {
        return this.maxStats.getMana().getValue();
    }
}
