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
    private List<GameSummary> matches = new ArrayList<>();
    private HistoryScreen historyScreen;
    private User user;

    public HistoryController(User currentUser, HistoryScreen historyScreen) {
        this.historyScreen = historyScreen;
        this.user = currentUser;
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
                if (summaries != null && !summaries.isEmpty()) {
                    matches = summaries;
                    fetchGuestUsers(0); // Start fetching guest details
                } else {
                    Gdx.app.log("History", "No game summaries found.");
                    Gdx.app.postRunnable(() -> historyScreen.showGameSummaries(new ArrayList<>()));
                }
            }

            @Override
            public void onFailure(Exception e) {
                Gdx.app.log("History", "Failed to fetch game summaries: " + e.getMessage());
                Gdx.app.postRunnable(() -> historyScreen.showGameSummaries(new ArrayList<>(matches)));
            }
        });
    }

    private void fetchGuestUsers(int index) {
        if (index < matches.size()) {
            GameSummary summary = matches.get(index);
            Gdx.app.log("History", "Fetching game summaries for user: " + user.getId());
            ServiceLocator.getUserService().fetchUserById(summary.getGuestId(), new UserService.UserCallback() {
                @Override
                public void onSuccess(User guest) {
                    summary.setUserGuest(guest);
                    summary.setUserHost(user);
                    fetchGuestUsers(index + 1); // Recursive call to fetch the next guest
                }

                @Override
                public void onFailure(Exception e) {
                    Gdx.app.log("History", "Failed to fetch guest details: " + e.getMessage());
                    fetchGuestUsers(index + 1); // Continue with next guest despite the error
                }
            });
        } else {
            Gdx.app.postRunnable(() -> historyScreen.showGameSummaries(new ArrayList<>(matches))); // Update UI only after all data is ready
        }
    }
}
