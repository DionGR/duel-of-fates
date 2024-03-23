package no.ntnu.dof.controller.gameplay;

import java.util.Optional;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

public abstract class PlayerController {
    protected Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public abstract Optional<Card> choosePlay();
}
