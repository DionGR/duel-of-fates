package no.ntnu.dof.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
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

    public GameComms(String gameId, String startingPlayerName) {
        this.gameId = gameId;
        this.playerLastTurn = startingPlayerName;
        this.cards = new ArrayList<>();
    }
}
