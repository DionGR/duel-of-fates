package no.ntnu.dof.model.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Module
public class CardModule {

    @Provides
    @Named("attackCard_2")
    public Card provideAttackCard2() {
        return Card.builder()
                .name("attackCard_2")
                .cost(new Mana(1))
                .opponentEffectName("damageEffect_2")
                .build();
    }

    @Provides
    @Named("attackCard_5")
    public Card provideAttackCard5() {
        return Card.builder()
                .name("attackCard_5")
                .cost(new Mana(3))
                .opponentEffectName("damageEffect_5")
                .build();
    }

    @Provides
    @Named("attackCard_12")
    public Card provideAttackCard12() {
        return Card.builder()
                .name("attackCard_12")
                .cost(new Mana(6))
                .opponentEffectName("damageEffect_12")
                .build();
    }

    @Provides
    @Named("manaCard_2")
    public Card provideManaCard2() {
        return Card.builder()
                .name("manaCard_2")
                .cost(new Mana(1))
                .hostEffectName("manaEffect_2")
                .build();
    }

    @Provides
    @Named("manaCard_5")
    public Card provideManaCard5() {
        return Card.builder()
                .name("manaCard_5")
                .cost(new Mana(3))
                .hostEffectName("manaEffect_5")
                .build();
    }

    @Provides
    @Named("healthCard_2")
    public Card provideHealthCard2() {
        return Card.builder()
                .name("healthCard_2")
                .cost(new Mana(2))
                .hostEffectName("healthEffect_2")
                .build();
    }

    @Provides
    @Named("healthCard_5")
    public Card provideHealthCard5() {
        return Card.builder()
                .name("healthCard_5")
                .cost(new Mana(3))
                .hostEffectName("healthEffect_5")
                .build();
    }

    @Provides
    @Named("healthCard_12")
    public Card provideHealthCard12() {
        return Card.builder()
                .name("healthCard_12")
                .cost(new Mana(6))
                .hostEffectName("healthEffect_12")
                .build();
    }

    @Provides
    @Named("poisonCard_4_3")
    public Card providePoisonCard4_3() {
        return Card.builder()
                .name("poisonCard_4_3")
                .cost(new Mana(4))
                .opponentEffectName("poisonEffect_4_3")
                .build();
    }

    @Provides
    @Named("passiveHealingCard_3_4")
    public Card providePassiveHealingCard3_4() {
        return Card.builder()
                .name("passiveHealingCard_3_4")
                .cost(new Mana(4))
                .hostEffectName("passiveHealingEffect_3_4")
                .build();
    }

    @Provides
    @Named("refillHandCard")
    public Card provideRefillHandCard() {
        return Card.builder()
                .name("refillHandCard")
                .cost(new Mana(5))
                .hostEffectName("refillHandEffect")
                .build();
    }

    @Provides
    @Named("refillManaCard")
    public Card provideRefillManaCard() {
        return Card.builder()
                .name("refillManaCard")
                .cost(new Mana(4))
                .hostEffectName("refillManaEffect")
                .build();
    }

    @Provides
    @Named("versatileAttackCard_12")
    public Card provideVersatileAttackCard12() {
        return Card.builder()
                .name("versatileAttackCard_12")
                .cost(new Mana(8))
                .opponentEffectName("damageEffect_12")
                .hostEffectName("manaEffect_4")
                .build();
    }

    @Provides
    @Named("defensiveHealthCard_12")
    public Card provideDefensiveHealthCard12() {
        return Card.builder()
                .name("defensiveHealthCard_12")
                .cost(new Mana(8))
                .hostEffectName("healthEffect_12")
                .opponentEffectName("damageEffect_5")
                .build();
    }

    @Provides
    @Named("multiPoisonCard_4_3")
    public Card provideMultiPoisonCard4_3() {
        return Card.builder()
                .name("multiPoisonCard_4_3")
                .cost(new Mana(7))
                .opponentEffectName("poisonEffect_4_3")
                .hostEffectName("passiveHealingEffect_3_4")
                .build();
    }

    @Provides
    @Named("extendedHealingCard_3_4")
    public Card provideExtendedHealingCard3_4() {
        return Card.builder()
                .name("extendedHealingCard_3_4")
                .cost(new Mana(5))
                .hostEffectName("passiveHealingEffect_3_4")
                .opponentEffectName("damageEffect_2")
                .build();
    }

    @Provides
    @Named("surpriseAttackCard_2")
    public Card provideSurpriseAttackCard2() {
        return Card.builder()
                .name("surpriseAttackCard_2")
                .cost(new Mana(2))
                .opponentEffectName("damageEffect_2")
                .hostEffectName("refillManaEffect")
                .build();
    }
}