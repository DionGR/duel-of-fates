package no.ntnu.dof.controller;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.view.screens.lobby.HistoryScreen;
import no.ntnu.dof.view.screens.menu.ChooseClassScreen;
import no.ntnu.dof.view.screens.menu.LoginScreen;
import no.ntnu.dof.view.screens.menu.MenuScreen;
import no.ntnu.dof.view.screens.lobby.LobbiesScreen;
import no.ntnu.dof.view.screens.lobby.LobbyScreen;

public class ScreenController {
    private static final Stack<Screen> screens = new Stack<>();
    private static DuelOfFates application;
    private static SpriteBatch batch;
    private static AssetManager assetManager;

    public static void initialize(DuelOfFates application, SpriteBatch spriteBatch, AssetManager assetMgr) {
        ScreenController.application = application;
        batch = spriteBatch;
        assetManager = assetMgr;
    }

    public static void pushScreen(Screen screen) {
        screens.push(screen);
        application.setScreen(screen);
    }

    public static void popScreen() {
        if (screens.size() > 1) {
            screens.pop().dispose();
            application.setScreen(screens.peek());
        }
    }

    public static void transitionToMenu() {
        pushScreen(new MenuScreen(application, batch, assetManager));
    }

    public static void transitionToLobbies() {
        LobbiesScreen lobbiesScreen = new LobbiesScreen(application);
        new GameLobbiesController(application, lobbiesScreen);
        pushScreen(lobbiesScreen);
    }

    public static void transitionToLobby(GameLobby gameLobby) {
        LobbyScreen lobbyScreen = new LobbyScreen(application, gameLobby);
        new GameLobbyController(application, lobbyScreen, gameLobby);
        pushScreen(lobbyScreen);
    }

    public static void transitionToChooseClass() {
        pushScreen(new ChooseClassScreen(application));
    }

    public static void transitionToLogin() {
        LoginScreen loginScreen = new LoginScreen(batch, assetManager);
        new LoginController(application, loginScreen);
        pushScreen(loginScreen);
    }

    public static void transitionToLoginWhenLoggedIn() {
        popScreen();
        LoginScreen loginScreen = (LoginScreen) screens.peek();
        loginScreen.initializeUI();
    }

    public static void transitionToHistory() {
        pushScreen(new HistoryScreen(application));
    }
}

