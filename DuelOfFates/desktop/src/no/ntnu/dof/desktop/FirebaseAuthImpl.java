package no.ntnu.dof.desktop;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.FileInputStream;
import java.io.IOException;

import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.AuthInterface;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.controller.network.UserService;
import no.ntnu.dof.model.User;

public class FirebaseAuthImpl implements AuthInterface {
    private boolean appInitialized = false;
    private UserRecord currentUser = null;

    @Override
    public synchronized void signIn(String email, String password, AuthCallback callback) {
        if (!appInitialized) {
            FirebaseOptions.Builder options = FirebaseOptions.builder();
            try {
                FileInputStream serviceAccount = new FileInputStream("desktop/firebase-cred.json");
                options.setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://duel-of-fates-default-rtdb.europe-west1.firebasedatabase.app");
            } catch (IOException e) {
                callback.onError("Credentials unavailable");
            }
            FirebaseApp.initializeApp(options.build());
            appInitialized = true;
        }

        try {
            currentUser = FirebaseAuth.getInstance().getUserByEmail(email);
            callback.onSuccess();
        } catch (FirebaseAuthException e) {
            callback.onError("");
        }
    }

    @Override
    public void signUp(String email, String password, AuthCallback callback) {
        try {
            currentUser = FirebaseAuth.getInstance().createUser(new UserRecord.CreateRequest().setEmail(email).setPassword(password));
            ServiceLocator.getUserService().addUser(new User(currentUser.getUid(), currentUser.getEmail()), new UserService.UserCreationCallback() {
                @Override
                public void onSuccess(User user) {
                    callback.onSuccess();
                }

                @Override
                public void onFailure(Exception e) {
                    callback.onError(e.getMessage());
                    currentUser = null;
                }
            });
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void signOut() {
        FirebaseApp.getInstance().delete();
        appInitialized = false;
    }

    @Override
    public User createGameUserFromFirebaseUser() {
        if (currentUser == null) return null;

        String userId = currentUser.getUid();
        String userMail = currentUser.getEmail();
        return new User(userId, userMail);
    }
}

