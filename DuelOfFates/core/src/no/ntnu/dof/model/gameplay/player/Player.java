package no.ntnu.dof.model.gameplay.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.effect.Effect;

@Getter
@SuperBuilder
public class Player extends GameplayEntity {

    // private final User user;
    private final Deck deck;


    public void addEffect(Effect effect) {
    }
}
