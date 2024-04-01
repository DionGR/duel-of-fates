package no.ntnu.dof.desktop;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.AuthInterface;

public class FirebaseAuthImpl implements AuthInterface {
    private boolean appInitialized = false;

    @Override
    public synchronized void signIn(String email, String password, AuthCallback callback) {
        if (appInitialized) return;

        FirebaseOptions.Builder options = FirebaseOptions.builder();
        System.out.println(System.getProperty("user.dir"));
        try {
            FileInputStream serviceAccount = new FileInputStream("desktop/firebase-cred.json");
            options.setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://duel-of-fates-default-rtdb.europe-west1.firebasedatabase.app");
        } catch (IOException e) {
            callback.onError("Credentials unavailable");
        }

        FirebaseApp.initializeApp(options.build());
        appInitialized = true;
        callback.onSuccess();
    }
}

