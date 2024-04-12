package no.ntnu.dof.model;

import lombok.Data;

@Data
public class GameSummary {
    private String matchId;
    private String player1Id;
    private String player2Id;
    private Boolean player1Win;
    private Boolean player2Win;
    private User player1;
    private User player2;

    public GameSummary() {}

    public GameSummary(String matchId, String player1Id, String player2Id, Boolean player1Win, Boolean player2Win) {
        this.matchId = matchId;


    }
}
