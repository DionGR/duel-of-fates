package no.ntnu.dof.view.di;

import javax.inject.Singleton;

import dagger.Component;
import no.ntnu.dof.view.screens.BaseScreen;

@Singleton
@Component(modules = {SoundControllerModule.class})
public interface BaseScreenComponent {

    void inject(BaseScreen baseScreen);
}
