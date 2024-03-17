package no.ntnu.dof.entity;

public abstract class Entity {

    private final String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
