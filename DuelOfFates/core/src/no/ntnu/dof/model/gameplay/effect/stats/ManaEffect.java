package no.ntnu.dof.model.gameplay.effect.stats;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@SuperBuilder
public class ManaEffect extends Effect {
    private int delta;

    @Override
    public void apply(Player player) {
        player.setMana(new Mana(player.getMana() + delta));
    }
}
