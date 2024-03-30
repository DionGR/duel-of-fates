package no.ntnu.dof.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import no.ntnu.dof.model.gameplay.card.Card;

@Data
public class GameComms {
    private String gameId;
    private String playerLastTurn;
    private List<Card> cards;

    public GameComms() {}

    public GameComms(String gameId) {
        this.gameId = gameId;
        this.playerLastTurn = null;
        this.cards = new ArrayList<>();
    }

    public GameComms(String gameId, String player, List<Card> cards) {
        this.gameId = gameId;
        this.playerLastTurn = player;
        this.cards = cards;
    }
}
