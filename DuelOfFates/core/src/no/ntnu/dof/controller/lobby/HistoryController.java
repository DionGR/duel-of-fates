package no.ntnu.dof.controller.lobby;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.controller.network.UserService;
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
        //createMockGameSummary();
        fetchGameSummaries();
        historyScreen.showGameSummaries(matches); // Call the method here
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

    private void fetchGameSummaries() {
        ServiceLocator.getUserService().fetchUserGameSummaries(user.getId(), new UserService.GameSummariesCallback() {
            @Override
            public void onSuccess(List<GameSummary> summaries) {
                matches = summaries;
                fetchGuestUsers(0);

            }

            @Override
            public void onFailure(Exception e) {
                Gdx.app.log("History", "Failed to fetch game summaries: " + e.getMessage());
            }
        });
    }

    private void fetchGuestUsers(int index) {
        if (index >= matches.size()) {
            historyScreen.showGameSummaries(new ArrayList<>(matches));
            return;
        }

        GameSummary summary = matches.get(index);
        ServiceLocator.getUserService().fetchUserById(summary.getGuestId(), new UserService.UserCallback() {
            @Override
            public void onSuccess(User guest) {
                summary.setUserGuest(guest);
                fetchGuestUsers(index + 1); // Fetch next guest
            }

            @Override
            public void onFailure(Exception e) {
                Gdx.app.log("History", "Failed to fetch guest details: " + e.getMessage());
                fetchGuestUsers(index + 1); // Continue with next guest despite the error
            }
        });
    }

    public void showMockGameSummary() {
        historyScreen.showGameSummaries(matches);
    }
    public List<GameSummary> getMatches() {
        return matches;
    }
}
