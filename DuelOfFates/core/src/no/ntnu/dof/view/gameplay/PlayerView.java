package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.scenes.scene2d.Actor;

import no.ntnu.dof.model.gameplay.player.Player;

public class PlayerView extends Actor {
    private final Player player;

    public PlayerView(Player player) {
        this.player = player;
        // TODO init hand view
        // TODO init deck view
    }
}
