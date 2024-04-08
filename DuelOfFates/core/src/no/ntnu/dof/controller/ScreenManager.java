package no.ntnu.dof.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.view.screens.ChooseClassScreen;
import no.ntnu.dof.view.screens.LoginScreen;
import no.ntnu.dof.view.screens.MenuScreen;
import no.ntnu.dof.view.screens.LobbiesScreen;
import no.ntnu.dof.view.screens.LobbyScreen;

public class ScreenManager {
    private static final Stack<Screen> screens = new Stack<>();
    private static DuelOfFates game;
    private static SpriteBatch batch;
    private static AssetManager assetManager;

    public static void initialize(DuelOfFates gameInstance, SpriteBatch spriteBatch, AssetManager assetMgr) {
        game = gameInstance;
        batch = spriteBatch;
        assetManager = assetMgr;
    }

    public static void pushScreen(Screen screen) {
        screens.push(screen);
        game.setScreen(screen);
    }

    public static void popScreen() {
        if (screens.size() > 1) {
            screens.pop().dispose();
            game.setScreen(screens.peek());
        }
    }

    // Transition methods
    public static void transitionToMenu() {
        pushScreen(new MenuScreen(game, batch, assetManager));
    }

    public static void transitionToLobbies() {
        pushScreen(new LobbiesScreen(game, batch, assetManager));
    }

    public static void transitionToLobby(GameLobby gameLobby) {
        pushScreen(new LobbyScreen(game, gameLobby));
    }

    public static void transitionToChooseClass() {
        pushScreen(new ChooseClassScreen(game));
    }

    public static void transitionToLogin() {
        LoginScreen loginScreen = new LoginScreen(batch, assetManager);
        new LoginController(game, loginScreen);
        pushScreen(loginScreen);
    }
}

