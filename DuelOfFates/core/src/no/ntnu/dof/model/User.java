package no.ntnu.dof.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String email;
    private String name;
    private String playerClassName;

    public User() {}

    public User(String id, String email) {
        this.id = id;
        this.email = email;
        this.name = this.email.split("@")[0];
        this.playerClassName = "warrior"; // NOTE: To be replaced by dynamically assigning a playerClass (name) from an available PlayerClass
    }
}
