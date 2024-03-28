package no.ntnu.dof.controller.gameplay;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

@AllArgsConstructor
public abstract class PlayerController {
    protected final Player player;

    public abstract Optional<Card> choosePlay();
}
