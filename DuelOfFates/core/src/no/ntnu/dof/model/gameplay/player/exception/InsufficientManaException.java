package no.ntnu.dof.model.gameplay.player.exception;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;

@AllArgsConstructor
public class InsufficientManaException extends Exception {
    private final Player player;
    private final Card card;

    @NonNull
    @Override
    public String toString() {
        return "Not enough resources for " + player.getName() + " to play card " + card.getName()
                + ":\nplayer has " + player + ", card requires " + card.getCost();
    }
}
