package no.ntnu.dof.entity.gameplay.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import no.ntnu.dof.entity.gameplay.GameplayEntity;
import no.ntnu.dof.entity.gameplay.deck.Deck;
import no.ntnu.dof.entity.gameplay.effect.Effect;

@Getter
@SuperBuilder
public class Player extends GameplayEntity {

    // private final User user;
    private final Deck deck;


    public void addEffect(Effect effect) {
    }
}
