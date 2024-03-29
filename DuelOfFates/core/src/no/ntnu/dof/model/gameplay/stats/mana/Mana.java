package no.ntnu.dof.model.gameplay.stats.mana;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Mana implements Comparable<Mana> {
    int value;

    @Override
    public int compareTo(Mana other) {
        return Integer.compare(this.value, other.value);
    }
}
