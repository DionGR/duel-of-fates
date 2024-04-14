package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.controller.network.GameService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

public class RemotePlayerController implements PlayerController, GameService.PlayListener {
    protected Player player;
    protected Optional<Card> chosen;
    protected boolean played;

    public RemotePlayerController(Player player, GameComms comms) {
        this.player = player;
        this.chosen = Optional.empty();
        this.played = false;

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
