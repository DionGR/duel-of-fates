package no.ntnu.dof.controller.menu;

import com.badlogic.gdx.Gdx;

import no.ntnu.dof.controller.ScreenController;
import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.view.screens.menu.LoginScreen;
import no.ntnu.dof.controller.DuelOfFates;

public class LoginController implements LoginScreen.LoginViewListener {
    private final DuelOfFates application;
    private final LoginScreen loginScreen;

    public LoginController(DuelOfFates application, LoginScreen loginScreen) {
        this.application = application;
        this.loginScreen = loginScreen;
        this.loginScreen.setLoginViewListener(this);
    }

    @Override
    public void onLoginAttempt(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            if (application.getCurrentUser() != null) {
                initiateLogout();
            }

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

    public void initiateLogout() {
        this.application.setCurrentUser(null);
        ServiceLocator.getAuthService().signOut();
    }

    public void loginSuccess() {
        application.setCurrentUser(ServiceLocator.getAuthService().createGameUserFromFirebaseUser());

        // Navigate to the menu screen
        Gdx.app.postRunnable(ScreenController::transitionToMenu);
    }
}
