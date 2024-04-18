package no.ntnu.dof.controller.gameplay.di;

import dagger.Component;
import no.ntnu.dof.model.di.EffectModule;
import no.ntnu.dof.model.gameplay.Game;

@Component(modules = EffectModule.class)
public interface GameComponent {
    void inject(Game game);
}
