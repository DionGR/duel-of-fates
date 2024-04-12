package no.ntnu.dof.model.gameplay.playerclass;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;

@Getter
public class PlayerClassInvoker<K, V extends PlayerClass> {
    private final Map<K, V> playerClasses;

    public PlayerClassInvoker() {
        this.playerClasses = new HashMap<>();
    }

    public void register(K key, V effect) {
        playerClasses.put(key, effect);
    }

    public V invoke(K key) {
        return playerClasses.get(key);
    }

}
