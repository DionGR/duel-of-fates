package no.ntnu.dof;

public class MockAuthImpl implements AuthInterface {
    @Override
    public void signIn(String email, String password, AuthCallback callback) {
        if (email.equals("admin@gmail.com") && password.equals("admin123")) {
            callback.onSuccess();
        } else {
            callback.onError("Invalid credentials");
        }
    }
}

