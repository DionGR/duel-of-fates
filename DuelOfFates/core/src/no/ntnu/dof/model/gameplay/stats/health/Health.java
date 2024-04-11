package no.ntnu.dof.model.gameplay.stats.health;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Health {
    int value;

    public String toString() {
        return value + " HP";
    }
}
