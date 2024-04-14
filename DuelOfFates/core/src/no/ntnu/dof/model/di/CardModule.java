package no.ntnu.dof.model.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Module
public class CardModule {

    @Provides
    @Named("exampleCard")
    public Card provideAttackCard() {
        return Card.builder()
                .name("attack_1")
                .cost(new Mana(3))
                .opponentEffectName("damageEffect")
                .build();
    }

    //TODO: Add more cards
}
