package no.ntnu.dof.entity.gameplay.deck;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import no.ntnu.dof.entity.gameplay.GameplayEntity;
import no.ntnu.dof.entity.gameplay.card.Card;

@Getter
@SuperBuilder
public class Deck extends GameplayEntity {

    @Singular private final List<Card> cards;

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(0);
    }
}
