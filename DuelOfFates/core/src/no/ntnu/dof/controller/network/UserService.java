package no.ntnu.dof.controller.network;

import java.util.ArrayList;

import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.User;

public interface UserService {

    void addUser(User user, UserCreationCallback callback);

    interface UserCreationCallback {
        void onSuccess(User user);
        void onFailure(Exception e);
    }

    interface UserDeletionCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
}
