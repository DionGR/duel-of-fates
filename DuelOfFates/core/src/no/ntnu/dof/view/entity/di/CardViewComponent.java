package no.ntnu.dof.view.entity.di;

import dagger.Component;
import no.ntnu.dof.model.di.EffectModule;
import no.ntnu.dof.view.entity.view.CardView;

@Component(modules = EffectModule.class)
public interface CardViewComponent {
    void inject(CardView cardView);
}
