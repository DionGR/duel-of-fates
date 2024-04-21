package no.ntnu.dof.controller.lobby;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.controller.network.UserService;
import no.ntnu.dof.model.communication.GameSummary;
import no.ntnu.dof.model.communication.User;
import no.ntnu.dof.view.screen.lobby.HistoryScreen;

public class HistoryController {
    private final HistoryScreen historyScreen;
    private final User user;
    private List<GameSummary> matches = new ArrayList<>();

    public HistoryController(User currentUser, HistoryScreen historyScreen) {
        this.historyScreen = historyScreen;
        this.user = currentUser;
        fetchGameSummaries();
        historyScreen.showGameSummaries(matches); // Call the method here
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
