package no.ntnu.dof.controller.menu;

import com.badlogic.gdx.Gdx;

import no.ntnu.dof.DuelOfFates;
import no.ntnu.dof.controller.application.ScreenController;
import no.ntnu.dof.controller.network.AuthService;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.controller.network.UserService;
import no.ntnu.dof.model.communication.User;
import no.ntnu.dof.view.screen.menu.LoginScreen;

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

            ServiceLocator.getAuthService().signIn(email, password, new AuthService.AuthCallback() {
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

    @Override
    public void onSignUpAttempt(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            ServiceLocator.getAuthService().signUp(email, password, new AuthService.AuthCallback() {
                @Override
                public void onSuccess() {
                    Gdx.app.postRunnable(() -> loginSuccess());
                }

                @Override
                public void onError(String message) {
                    Gdx.app.postRunnable(() -> loginScreen.showLoginFailed("Sign up failed: " + message));
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
        User user = ServiceLocator.getAuthService().createGameUserFromFirebaseUser();
        ServiceLocator.getUserService().fetchUserById(user.getId(), new UserService.UserCallback() {
            @Override
            public void onSuccess(User updatedUser) {
                application.setCurrentUser(updatedUser);
                Gdx.app.log("Login", "Fetched user data.");
            }

            @Override
            public void onFailure(Exception e) {
                application.setCurrentUser(user); // use user with default player class
                Gdx.app.log("Login", "User info could not be fetched, creating user with default class.");
            }
        });

        // Navigate to the menu screen
        Gdx.app.postRunnable(ScreenController::transitionToMenu);
    }
}
