package no.ntnu.dof.controller.gameplay.di;

import dagger.Component;
import no.ntnu.dof.controller.gameplay.player.RemotePlayerController;
import no.ntnu.dof.model.di.CardModule;

@Component(modules = CardModule.class)
public interface RemotePlayerControllerComponent {
    void inject(RemotePlayerController remotePlayerController);
}
