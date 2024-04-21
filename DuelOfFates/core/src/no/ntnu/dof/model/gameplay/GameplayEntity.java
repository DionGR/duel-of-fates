package no.ntnu.dof.model.gameplay;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class GameplayEntity {
    private String name;
    private String viewName;
    private String description;

    public GameplayEntity() {
    }
}
