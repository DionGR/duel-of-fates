package no.ntnu.dof.model.gameplay.effect;

import lombok.experimental.SuperBuilder;
import no.ntnu.dof.model.gameplay.player.Player;

@SuperBuilder
public class DamageEffect extends Effect {
    private int damage;

    @Override
    public void apply(Player player) {
        if (player.getDefense() > damage) {
            player.setDefense(player.getDefense() - damage);
        } else if (player.getDefense() > 0) {
            int remainingDamage = damage - player.getDefense();
            player.setDefense(0);
            player.setHealth(player.getHealth() - remainingDamage);
        } else {
            player.setHealth(player.getHealth() - damage);
        }
    }
}
