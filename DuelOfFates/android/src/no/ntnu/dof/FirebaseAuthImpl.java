package no.ntnu.dof;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.AuthInterface;
import no.ntnu.dof.model.User;

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

    @Override
    public User createGameUserFromFirebaseUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Get the unique user ID from the FirebaseUser object
            String userId = user.getUid();
            String userMail = user.getEmail();
            return new User(userId, userMail);
        } else {
            return null;
        }
    }
}

