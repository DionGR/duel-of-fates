package no.ntnu.dof.model.stats;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;

@Getter
@SuperBuilder
public class Stats extends GameplayEntity {

    private final int health;
    private final int armor;
    private final int mana;
}
