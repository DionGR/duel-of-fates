package no.ntnu.dof.controller.network;

public interface AuthInterface {
    void signIn(String email, String password, AuthCallback callback);
}

