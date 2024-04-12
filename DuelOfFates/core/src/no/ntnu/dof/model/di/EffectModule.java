package no.ntnu.dof.model.di;

import java.util.HashMap;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.effect.EffectInvoker;
import no.ntnu.dof.model.gameplay.effect.card.DamageEffect;
import no.ntnu.dof.model.gameplay.effect.card.RefillHandEffect;
import no.ntnu.dof.model.gameplay.effect.card.RefillManaEffect;
import no.ntnu.dof.model.gameplay.stats.health.HealthEffect;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;
import no.ntnu.dof.model.gameplay.stats.mana.ManaEffect;

@Module
public class EffectModule {

    @Provides
    @Named("refillHandEffect")
    public Effect provideRefillHandEffect() {
        return RefillHandEffect.builder()
                .name("refillHandEffect")
                .build();
    }

    @Provides
    @Named("refillManaEffect")
    public Effect provideRefillManaEffect() {
        return RefillManaEffect.builder()
                .name("refillManaEffect")
                .build();
    }

    /* Damage effects */

    @Provides
    @Named("damageEffect_2")
    public Effect provideDamageEffect2() {
        return DamageEffect.builder()
                .name("damageEffect_2")
                .damage(2)
                .build();
    }

    @Provides
    @Named("damageEffect_5")
    public Effect provideDamageEffect5() {
        return DamageEffect.builder()
                .name("damageEffect_5")
                .damage(5)
                .build();
    }

    @Provides
    @Named("damageEffect_10")
    public Effect provideDamageEffect10() {
        return DamageEffect.builder()
                .name("damageEffect_10")
                .damage(10)
                .build();
    }

    /* Mana effects */

    @Provides
    @Named("manaEffect_2")
    public Effect provideManaEffect2() {
        return ManaEffect.builder()
                .name("manaEffect_2")
                .delta(2)
                .build();
    }

    @Provides
    @Named("manaEffect_4")
    public Effect provideManaEffect4() {
        return ManaEffect.builder()
                .name("manaEffect_4")
                .delta(4)
                .build();
    }

    /* Health effects */

    @Provides
    @Named("healthEffect_2")
    public Effect provideHealthEffect2() {
        return HealthEffect.builder()
                .name("healthEffect_2")
                .delta(2)
                .build();
    }

    @Provides
    @Named("healthEffect_5")
    public Effect provideHealthEffect5() {
        return HealthEffect.builder()
                .name("healthEffect_5")
                .delta(5)
                .build();
    }

    @Provides
    @Named("healthEffect_10")
    public Effect provideHealthEffect10() {
        return HealthEffect.builder()
                .name("healthEffect_10")
                .delta(10)
                .build();
    }

    /* Poison Effect - Deals damage over time */
    @Provides
    @Named("poisonEffect_3")
    public Effect providePoisonEffect3() {
        return PoisonEffect.builder()
                .name("poisonEffect_3")
                .damagePerTurn(3)
                .duration(3)
                .build();
    }

    /* Armor Boost Effect */
    @Provides
    @Named("armorBoostEffect_5")
    public Effect provideArmorBoostEffect5() {
        return ArmorBoostEffect.builder()
                .name("armorBoostEffect_5")
                .boost(5)
                .build();
    }

    /* Double Strike Effect - Strikes twice */
    @Provides
    @Named("doubleStrikeEffect")
    public Effect provideDoubleStrikeEffect() {
        return DoubleStrikeEffect.builder()
                .name("doubleStrikeEffect")
                .build();
    }

    /* Freeze Effect - Opponent skips their turn */
    @Provides
    @Named("freezeEffect")
    public Effect provideFreezeEffect() {
        return FreezeEffect.builder()
                .name("freezeEffect")
                .build();
    }

    @Provides
    @Named("effectInvoker")
    public EffectInvoker<String, Effect> provideEffectInvoker(
                                                @Named("damageEffect") Effect damageEffect,
                                                @Named("refillHandEffect") Effect refillHandEffect,
                                                @Named("refillManaEffect") Effect refillManaEffect){

        EffectInvoker<String, Effect> effectInvoker = new EffectInvoker<>();

        return effectInvoker;
    }
}
