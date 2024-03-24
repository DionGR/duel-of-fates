package no.ntnu.dof.model.gameplay.stats.health;

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Getter
@Value
public class Health {
    @NonNull Integer value;
}
