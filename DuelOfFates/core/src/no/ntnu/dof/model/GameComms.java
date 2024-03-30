package no.ntnu.dof.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import no.ntnu.dof.model.gameplay.card.Card;

@Data
public class GameComms {
    private String gameId;
    private boolean turnFlag; // TODO consider changing turn end methodology
    private List<Card> cards;

    public GameComms() {}

    public GameComms(String gameId) {
        this.gameId = gameId;
        this.turnFlag = false;
        this.cards = new ArrayList<>();
    }

    public GameComms(String gameId, boolean turnFlag, List<Card> cards) {
        this.gameId = gameId;
        this.turnFlag = turnFlag;
        this.cards = cards;
    }
}
