package no.ntnu.dof.model.gameplay.player;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;
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
    public final TurnEvent beginTurnEvent = new TurnEvent(this);
    public final TurnEvent endTurnEvent = new TurnEvent(this);
    public final CardPlayedEvent cardPlayedEvent = new CardPlayedEvent(this);
    private final PlayerClass playerClass;
    private final Health health;
    private final Armor armor;
    private final Mana mana;
    private final Hand hand;
    private final Deck deck;

    public void refillHand() {
        this.hand.refill(this.deck);
    }

    public boolean isDead() {
        return this.health.getValue() <= 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + health.getValue() + " health, " + armor.getValue() + " armor, " + mana.getValue() + " mana)";
    }

    public boolean canPlay(Card card) {
        return mana.compareTo(card.getCost()) >= 0;
    }

    public Card getLastPlayedCard() {
        return this.deck.getLastPlayedCard();
    }

    public static abstract class PlayerBuilder<C extends Player, B extends PlayerBuilder<C, B>> extends GameplayEntityBuilder<C, B> {
        public B playerClass(PlayerClass playerClass) {
            this.playerClass = playerClass;
            this.health = new Health(playerClass.getMaxHealth().getValue());
            this.armor = new Armor(playerClass.getMaxArmor().getValue());
            this.mana = new Mana(playerClass.getMaxMana().getValue());
            this.hand = Hand.builder().maxSize(3).build();
            this.deck = Deck.builder()
                    .name(playerClass.getDeck().getName())
                    .activeCards(new ArrayList<>(playerClass.getDeck().getActiveCards()))
                    .build();

            this.deck.shuffle();

            this.hand.refill(this.deck);

            return self();
        }
    }
}
