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

@Module
public class EffectModule {

    @Provides
    @Named("damageEffect")
    public Effect provideDamageEffect() {
        return DamageEffect.builder()
                .name("damage")
                .damage(5)
                .build();
    }

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

    @Provides
    @Named("effectInvoker")
    public EffectInvoker<String, Effect> provideEffectInvoker(
                                                @Named("damageEffect") Effect damageEffect,
                                                @Named("refillHandEffect") Effect refillHandEffect,
                                                @Named("refillManaEffect") Effect refillManaEffect){

        EffectInvoker<String, Effect> effectInvoker = new EffectInvoker<>();

        effectInvoker.register(damageEffect.getName(), damageEffect);
        effectInvoker.register(refillHandEffect.getName(), refillHandEffect);
        effectInvoker.register(refillManaEffect.getName(), refillManaEffect);

        return effectInvoker;
    }
}
