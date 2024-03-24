package no.ntnu.dof.model.gameplay.stats.mana;

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Getter
@Value
public class Mana {
    @NonNull Integer value;
}
