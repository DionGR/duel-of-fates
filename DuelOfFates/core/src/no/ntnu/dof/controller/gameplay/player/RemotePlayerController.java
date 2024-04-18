package no.ntnu.dof.controller.gameplay.player;

import com.badlogic.gdx.Gdx;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import no.ntnu.dof.controller.gameplay.di.DaggerRemotePlayerControllerComponent;
import no.ntnu.dof.controller.gameplay.di.RemotePlayerControllerComponent;
import no.ntnu.dof.controller.network.GameService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.communication.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

public class RemotePlayerController implements PlayerController, GameService.PlayListener {
    protected Player player;
    protected Optional<Card> chosen;
    protected boolean abort;
    @Inject
    @Named("abortCard")
    protected Card abortCard;

    public RemotePlayerController(Player player, GameComms comms) {
        this.player = player;
        this.chosen = Optional.empty();
        this.abort = false;

        ServiceLocator.getGameService().addPlayListener(comms, this);
        RemotePlayerControllerComponent component = DaggerRemotePlayerControllerComponent.create();
        component.inject(this);
    }

    @Override
    public synchronized Optional<Card> choosePlay(long timeout) throws InterruptedException {
        if (abort) return Optional.of(abortCard);
        chosen = Optional.empty();
        this.wait(timeout);
        if (abort) return Optional.of(abortCard);
        return chosen;
    }

    @Override
    public synchronized void onCardPlayed(Card card) {
        chosen = Optional.of(card);
        this.notify();
    }

    @Override
    public synchronized void onTurnEnd(String player) {
        if (!player.equals(this.player.getName())) return;
        chosen = Optional.empty();
        this.notify();
    }

    @Override
    public synchronized void onAbort() {
        Gdx.app.log("Game", "Opponent aborted.");
        abort = true;
        this.notify();
    }
}
