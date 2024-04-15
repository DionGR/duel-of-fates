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

        matches.add(new GameSummary("Match1", "player1_id", "player2_id", true, false, player1, player2));
        matches.add(new GameSummary("Match2", "player1_id", "player2_id", false, true, player1, player2));
    }

    public void showMockGameSummary() {
        historyScreen.showMockGameSummaries(matches);
    }
    public List<GameSummary> getMatches() {
        return matches;
    }
}
