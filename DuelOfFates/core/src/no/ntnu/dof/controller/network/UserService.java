package no.ntnu.dof.controller.network;

import java.util.List;

import no.ntnu.dof.model.communication.GameSummary;
import no.ntnu.dof.model.communication.User;

public interface UserService {

    void addUser(User user, UserCreationCallback callback);

    void uploadGameSummary(String userId, GameSummary summary, GameSummaryCallback callback);

    void fetchUserGameSummaries(String userId, GameSummariesCallback callback);

    void fetchUserById(String userId, UserCallback callback);

    interface UserCreationCallback {
        void onSuccess(User user);
        void onFailure(Exception e);
    }

    interface GameSummaryCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    interface GameSummariesCallback {
        void onSuccess(List<GameSummary> summaries);
        void onFailure(Exception e);
    }

    interface UserCallback {
        void onSuccess(User user);
        void onFailure(Exception e);
    }
}
