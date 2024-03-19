package no.ntnu.dof.model.gameplay.player;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.effect.Effect;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Player extends GameplayEntity {

    // private final User user;
    private int MAX_MANA;
    private int mana;
    private int health;
    private int defense;
    private final Deck deck;
    private final Hand hand;

    public void applyEffects(final List<Effect> effects) {
        effects.forEach(effect -> effect.apply(this));
    }

    public void refillHand() {
        this.hand.refill(this.deck);
    }

    public void refillMana() {
        this.mana = this.MAX_MANA;
    }

    public boolean isDead() {
        return health <= 0;
    }
}
