package no.ntnu.dof.model.gameplay.effect;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import no.ntnu.dof.model.gameplay.player.Player;

@Getter
public class EffectInvoker<K, V extends Effect> {
    private final Map<K, V> effects;

    public EffectInvoker(Map<K, V> effects) {
        this.effects = effects;
    }

    public void register(K key, V effect) {
        effects.put(key, effect);
    }

    public void invoke(K key, Player player) {
        effects.get(key).apply(player);
    }

}
