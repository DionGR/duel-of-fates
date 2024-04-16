package no.ntnu.dof.model;

import lombok.Data;

@Data
public class GameSummary {
    private String matchId;
    private String hostId;
    private String guestId;
    private Boolean hostWin;
    private Boolean guestWin;
    private User userHost;
    private User userGuest;

    public GameSummary() {}

    public GameSummary(String hostId, String guestId, Boolean hostWin, Boolean guestWin) {
        this.hostId = hostId;
        this.guestId = guestId;
        this.hostWin = hostWin;
        this.guestWin = guestWin;
    }

    public GameSummary(String matchId, String hostId, String guestId, Boolean hostWin, Boolean guestWin) {
        this.matchId = matchId;
        this.hostId = hostId;
        this.guestId = guestId;
        this.hostWin = hostWin;
        this.guestWin = guestWin;
    }
}
