package no.ntnu.dof.controller.lobby;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.dof.model.GameSummary;
import no.ntnu.dof.model.User;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.screens.lobby.HistoryScreen;

public class HistoryController {
    private List<GameSummary> matches;
    private HistoryScreen historyScreen;
    private User user;

    public HistoryController(User user, HistoryScreen historyScreen){
        this.historyScreen = historyScreen;
        this.user = user;
        createMockGameSummary();
        historyScreen.showMockGameSummaries(matches); // Call the method here
    }

    public void createMockGameSummary() {
        matches = new ArrayList<>();

        User player1 = new User("player1_id", "player1@test.com");
        User player2 = new User("player2_id", "player2@test.com");
        GameSummary gs1 = new GameSummary("Match1", "player1_id", "player2_id", true, false);
        gs1.setUserHost(player1);
        gs1.setUserGuest(player2);
        GameSummary gs2 = new GameSummary("Match2", "player1_id", "player2_id", false, true);
        gs2.setUserHost(player1);
        gs2.setUserGuest(player2);
        matches.add(gs1);
        matches.add(gs2);
    }

    public void showMockGameSummary() {
        historyScreen.showMockGameSummaries(matches);
    }
    public List<GameSummary> getMatches() {
        return matches;
    }
}
