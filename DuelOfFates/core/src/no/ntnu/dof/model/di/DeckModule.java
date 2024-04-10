package no.ntnu.dof.model.di;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;

@Module(includes = {CardModule.class})
public class DeckModule {

    @Provides
    @Named ("exampleDeck")
    public Deck provideExampleDeck(@Named ("exampleCard") Card attackCard) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(attackCard);
        cards.add(attackCard);
        cards.add(attackCard);
        cards.add(attackCard);
        cards.add(attackCard);
        cards.add(attackCard);

        return Deck.builder()
                .activeCards(cards)
                .build();
    }

    //TODO: Add more decks
}
