package no.ntnu.dof.model.gameplay.playerclass;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.GameplayEntity;
import no.ntnu.dof.model.gameplay.deck.Deck;
import no.ntnu.dof.model.gameplay.stats.armor.Armor;
import no.ntnu.dof.model.gameplay.stats.health.Health;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Getter
@SuperBuilder
public class PlayerClass extends GameplayEntity {
    private final Deck deck;
    private final Health maxHealth;
    private final Armor maxArmor;
    private final Mana maxMana;
}
