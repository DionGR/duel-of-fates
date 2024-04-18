package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.model.gameplay.card.Card;

public interface PlayerController {
    long TURN_TIMEOUT = 10_000; // milliseconds

    Optional<Card> choosePlay(long timeout) throws InterruptedException;
}
