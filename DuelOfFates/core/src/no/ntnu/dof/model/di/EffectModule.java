package no.ntnu.dof.model.di;

import java.util.HashMap;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.effect.EffectInvoker;
import no.ntnu.dof.model.gameplay.effect.card.DamageEffect;
import no.ntnu.dof.model.gameplay.effect.card.PassiveHealingEffect;
import no.ntnu.dof.model.gameplay.effect.card.PoisonEffect;
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
                .delta(-2)
                .build();
    }

    @Provides
    @Named("healthEffect_5")
    public Effect provideHealthEffect5() {
        return HealthEffect.builder()
                .name("healthEffect_5")
                .delta(-5)
                .build();
    }

    @Provides
    @Named("healthEffect_10")
    public Effect provideHealthEffect10() {
        return HealthEffect.builder()
                .name("healthEffect_10")
                .delta(-10)
                .build();
    }

    /* Poison Effect */
    @Provides
    @Named("poisonEffect_4_3")
    public Effect providePoisonEffect4_3() {
        return PoisonEffect.builder()
                .name("poisonEffect_4_3")
                .damage(4)
                .duration(3)
                .build();
    }

    /* Passive Healing Effect */
    @Provides
    @Named("passiveHealingEffect_3_5")
    public Effect providePassiveHealingEffect3_5() {
        return PassiveHealingEffect.builder()
                .name("passiveHealingEffect_3_5")
                .health(3)
                .duration(5)
                .build();
    }

    @Provides
    @Named("effectInvoker")
    public EffectInvoker<String, Effect> provideEffectInvoker(
            @Named("refillHandEffect") Effect refillHandEffect,
            @Named("refillManaEffect") Effect refillManaEffect,
            @Named("damageEffect_2") Effect damageEffect2,
            @Named("damageEffect_5") Effect damageEffect5,
            @Named("damageEffect_10") Effect damageEffect10,
            @Named("manaEffect_2") Effect manaEffect2,
            @Named("manaEffect_4") Effect manaEffect4,
            @Named("healthEffect_2") Effect healthEffect2,
            @Named("healthEffect_5") Effect healthEffect5,
            @Named("healthEffect_10") Effect healthEffect10,
            @Named("poisonEffect_4_3") Effect poisonEffect4_3,
            @Named("passiveHealingEffect_3_5") Effect passiveHealingEffect3_5
    ) {

        EffectInvoker<String, Effect> effectInvoker = new EffectInvoker<>();

        effectInvoker.register("refillHandEffect", refillHandEffect);
        effectInvoker.register("refillManaEffect", refillManaEffect);
        effectInvoker.register("damageEffect_2", damageEffect2);
        effectInvoker.register("damageEffect_5", damageEffect5);
        effectInvoker.register("damageEffect_10", damageEffect10);
        effectInvoker.register("manaEffect_2", manaEffect2);
        effectInvoker.register("manaEffect_4", manaEffect4);
        effectInvoker.register("healthEffect_2", healthEffect2);
        effectInvoker.register("healthEffect_5", healthEffect5);
        effectInvoker.register("healthEffect_10", healthEffect10);
        effectInvoker.register("poisonEffect_4_3", poisonEffect4_3);
        effectInvoker.register("passiveHealingEffect_3_5", passiveHealingEffect3_5);

        return effectInvoker;
    }
}
