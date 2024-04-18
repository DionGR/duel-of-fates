package no.ntnu.dof.model.gameplay.effect.card;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.armor.ArmorEffect;
import no.ntnu.dof.model.gameplay.stats.health.HealthEffect;

@SuperBuilder
public class DamageEffect extends Effect {
    private final int damage;

    @Override
    public void apply(@NonNull final Player player) {
        List<Effect> effects = new ArrayList<>();

        int remainingDamage = damage;
        if (player.getArmor().getValue() > 0) {
            int armorDamage = Integer.min(player.getArmor().getValue(), damage);
            remainingDamage = damage - armorDamage;
            effects.add(ArmorEffect.builder().delta(armorDamage).build());
        }

        if (remainingDamage > 0) {
            effects.add(HealthEffect.builder().delta(remainingDamage).build());
        }

        effects.forEach(e -> e.apply(player));
    }

    @Override
    public String toString() {
        return "damage=" + damage;
    }
}
