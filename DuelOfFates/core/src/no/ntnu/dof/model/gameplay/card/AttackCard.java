package no.ntnu.dof.model.gameplay.card;

import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
public class AttackCard extends Card{

    AttackCard copy() {
        return this.toBuilder().build();
    }
}
