package no.ntnu.dof.controller;

import com.badlogic.gdx.Gdx;

import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.User;
import no.ntnu.dof.view.screens.LoginScreen;

public class LoginController implements LoginScreen.LoginViewListener {
    private final DuelOfFates game;
    private final LoginScreen loginScreen;

    public LoginController(DuelOfFates game, LoginScreen loginScreen) {
        this.game = game;
        this.loginScreen = loginScreen;
        this.loginScreen.setLoginViewListener(this);
    }

    @Override
    public void onLoginAttempt(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            ServiceLocator.getAuthService().signIn(email, password, new AuthCallback() {
                @Override
                public void onSuccess() {
                    Gdx.app.postRunnable(() -> loginSuccess());
                }

                @Override
                public void onError(String message) {
                    Gdx.app.postRunnable(() -> loginScreen.showLoginFailed(message));
                }
            });
        } else {
            Gdx.app.postRunnable(() -> loginScreen.showLoginFailed("Email and password cannot be empty."));
        }
    }

    public void loginSuccess() {
        game.setCurrentUser(ServiceLocator.getAuthService().createGameUserFromFirebaseUser());

        // Navigate to the menu screen
        Gdx.app.postRunnable(() -> {
            ScreenManager.transitionToMenu();
        });
    }
}
