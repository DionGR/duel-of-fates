package no.ntnu.dof.model.gameplay.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.card.Card;

@Getter
@SuperBuilder(toBuilder = true)
public class Deck extends GameplayEntity implements Iterator<Card> {
    @Builder.Default private final List<Card> activeCards = new ArrayList<>();
    private final List<Card> playedCards;

    public void shuffle() {
        Collections.shuffle(activeCards);
    }

    @Override
    public boolean hasNext() {
        return !activeCards.isEmpty();
    }

    @Override
    public Card next() throws IndexOutOfBoundsException {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("Deck is empty");
        }

        return activeCards.remove(0);
    }
}
