package no.ntnu.dof.model.gameplay;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.Entity;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class GameplayEntity extends Entity {

}
