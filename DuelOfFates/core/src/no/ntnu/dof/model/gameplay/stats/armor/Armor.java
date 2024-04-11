package no.ntnu.dof.model.gameplay.stats.armor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Armor {
    int value;

    public String toString() {
        return value + " ARMOR";
    }
}
