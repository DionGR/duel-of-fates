package no.ntnu.dof.model.di;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.card.DefenseCard;
import no.ntnu.dof.model.gameplay.card.UtilityCard;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Module
public class CardModule {

    @Provides
    @Named("attackCard_2")
    public Card provideAttackCard2() {
        return AttackCard.builder()
                .name("attackCard_2")
                .cost(new Mana(2))
                .opponentEffectName("damageEffect_2")
                .build();
    }

    @Provides
    @Named("attackCard_5")
    public Card provideAttackCard5() {
        return AttackCard.builder()
                .name("attackCard_5")
                .cost(new Mana(3))
                .opponentEffectName("damageEffect_5")
                .build();
    }

    @Provides
    @Named("attackCard_10")
    public Card provideAttackCard10() {
        return AttackCard.builder()
                .name("attackCard_10")
                .cost(new Mana(6))
                .opponentEffectName("damageEffect_10")
                .build();
    }

    @Provides
    @Named("manaCard_2")
    public Card provideManaCard2() {
        return UtilityCard.builder()
                .name("manaCard_2")
                .cost(new Mana(1))
                .hostEffectName("manaEffect_2")
                .build();
    }

    @Provides
    @Named("manaCard_4")
    public Card provideManaCard4() {
        return UtilityCard.builder()
                .name("manaCard_4")
                .cost(new Mana(3))
                .hostEffectName("manaEffect_4")
                .build();
    }

    @Provides
    @Named("healthCard_2")
    public Card provideHealthCard2() {
        return DefenseCard.builder()
                .name("healthCard_2")
                .cost(new Mana(2))
                .hostEffectName("healthEffect_2")
                .build();
    }

    @Provides
    @Named("healthCard_5")
    public Card provideHealthCard5() {
        return DefenseCard.builder()
                .name("healthCard_5")
                .cost(new Mana(3))
                .hostEffectName("healthEffect_5")
                .build();
    }

    @Provides
    @Named("healthCard_10")
    public Card provideHealthCard10() {
        return DefenseCard.builder()
                .name("healthCard_10")
                .cost(new Mana(6))
                .hostEffectName("healthEffect_10")
                .build();
    }

    @Provides
    @Named("poisonCard_4_3")
    public Card providePoisonCard4_3() {
        return AttackCard.builder()
                .name("poisonCard_4_3")
                .cost(new Mana(4))
                .opponentEffectName("poisonEffect_4_3")
                .build();
    }

    @Provides
    @Named("passiveHealingCard_3_5")
    public Card providePassiveHealingCard3_5() {
        return DefenseCard.builder()
                .name("passiveHealingCard_3_5")
                .cost(new Mana(5))
                .hostEffectName("passiveHealingEffect_3_5")
                .build();
    }

    @Provides
    @Named("refillHandCard")
    public Card provideRefillHandCard() {
        return UtilityCard.builder()
                .name("refillHandCard")
                .cost(new Mana(5))
                .hostEffectName("refillHandEffect")
                .build();
    }

    @Provides
    @Named("refillManaCard")
    public Card provideRefillManaCard() {
        return UtilityCard.builder()
                .name("refillManaCard")
                .cost(new Mana(6))
                .hostEffectName("refillManaEffect")
                .build();
    }

    @Provides
    @Named("versatileAttackCard_10")
    public Card provideVersatileAttackCard10() {
        return AttackCard.builder()
                .name("versatileAttackCard_10")
                .cost(new Mana(8))
                .opponentEffectName("damageEffect_10")
                .hostEffectName("manaEffect_4")
                .build();
    }

    @Provides
    @Named("defensiveHealthCard_10")
    public Card provideDefensiveHealthCard10() {
        return DefenseCard.builder()
                .name("defensiveHealthCard_10")
                .cost(new Mana(8))
                .hostEffectName("healthEffect_10")
                .opponentEffectName("damageEffect_2")
                .build();
    }

    @Provides
    @Named("multiPoisonCard_4_3")
    public Card provideMultiPoisonCard4_3() {
        return AttackCard.builder()
                .name("multiPoisonCard_4_3")
                .cost(new Mana(7))
                .opponentEffectName("poisonEffect_4_3")
                .hostEffectName("healthEffect_5")
                .build();
    }

    @Provides
    @Named("extendedHealingCard_3_5")
    public Card provideExtendedHealingCard3_5() {
        return DefenseCard.builder()
                .name("extendedHealingCard_3_5")
                .cost(new Mana(7))
                .hostEffectName("passiveHealingEffect_3_5")
                .opponentEffectName("damageEffect_2")
                .build();
    }

    @Provides
    @Named("surpriseAttackCard_2")
    public Card provideSurpriseAttackCard2() {
        return AttackCard.builder()
                .name("surpriseAttackCard_2")
                .cost(new Mana(1))
                .opponentEffectName("damageEffect_2")
                .hostEffectName("refillManaEffect")
                .build();
    }
}