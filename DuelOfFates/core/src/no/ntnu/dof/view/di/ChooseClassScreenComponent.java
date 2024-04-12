package no.ntnu.dof.view.di;

import dagger.Component;
import no.ntnu.dof.model.di.PlayerClassModule;
import no.ntnu.dof.view.screens.menu.ChooseClassScreen;

@Component(modules = PlayerClassModule.class)
public interface ChooseClassScreenComponent {
    void inject(ChooseClassScreen chooseClassScreen);
}
