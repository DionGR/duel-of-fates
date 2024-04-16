package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.model.gameplay.card.Card;

public interface PlayerController {
    Optional<Card> choosePlay(long timeout) throws InterruptedException;
}
