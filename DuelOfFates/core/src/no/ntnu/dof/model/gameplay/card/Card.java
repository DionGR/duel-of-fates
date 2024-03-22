package no.ntnu.dof.model.gameplay.card;

import java.util.List;

import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.stats.Stats;

@Getter
@SuperBuilder
public abstract class Card extends GameplayEntity {
    @Singular private List<Effect> hostEffects;
    @Singular private List<Effect> opponentEffects;

    private final Stats cost;
}
