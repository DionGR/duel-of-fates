package no.ntnu.dof.controller.network;

import no.ntnu.dof.model.User;

public interface AuthInterface {
    void signIn(String email, String password, AuthCallback callback);

    void signUp(String email, String password, AuthCallback callback);
    void signOut();
    User createGameUserFromFirebaseUser();
}

