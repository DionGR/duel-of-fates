package no.ntnu.dof.model.gameplay.deck;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.card.Card;

@Getter
@SuperBuilder(toBuilder = true)
public class Hand extends GameplayEntity {
    @Setter
    private int maxSize;
    @Builder.Default private List<Card> cards = new ArrayList<>();

    public void refill(final Deck deck) {
        while (cards.size() < maxSize) {
            cards.add(deck.next());
        }
    }

    public void remove(final Card card) {
        cards.remove(card);
    }
}
