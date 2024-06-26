package no.ntnu.dof.controller.gameplay.di;

import dagger.Component;
import no.ntnu.dof.controller.gameplay.TutorialController;
import no.ntnu.dof.model.di.TutorialPlayerModule;

@Component(modules = TutorialPlayerModule.class)
public interface TutorialComponent {
    void inject(TutorialController tutorialController);
}
