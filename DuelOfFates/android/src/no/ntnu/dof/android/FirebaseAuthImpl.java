package no.ntnu.dof.android;

import com.google.firebase.auth.FirebaseAuth;
import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.AuthInterface;

public class FirebaseAuthImpl implements AuthInterface {
    @Override
    public void signIn(String email, String password, AuthCallback callback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onError(task.getException().getMessage());
                    }
                });
    }
}

