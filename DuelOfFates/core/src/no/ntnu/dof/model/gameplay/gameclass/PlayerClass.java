package no.ntnu.dof.model.gameplay.gameclass;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.stats.Stats;

@Getter
@SuperBuilder
public class PlayerClass extends GameplayEntity {

    private final Deck deck;
    private final Stats maxStats;

}
