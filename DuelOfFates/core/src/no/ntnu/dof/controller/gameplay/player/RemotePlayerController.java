package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.controller.network.GameService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;

// TODO inherit from visual implementation of PlayerController
public class RemotePlayerController implements PlayerController, GameService.PlayListener {
    protected Optional<Card> chosen;
    protected boolean played;

    public RemotePlayerController() {
        this.chosen = Optional.empty();
        this.played = false;
        GameComms comms = ServiceLocator.getGameService().createComms("-NuBZPuG4gkubhYI_FsN");
        ServiceLocator.getGameService().addPlayListener(comms, this);
    }

    @Override
    public synchronized Optional<Card> choosePlay() {
        played = false;
        while (!played) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {}
        }
        played = false;
        return chosen;
    }

    @Override
    public synchronized void onCardPlayed(Card card) {
        card.flipEffects();
        chosen = Optional.of(card);
        played = true;
        this.notify();
    }

    @Override
    public synchronized void onTurnEnd() {
        chosen = Optional.empty();
        played = true;
        this.notify();
    }
}
