package no.ntnu.dof.model.stats;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;

@Getter
@Setter
@SuperBuilder
public class Stats extends GameplayEntity {

    private int health;
    private int armor;
    private int mana;

    public void subtract(Stats stats) {
        this.health -= stats.health;
        this.armor -= stats.armor;
        this.mana -= stats.mana;
    }

    public void add(Stats stats) {
        this.health += stats.health;
        this.armor += stats.armor;
        this.mana += stats.mana;
    }

    public int compareTo(Stats stats) {
        if (this.health > stats.health
                && this.armor == stats.armor
                && this.mana == stats.mana) {
            return 0;
        }

        return (this.health > stats.health
                && this.armor >= stats.armor
                && this.mana >= stats.mana)
                ? 1 : -1;
    }

}
