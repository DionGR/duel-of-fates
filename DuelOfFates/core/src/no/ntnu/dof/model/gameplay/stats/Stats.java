package no.ntnu.dof.model.gameplay.stats;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.effect.stats.ArmorEffect;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.effect.stats.HealthEffect;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;
import no.ntnu.dof.model.gameplay.effect.stats.ManaEffect;

@Getter
@Setter
@SuperBuilder
public class Stats extends GameplayEntity implements Comparable<Stats> {
    private Health health;
    private Armor armor;
    private Mana mana;

    public List<Effect> asEffects() {
        List<Effect> effects = new ArrayList<>();
        if (health.getValue() != 0) effects.add(HealthEffect.builder().delta(health.getValue()).build());
        if (armor.getValue() != 0) effects.add(ArmorEffect.builder().delta(armor.getValue()).build());
        if (mana.getValue() != 0) effects.add(ManaEffect.builder().delta(mana.getValue()).build());
        return effects;
    }

    @Override
    public int compareTo(Stats stats) {
        if (this.health.getValue() > stats.health.getValue()
                && this.armor.getValue() == stats.armor.getValue()
                && this.mana.getValue() == stats.mana.getValue()) {
            return 0;
        }

        return (this.health.getValue() > stats.health.getValue()
                && this.armor.getValue() >= stats.armor.getValue()
                && this.mana.getValue() >= stats.mana.getValue())
                ? 1 : -1;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + health.getValue() + " health, " + armor.getValue() + " armor, " + mana.getValue() + " mana)";
    }

    // Lombok customization, as per: https://stackoverflow.com/questions/53099011/lombok-customize-superbuilder
    public static abstract class StatsBuilder<C extends Stats, B extends StatsBuilder<C, B>> extends GameplayEntityBuilder<C, B> {
        public B health(int health) {
            this.health = new Health(health);
            return self();
        }

        public B armor(int armor) {
            this.armor = new Armor(armor);
            return self();
        }

        public B mana(int mana) {
            this.mana = new Mana(mana);
            return self();
        }
    }
}
