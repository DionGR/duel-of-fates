package no.ntnu.dof.model;

import lombok.Data;
import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;

@Data
public class User {
    private String id;
    private String email;
    private String name;
    private PlayerClass playerClass;

    public User() {}

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
