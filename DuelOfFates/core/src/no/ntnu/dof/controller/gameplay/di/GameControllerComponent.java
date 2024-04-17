package no.ntnu.dof.controller.gameplay.di;

import javax.inject.Named;

import dagger.Component;
import no.ntnu.dof.controller.gameplay.GameController;
import no.ntnu.dof.model.di.DeckModule;
import no.ntnu.dof.model.di.PlayerClassModule;

@Component(modules = PlayerClassModule.class)
public interface GameControllerComponent {
    void inject(GameController gameController);
}
