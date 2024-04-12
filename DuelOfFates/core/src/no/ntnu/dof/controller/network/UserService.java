package no.ntnu.dof.controller.network;

import java.util.ArrayList;

import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.User;

public interface UserService {


    interface UserCreationCallback {
        void onSuccess(User user);
        void onFailure(Exception e);
    }

    interface UserFetchCallback {
        void onSuccess(ArrayList<User> users);
        void onFailure(Exception e);
    }

    interface UserDeletionCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
}
