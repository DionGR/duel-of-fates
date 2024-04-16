package no.ntnu.dof.controller.network;

import no.ntnu.dof.model.User;

public interface AuthService {
    void signIn(String email, String password, AuthCallback callback);

    void signUp(String email, String password, AuthCallback callback);
    void signOut();
    User createGameUserFromFirebaseUser();

    interface AuthCallback {
        void onSuccess(); // Called when authentication is successful
        void onError(String message); // Called when authentication fails
    }
}

