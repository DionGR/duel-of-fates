package no.ntnu.dof.model.di;

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
import no.ntnu.dof.model.gameplay.effect.card.SuicideEffect;
import no.ntnu.dof.model.gameplay.stats.health.HealthEffect;
import no.ntnu.dof.model.gameplay.stats.mana.ManaEffect;

@Module
public class EffectModule {
    @Provides
    @Named("refillHandEffect")
    public Effect provideRefillHandEffect() {
        return RefillHandEffect.builder()
                .name("refillHandEffect")
                .viewName("Refill")
                .description("Hand")
                .build();
    }

    @Provides
    @Named("refillManaEffect")
    public Effect provideRefillManaEffect() {
        return RefillManaEffect.builder()
                .name("refillManaEffect")
                .viewName("Refill")
                .description("Mana")
                .build();
    }

    /* Damage effects */

    @Provides
    @Named("damageEffect_4")
    public Effect provideDamageEffect4() {
        return DamageEffect.builder()
                .name("damageEffect_4")
                .viewName("Damage")
                .description("4 dp")
                .damage(4)
                .build();
    }

    @Provides
    @Named("damageEffect_8")
    public Effect provideDamageEffect8() {
        return DamageEffect.builder()
                .name("damageEffect_8")
                .viewName("Damage")
                .description("8 dp")
                .damage(8)
                .build();
    }

    @Provides
    @Named("damageEffect_12")
    public Effect provideDamageEffect12() {
        return DamageEffect.builder()
                .name("damageEffect_12")
                .viewName("Damage")
                .description("12 dp")
                .damage(12)
                .build();
    }

    /* Mana effects */

    @Provides
    @Named("manaEffect_2")
    public Effect provideManaEffect2() {
        return ManaEffect.builder()
                .name("manaEffect_2")
                .viewName("Mana")
                .description("2 mp")
                .delta(-2)
                .build();
    }

    @Provides
    @Named("manaEffect_5")
    public Effect provideManaEffect5() {
        return ManaEffect.builder()
                .name("manaEffect_5")
                .viewName("Mana")
                .description("5 mp")
                .delta(-5)
                .build();
    }

    /* Health effects */

    @Provides
    @Named("healthEffect_4")
    public Effect provideHealthEffect4() {
        return HealthEffect.builder()
                .name("healthEffect_4")
                .viewName("Health")
                .description("4 hp")
                .delta(-4)
                .build();
    }

    @Provides
    @Named("healthEffect_8")
    public Effect provideHealthEffect8() {
        return HealthEffect.builder()
                .name("healthEffect_8")
                .viewName("Health")
                .description("8 hp")
                .delta(-8)
                .build();
    }

    @Provides
    @Named("healthEffect_12")
    public Effect provideHealthEffect12() {
        return HealthEffect.builder()
                .name("healthEffect_12")
                .viewName("Health")
                .description("12 hp")
                .delta(-12)
                .build();
    }

    /* Poison Effect */
    @Provides
    @Named("poisonEffect_4_4")
    public Effect providePoisonEffect4_4() {
        return PoisonEffect.builder()
                .name("poisonEffect_4_4")
                .viewName("Poison")
                .description("4 dp, 4 turns")
                .damage(4)
                .duration(4)
                .build();
    }

    /* Passive Healing Effect */
    @Provides
    @Named("passiveHealingEffect_4_4")
    public Effect providePassiveHealingEffect4_4() {
        return PassiveHealingEffect.builder()
                .name("passiveHealingEffect_4_4")
                .viewName("Healing")
                .description("4 hp, 4 turns")
                .health(4)
                .duration(4)
                .build();
    }

    @Provides
    @Named("suicideEffect")
    public Effect provideSuicideEffect() {
        return SuicideEffect.builder()
                .name("suicideEffect")
                .viewName("Suicide")
                .description("death")
                .build();
    }

    @Provides
    @Named("effectInvoker")
    public EffectInvoker<String, Effect> provideEffectInvoker(
            @Named("refillHandEffect") Effect refillHandEffect,
            @Named("refillManaEffect") Effect refillManaEffect,
            @Named("damageEffect_4") Effect damageEffect4,
            @Named("damageEffect_8") Effect damageEffect8,
            @Named("damageEffect_12") Effect damageEffect12,
            @Named("manaEffect_2") Effect manaEffect2,
            @Named("manaEffect_5") Effect manaEffect5,
            @Named("healthEffect_4") Effect healthEffect4,
            @Named("healthEffect_8") Effect healthEffect8,
            @Named("healthEffect_12") Effect healthEffect12,
            @Named("poisonEffect_4_4") Effect poisonEffect4_4,
            @Named("passiveHealingEffect_4_4") Effect passiveHealingEffect4_4,
            @Named("suicideEffect") Effect suicideEffect
    ) {

        EffectInvoker<String, Effect> effectInvoker = new EffectInvoker<>();

        effectInvoker.register("refillHandEffect", refillHandEffect);
        effectInvoker.register("refillManaEffect", refillManaEffect);
        effectInvoker.register("damageEffect_4", damageEffect4);
        effectInvoker.register("damageEffect_8", damageEffect8);
        effectInvoker.register("damageEffect_12", damageEffect12);
        effectInvoker.register("manaEffect_2", manaEffect2);
        effectInvoker.register("manaEffect_5", manaEffect5);
        effectInvoker.register("healthEffect_4", healthEffect4);
        effectInvoker.register("healthEffect_8", healthEffect8);
        effectInvoker.register("healthEffect_12", healthEffect12);
        effectInvoker.register("poisonEffect_4_4", poisonEffect4_4);
        effectInvoker.register("passiveHealingEffect_4_4", passiveHealingEffect4_4);
        effectInvoker.register("suicideEffect", suicideEffect);

        return effectInvoker;
    }
}
