package no.ntnu.dof.view.di;

import dagger.Component;
import no.ntnu.dof.model.di.CardModule;
import no.ntnu.dof.model.di.EffectModule;
import no.ntnu.dof.view.entity.control.AbortButton;

@Component(modules = {CardModule.class, EffectModule.class})
public interface AbortButtonComponent {
    void inject(AbortButton abortButton);
}
