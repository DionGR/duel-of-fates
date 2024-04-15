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

    public GameSummary(String matchId, String player1Id, String player2Id, Boolean player1Win, Boolean player2Win, User player1, User player2) {
        this.matchId = matchId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1Win = player1Win;
        this.player2Win = player2Win;
        this.player1 = player1;
        this.player2 = player2;

    }
}
