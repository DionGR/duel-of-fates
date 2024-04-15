package no.ntnu.dof.view.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import no.ntnu.dof.controller.SoundController;

@Module
public class SoundControllerModule {

    @Provides
    @Named("soundController")
    public SoundController provideSoundController() {
        return SoundController.getInstance();
    }
}
