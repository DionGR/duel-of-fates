package no.ntnu.dof.model.di;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import jdk.jfr.Name;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClassInvoker;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Module(includes = {DeckModule.class})
public class PlayerClassModule {

    @Provides
    @Named("warriorPlayerClass")
    public PlayerClass provideWarriorPlayerClass(@Named("exampleDeck") Deck deck) {
        return PlayerClass.builder()
                .name("warrior")
                .deck(deck)
                .maxHealth(new Health(100))
                .maxArmor(new Armor(100))
                .maxMana(new Mana(100))
                .build();
    }

    @Provides
    @Named("magePlayerClass")
    public PlayerClass provideMagePlayerClass(@Named("exampleDeck") Deck deck) {
        return PlayerClass.builder()
                .name("mage")
                .deck(deck)
                .maxHealth(new Health(100))
                .maxArmor(new Armor(100))
                .maxMana(new Mana(100))
                .build();
    }

    @Provides
    @Named("roguePlayerClass")
    public PlayerClass provideRoguePlayerClass(@Named("exampleDeck") Deck deck) {
        return PlayerClass.builder()
                .name("rogue")
                .deck(deck)
                .maxHealth(new Health(100))
                .maxArmor(new Armor(100))
                .maxMana(new Mana(100))
                .build();
    }

    @Provides
    @Named("listPlayerClasses")
    public List<PlayerClass> providePlayerClasses(
            @Named("warriorPlayerClass") PlayerClass warriorPlayerClass,
            @Named("magePlayerClass") PlayerClass magePlayerClass,
            @Named("roguePlayerClass") PlayerClass roguePlayerClass) {

        final ArrayList<PlayerClass> playerClasses = new ArrayList<>();

        playerClasses.add(warriorPlayerClass);
        playerClasses.add(magePlayerClass);
        playerClasses.add(roguePlayerClass);

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