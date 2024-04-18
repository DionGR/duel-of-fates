package no.ntnu.dof.model.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClassInvoker;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Module(includes = {DeckModule.class})
public class PlayerClassModule {
    @Provides
    @Named("knightPlayerClass")
    public PlayerClass provideKnightPlayerClass(@Named("knightDeck") Deck deck) {
        return PlayerClass.builder()
                .name("knight")
                .deck(deck)
                .maxHealth(new Health(40))
                .maxArmor(new Armor(30))
                .maxMana(new Mana(6))
                .build();
    }

    @Provides
    @Named("magePlayerClass")
    public PlayerClass provideMagePlayerClass(@Named("mageDeck") Deck deck) {
        return PlayerClass.builder()
                .name("mage")
                .deck(deck)
                .maxHealth(new Health(40))
                .maxArmor(new Armor(15))
                .maxMana(new Mana(9))
                .build();
    }

    @Provides
    @Named("skeletonPlayerClass")
    public PlayerClass provideSkeletonPlayerClass(@Named("skeletonDeck") Deck deck) {
        return PlayerClass.builder()
                .name("skeleton")
                .deck(deck)
                .maxHealth(new Health(70))
                .maxArmor(new Armor(0))
                .maxMana(new Mana(7))
                .build();
    }

    @Provides
    @Named("listPlayerClasses")
    public List<PlayerClass> providePlayerClasses(
            @Named("knightPlayerClass") PlayerClass knightPlayerClass,
            @Named("magePlayerClass") PlayerClass magePlayerClass,
            @Named("skeletonPlayerClass") PlayerClass skeletonPlayerClass
    ) {

        final ArrayList<PlayerClass> playerClasses = new ArrayList<>();

        playerClasses.add(knightPlayerClass);
        playerClasses.add(magePlayerClass);
        playerClasses.add(skeletonPlayerClass);

        return playerClasses;
    }

    @Provides
    @Named("playerClassInvoker")
    public PlayerClassInvoker<String, PlayerClass> providePlayerClassInvoker(@Named("listPlayerClasses")
                                                                                 List<PlayerClass> playerClasses) {

        PlayerClassInvoker<String, PlayerClass> playerClassInvoker = new PlayerClassInvoker<>();

        for (PlayerClass playerClass : playerClasses) {
            playerClassInvoker.register(playerClass.getName(), playerClass);
        }

        return playerClassInvoker;
    }
}