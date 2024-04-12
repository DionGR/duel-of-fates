package no.ntnu.dof.controller.gameplay.di;

import dagger.Component;
import no.ntnu.dof.controller.lobby.GameLobbyController;
import no.ntnu.dof.model.di.PlayerClassModule;

@Component(modules = PlayerClassModule.class)
public interface GameLobbyControllerComponent {
    void inject(GameLobbyController gameLobbyController);
}
