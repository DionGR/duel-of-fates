package no.ntnu.dof.model.gameplay.card;

import java.util.List;

import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Getter
@SuperBuilder(toBuilder = true)
public class Card extends GameplayEntity {
    @Singular private List<String> hostEffectNames;
    @Singular private List<String> opponentEffectNames;

    private Mana cost;

    public Card() {}

    public void flipEffects() {
        List<String> tmp = hostEffectNames;
        hostEffectNames = opponentEffectNames;
        opponentEffectNames = tmp;
    }
}
