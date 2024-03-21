package no.ntnu.dof.controller.network;

public interface AuthCallback {
    void onSuccess(); // Called when authentication is successful
    void onError(String message); // Called when authentication fails
}

