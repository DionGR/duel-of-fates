package no.ntnu.dof.model.di;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.card.AttackCard;
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

        return Deck.builder()
                .activeCards(cards)
                .build();
    }

    @Provides
    @Named("skeletonDeck")
    public Deck provideSkeletonDeck(@Named("doubleStrikeCard") Card doubleStrikeCard,
                                    @Named("damageEffect_5") Card damageCard) {
        List<Card> cards = Arrays.asList(doubleStrikeCard, damageCard, doubleStrikeCard, damageCard,
                doubleStrikeCard, damageCard, doubleStrikeCard, damageCard, doubleStrikeCard, damageCard);

        ArrayList<Card> cards = new ArrayList<>();

        cards.add(doubleStrikeCard);
        cards.add(damageCard);
        cards

        return Deck.builder()
                .activeCards(cards)
                .build();
    }

    @Provides
    @Named("mageDeck")
    public Deck provideMageDeck(@Named("freezeCard") Card freezeCard,
                                @Named("attackCard_Poison3") Card poisonCard,
                                @Named("manaEffect_4") Card manaRegenCard) {
        List<Card> cards = Arrays.asList(freezeCard, poisonCard, manaRegenCard, freezeCard, poisonCard,
                manaRegenCard, poisonCard, freezeCard, manaRegenCard, freezeCard);
        return Deck.builder()
                .activeCards(cards)
                .build();
    }

    @Provides
    @Named("knightDeck")
    public Deck provideKnightDeck(@Named("armorBoostCard_5") Card armorBoostCard,
                                  @Named("healthCard_5") Card healthCard,
                                  @Named("attackCard_2") Card attackCard,
                                  @Named("attackCard_5") Card bigAttackCard) {
        List<Card> cards = Arrays.asList(armorBoostCard, healthCard, attackCard, bigAttackCard,
                armorBoostCard, healthCard, attackCard, armorBoostCard, healthCard, bigAttackCard);
        return Deck.builder()
                .activeCards(cards)
                .build();
    }

    //TODO: Add more decks
}
