package no.ntnu.dof.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class Entity {
    private String name;

    public Entity() {}
}
