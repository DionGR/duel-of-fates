package no.ntnu.dof.model.gameplay.player;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import no.ntnu.dof.model.gameplay.card.Card;

@AllArgsConstructor
public class InsufficientResourcesException extends Exception {
    private final Player player;
    private final Card card;

    @NonNull
    @Override
    public String toString() {
        return "Not enough resources for " + player.getName() + " to play card " + card.getName()
                + ":\nplayer has " + player.getLiveStats() + ", card requires " + card.getCost();
    }
}
