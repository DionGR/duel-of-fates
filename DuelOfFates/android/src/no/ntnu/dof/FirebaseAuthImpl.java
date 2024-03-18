package no.ntnu.dof;

import com.badlogic.gdx.Gdx;
import com.google.firebase.auth.FirebaseAuth;

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

