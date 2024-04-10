package no.ntnu.dof.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.ntnu.dof.model.gameplay.card.Card;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameComms {
    private String gameId;
    private String playerLastTurn;
    private List<Card> cards;

    public GameComms(String gameId) {
        this.gameId = gameId;
        this.playerLastTurn = null;
        this.cards = new ArrayList<>();
    }
}
