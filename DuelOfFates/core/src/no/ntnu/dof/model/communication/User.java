package no.ntnu.dof.model.communication;

import lombok.Data;

@Data
public class User {
    private String id;
    private String email;
    private String name;
    private String playerClassName;

    public User() {
        // Default constructor for serialization
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
        this.name = this.email.split("@")[0];
        this.playerClassName = "knight"; // default player class
    }

    public User(String id, String email, String name, String playerClassName) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.playerClassName = playerClassName;
    }
}
