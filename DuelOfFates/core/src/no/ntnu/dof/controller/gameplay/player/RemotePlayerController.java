package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.controller.network.GameService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

// TODO inherit from visual implementation of PlayerController
public class RemotePlayerController implements PlayerController, GameService.PlayListener {
    protected Player player;
    protected Optional<Card> chosen;
    protected boolean played;

    public RemotePlayerController(Player player) {
        this.player = player;
        this.chosen = Optional.empty();
        this.played = false;

        GameComms comms = ServiceLocator
                .getGameService()
                .createComms("-NuBZPuG4gkubhYI_FsN"); // TODO inject gameId

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
    public synchronized void onTurnEnd(String player) {
        if (!player.equals(this.player.getName())) return;

        chosen = Optional.empty();
        played = true;

        this.notify();
    }
}
