package no.ntnu.dof.model.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.deck.Deck;

@Module(includes = {CardModule.class})
public class DeckModule {

    /**
     * Creates a knightDeck focusing on high defense and strong attacks using armor and health boosts.
     */
    @Provides
    @Named("knightDeck")
    public Deck provideKnightDeck(
            @Named("attackCard_12") Card highDamageCard,
            @Named("healthCard_12") Card largeHealCard,
            @Named("passiveHealingCard_3_4") Card passiveHealingCard,
            @Named("attackCard_2") Card smallDamageCard,
            @Named("healthCard_2") Card healCard,
            @Named("surpriseAttackCard_2") Card surpriseAttackCard
    ) {
        List<Card> cards = new ArrayList<>();
        cards.add(highDamageCard);
        cards.add(largeHealCard);
        cards.add(passiveHealingCard);
        cards.add(smallDamageCard);
        cards.add(healCard);
        cards.add(smallDamageCard);
        cards.add(healCard);
        cards.add(surpriseAttackCard);
        cards.addAll(cards);  // Adding the same set of cards to make it 16 cards

        return Deck.builder()
                .activeCards(cards)
                .build();
    }

    /**
     * Creates a mageDeck with focus on mana manipulation and powerful effect-based attacks.
     */
    @Provides
    @Named("mageDeck")
    public Deck provideMageDeck(
            @Named("manaCard_5") Card manaCard4,
            @Named("poisonCard_4_3") Card poisonCard,
            @Named("passiveHealingCard_3_4") Card passiveHealingCard,
            @Named("refillManaCard") Card refillManaCard,
            @Named("refillHandCard") Card refillHandCard,
            @Named("attackCard_5") Card moderateDamageCard,
            @Named("manaCard_2") Card manaCard2
    ) {
        List<Card> cards = new ArrayList<>();
        cards.add(manaCard4);
        cards.add(poisonCard);
        cards.add(passiveHealingCard);
        cards.add(refillManaCard);
        cards.add(refillHandCard);
        cards.add(moderateDamageCard);
        cards.add(moderateDamageCard);
        cards.add(manaCard2);
        cards.addAll(cards);

        return Deck.builder()
                .activeCards(cards)
                .build();
    }

    /**
     * Creates a skeletonDeck emphasizing durability and persistent damage, leveraging its high health.
     */
    @Provides
    @Named("skeletonDeck")
    public Deck provideSkeletonDeck(
            @Named("poisonCard_4_3") Card heavyPoisonCard,
            @Named("healthCard_5") Card skeletonHealingCard,
            @Named("multiPoisonCard_4_3") Card multiPoisonCard,
            @Named("extendedHealingCard_3_4") Card extendedHealingCard,
            @Named("attackCard_12") Card devastatingAttackCard,
            @Named("healthCard_2") Card minorHealCard,
            @Named("refillManaCard") Card manaRefillCard
    ) {
        List<Card> cards = new ArrayList<>();
        cards.add(heavyPoisonCard);
        cards.add(skeletonHealingCard);
        cards.add(multiPoisonCard);
        cards.add(extendedHealingCard);
        cards.add(devastatingAttackCard);
        cards.add(minorHealCard);
        cards.add(minorHealCard);
        cards.add(manaRefillCard);
        cards.addAll(cards);

        return Deck.builder()
                .activeCards(cards)
                .build();
    }
}