package no.ntnu.dof.model;

import no.ntnu.dof.controller.DuelOfFates;

public class User {
    private String id;
    private String email;
    private String name;

    public User() {}

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
