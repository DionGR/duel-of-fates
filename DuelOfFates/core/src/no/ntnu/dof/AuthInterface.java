package no.ntnu.dof;

public interface AuthInterface {
    void signIn(String email, String password, AuthCallback callback);
}

