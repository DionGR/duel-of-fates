package no.ntnu.dof.view.di;

import javax.inject.Singleton;

import dagger.Component;
import no.ntnu.dof.model.di.PlayerClassModule;
import no.ntnu.dof.view.screen.menu.ChooseClassScreen;

@Singleton
@Component(modules = {PlayerClassModule.class, SoundControllerModule.class})
public interface ChooseClassScreenComponent {
    void inject(ChooseClassScreen chooseClassScreen);
}
