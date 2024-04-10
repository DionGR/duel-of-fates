package no.ntnu.dof.model.gameplay.player;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Hand;
import no.ntnu.dof.model.gameplay.event.CardPlayedEvent;
import no.ntnu.dof.model.gameplay.event.TurnEvent;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Getter
@SuperBuilder(toBuilder = true)
public class Player extends GameplayEntity {
    private final PlayerClass playerClass;

    private final Health health;
    private final Armor armor;
    private final Mana mana;
    private final Hand hand;

    public final TurnEvent beginTurnEvent = new TurnEvent(this);
    public final TurnEvent endTurnEvent = new TurnEvent(this);
    public final CardPlayedEvent cardPlayedEvent = new CardPlayedEvent(this);

    public void refillHand() {
        this.hand.refill(this.playerClass.getDeck());
    }

    public boolean isDead() {
        return this.health.getValue() <= 0;
    }

    @NonNull
    @Override
    public String toString() {
        return"(" + health.getValue() + " health, " + armor.getValue() + " armor, " + mana.getValue() + " mana)";
    }

    public boolean canPlay() {
        for (Card card : hand.getCards()) {
            if (mana.compareTo(card.getCost()) >= 0) {
                return true;
            }
        }

        return false;
    }

    public static abstract class PlayerBuilder<C extends Player, B extends PlayerBuilder<C, B>> extends GameplayEntityBuilder<C, B> {
        public B playerClass(PlayerClass playerClass) {
            this.playerClass = playerClass;
            this.health = new Health(playerClass.getMaxHealth().getValue());
            this.armor = new Armor(playerClass.getMaxArmor().getValue());
            this.mana = new Mana(playerClass.getMaxMana().getValue());
            this.hand = Hand.builder().maxSize(3).build();

            this.hand.refill(playerClass.getDeck());

            return self();
        }
    }
}
