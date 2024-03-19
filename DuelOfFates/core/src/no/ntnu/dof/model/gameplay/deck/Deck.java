package no.ntnu.dof.model.gameplay.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.card.Card;

@Getter
@SuperBuilder(toBuilder = true)
public class Deck extends GameplayEntity {
    @Builder.Default private final List<Card> cards = new ArrayList<>();

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        // TODO check if deck has run out of cards
        return cards.remove(0);
    }
}
