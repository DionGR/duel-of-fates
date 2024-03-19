package no.ntnu.dof.model.gameplay.card;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.effect.Effect;

@Getter
@SuperBuilder
public abstract class Card extends GameplayEntity {

    @Singular private List<Effect> effects;

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void removeEffect(Effect effect) {
        effects.remove(effect);
    }

}
