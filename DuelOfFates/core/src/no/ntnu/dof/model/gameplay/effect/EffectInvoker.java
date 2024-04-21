package no.ntnu.dof.model.gameplay.effect;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class EffectInvoker<K, V extends Effect> {
    private final Map<K, V> effects;

    public EffectInvoker() {
        this.effects = new HashMap<>();
    }

    public void register(K key, V effect) {
        effects.put(key, effect);
    }

    @NonNull
    public V invoke(K key) {
        return effects.get(key);
    }

    public void printEffects() {
        effects.forEach((k, v) -> System.out.println(k + " -> " + v));
    }

}
