package no.ntnu.dof.view.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.controller.application.SoundController;

@Module
public class SoundControllerModule {
    @Provides
    @Named("soundController")
    public SoundController provideSoundController() {
        return SoundController.getInstance();
    }
}
