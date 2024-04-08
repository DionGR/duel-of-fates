package no.ntnu.dof.model.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Module(includes = {DeckModule.class})
public class PlayerClassModule {

    @Provides
    public PlayerClass providePlayerClass(@Named("exampleDeck") Deck deck) {
        return PlayerClass.builder()
                .deck(deck)
                .maxHealth(new Health(100))
                .maxArmor(new Armor(50))
                .maxMana(new Mana(100))
                .build();
    }
}