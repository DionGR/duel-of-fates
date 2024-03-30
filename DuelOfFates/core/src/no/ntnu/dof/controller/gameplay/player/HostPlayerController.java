package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

// TODO inherit from visual implementation of PlayerController
public class HostPlayerController extends CliPlayerController {
    private final GameComms comms;

    public HostPlayerController(Player player) {
        super(player);
        comms = ServiceLocator.getGameService().createComms("-NuBZPuG4gkubhYI_FsN");
    }

    @Override
    public Optional<Card> choosePlay() {
        Optional<Card> play = super.choosePlay();
        ServiceLocator.getGameService().playCard(comms, play);
        return play;
    }
}
