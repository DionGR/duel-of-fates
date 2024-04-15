package no.ntnu.dof.controller.network;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.GameSummary;
import no.ntnu.dof.model.User;

public interface UserService {

    void addUser(User user, UserCreationCallback callback);

    //void uploadGameSummary(User host, User guest, GameSummaryCallback);

    //List<GameSummary> fetchUserGameSummaries(String userId);
    interface UserCreationCallback {
        void onSuccess(User user);
        void onFailure(Exception e);
    }

//    interface GameSummaryCallback {
//        void onSuccess(GameSummary gameSummary);
//        void onFailure(Exception e);
//    }

    interface UserDeletionCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
}
