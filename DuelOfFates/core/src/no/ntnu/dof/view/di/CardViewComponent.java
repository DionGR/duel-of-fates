package no.ntnu.dof.view.di;

import dagger.Component;
import no.ntnu.dof.model.di.EffectModule;
import no.ntnu.dof.view.gameplay.entity.CardView;

@Component(modules = EffectModule.class)
public interface CardViewComponent {
    void inject(CardView cardView);
}
