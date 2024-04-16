package no.ntnu.dof.model.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Module
public class CardModule {

    @Provides
    @Named("attackCard_4")
    public Card provideAttackCard4() {
        return Card.builder()
                .name("attackCard_4")
                .viewName("Light Attack")
                .cost(new Mana(1))
                .opponentEffectName("damageEffect_4")
                .build();
    }

    @Provides
    @Named("attackCard_8")
    public Card provideAttackCard8() {
        return Card.builder()
                .name("attackCard_8")
                .viewName("Medium Attack")
                .cost(new Mana(3))
                .opponentEffectName("damageEffect_8")
                .build();
    }

    @Provides
    @Named("attackCard_12")
    public Card provideAttackCard12() {
        return Card.builder()
                .name("attackCard_12")
                .viewName("Heavy Attack")
                .cost(new Mana(5))
                .opponentEffectName("damageEffect_12")
                .build();
    }

    @Provides
    @Named("manaCard_2")
    public Card provideManaCard2() {
        return Card.builder()
                .name("manaCard_2")
                .viewName("Light Mana")
                .cost(new Mana(1))
                .hostEffectName("manaEffect_2")
                .build();
    }

    @Provides
    @Named("manaCard_5")
    public Card provideManaCard5() {
        return Card.builder()
                .name("manaCard_5")
                .viewName("Medium Mana")
                .cost(new Mana(3))
                .hostEffectName("manaEffect_5")
                .build();
    }

    @Provides
    @Named("healthCard_4")
    public Card provideHealthCard4() {
        return Card.builder()
                .name("healthCard_4")
                .viewName("Light Healing")
                .cost(new Mana(1))
                .hostEffectName("healthEffect_4")
                .build();
    }

    @Provides
    @Named("healthCard_8")
    public Card provideHealthCard8() {
        return Card.builder()
                .name("healthCard_8")
                .viewName("Medium Healing")
                .cost(new Mana(3))
                .hostEffectName("healthEffect_8")
                .build();
    }

    @Provides
    @Named("healthCard_12")
    public Card provideHealthCard12() {
        return Card.builder()
                .name("healthCard_12")
                .viewName("Heavy Healing")
                .cost(new Mana(5))
                .hostEffectName("healthEffect_12")
                .build();
    }

    @Provides
    @Named("poisonCard_4_4")
    public Card providePoisonCard4_4() {
        return Card.builder()
                .name("poison")
                .viewName("Poison")
                .cost(new Mana(4))
                .opponentEffectName("poisonEffect_4_4")
                .build();
    }

    @Provides
    @Named("passiveHealingCard_4_4")
    public Card providePassiveHealingCard4_4() {
        return Card.builder()
                .name("heal_over_time")
                .viewName("Passive Healing")
                .cost(new Mana(4))
                .hostEffectName("passiveHealingEffect_4_4")
                .build();
    }

    @Provides
    @Named("refillManaCard")
    public Card provideRefillManaCard() {
        return Card.builder()
                .name("refillManaCard")
                .viewName("Refill Mana")
                .cost(new Mana(4))
                .hostEffectName("refillManaEffect")
                .build();
    }

    @Provides
    @Named("versatileAttackCard_12")
    public Card provideVersatileAttackCard12() {
        return Card.builder()
                .name("versatileAttack")
                .viewName("Versatile Attack")
                .cost(new Mana(7))
                .opponentEffectName("damageEffect_12")
                .hostEffectName("manaEffect_4")
                .build();
    }

    @Provides
    @Named("defensiveHealthCard_12")
    public Card provideDefensiveHealthCard12() {
        return Card.builder()
                .name("heal_&_damage")
                .viewName("Defensive Healing")
                .cost(new Mana(7))
                .hostEffectName("healthEffect_12")
                .opponentEffectName("damageEffect_8")
                .build();
    }

    @Provides
    @Named("multiPoisonCard_4_4")
    public Card provideMultiPoisonCard4_4() {
        return Card.builder()
                .name("draining")
                .viewName("Draining")
                .cost(new Mana(7))
                .opponentEffectName("poisonEffect_4_4")
                .hostEffectName("passiveHealingEffect_4_4")
                .build();
    }

    @Provides
    @Named("extendedHealingCard_4_4")
    public Card provideExtendedHealingCard4_4() {
        return Card.builder()
                .name("extendedHealing")
                .viewName("Extended Healing")
                .cost(new Mana(6))
                .hostEffectName("passiveHealingEffect_4_4")
                .opponentEffectName("damageEffect_4")
                .build();
    }

    @Provides
    @Named("surpriseAttackCard_4")
    public Card provideSurpriseAttackCard4() {
        return Card.builder()
                .name("surpriseAttack")
                .viewName("Surprise Attack")
                .cost(new Mana(5))
                .opponentEffectName("damageEffect_4")
                .hostEffectName("refillManaEffect")
                .build();
    }

    @Provides
    @Named("abortCard")
    public Card provideAbortCard() {
        return Card.builder()
                .name("abort")
                .viewName("Abort")
                .cost(new Mana(0))
                .hostEffectName("suicideEffect")
                .build();
    }
}