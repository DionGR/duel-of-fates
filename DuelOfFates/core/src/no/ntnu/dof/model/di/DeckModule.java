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
            @Named("passiveHealingCard_4_4") Card passiveHealingCard,
            @Named("attackCard_4") Card smallDamageCard,
            @Named("attackCard_8") Card moderateDamageCard,
            @Named("healthCard_4") Card healCard,
            @Named("healthCard_8") Card moderateHealCard,
            @Named("surpriseAttackCard_4") Card surpriseAttackCard
    ) {
        List<Card> cards = new ArrayList<>();
        cards.add(smallDamageCard);
        cards.add(moderateDamageCard);
        cards.add(moderateDamageCard);
        cards.add(highDamageCard);
        cards.add(surpriseAttackCard);

        cards.add(healCard);
        cards.add(moderateHealCard);
        cards.add(passiveHealingCard);

        cards.addAll(cards);

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
            @Named("manaCard_5") Card highManaCard,
            @Named("manaCard_2") Card lowManaCard,
            @Named("poisonCard_4_4") Card poisonCard,
            @Named("passiveHealingCard_4_4") Card passiveHealingCard,
            @Named("attackCard_8") Card moderateDamageCard
    ) {
        List<Card> cards = new ArrayList<>();

        cards.add(highManaCard);
        cards.add(highManaCard);
        cards.add(lowManaCard);

        cards.add(passiveHealingCard);

        cards.add(poisonCard);
        cards.add(poisonCard);
        cards.add(moderateDamageCard);
        cards.add(moderateDamageCard);

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
            @Named("poisonCard_4_4") Card heavyPoisonCard,
            @Named("healthCard_4") Card skeletonHealingCard,
            @Named("multiPoisonCard_4_4") Card multiPoisonCard,
            @Named("extendedHealingCard_4_4") Card extendedHealingCard,
            @Named("attackCard_12") Card devastatingAttackCard,
            @Named("healthCard_4") Card minorHealCard,
            @Named("attackCard_12") Card highDamageCard
    ) {
        List<Card> cards = new ArrayList<>();

        cards.add(minorHealCard);
        cards.add(skeletonHealingCard);
        cards.add(extendedHealingCard);

        cards.add(heavyPoisonCard);
        cards.add(multiPoisonCard);
        cards.add(multiPoisonCard);

        cards.add(devastatingAttackCard);
        cards.add(highDamageCard);

        cards.addAll(cards);

        return Deck.builder()
                .activeCards(cards)
                .build();
    }

    /**
     * Creates a deck for the tutorial
     */
    @Provides
    @Named("tutorialDeck")
    public Deck provideTutorialDeck(
            @Named("attackCard_4") Card minorDamageCard,
            @Named("healthCard_4") Card minorHealCard,
            @Named("healthCard_8") Card mediumHealCard,
            @Named("attackCard_8") Card mediumDamageCard,
            @Named("manaCard_2") Card manaCard
    ) {
        List<Card> cards = new ArrayList<>();
        cards.add(minorDamageCard);
        cards.add(minorHealCard);
        cards.add(mediumHealCard);
        cards.add(mediumDamageCard);
        cards.add(manaCard);

        return Deck.builder()
                .activeCards(cards)
                .build();
    }
}