package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.communication.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

public class HostPlayerController extends ClickPlayerController {
    private final Player player;
    private final GameComms comms;

    public HostPlayerController(Player player, GameComms comms) {
        this.player = player;
        this.comms = comms;
        comms.setPlayerLastTurn(this.player.getName());
    }

    @Override
    public Optional<Card> choosePlay(long timeout) throws InterruptedException {
        Optional<Card> play = super.choosePlay(timeout);

        comms.setPlayerLastTurn(player.getName());
        ServiceLocator.getGameService().playCard(comms, play);

        return play;
    }
}
