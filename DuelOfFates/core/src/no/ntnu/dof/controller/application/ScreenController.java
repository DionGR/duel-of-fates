package no.ntnu.dof.controller.application;

import com.badlogic.gdx.Screen;

import java.util.Stack;

import no.ntnu.dof.DuelOfFates;
import no.ntnu.dof.controller.gameplay.GameController;
import no.ntnu.dof.controller.gameplay.TutorialController;
import no.ntnu.dof.controller.lobby.GameLobbiesController;
import no.ntnu.dof.controller.lobby.GameLobbyController;
import no.ntnu.dof.controller.lobby.HistoryController;
import no.ntnu.dof.controller.menu.LoginController;
import no.ntnu.dof.model.communication.GameComms;
import no.ntnu.dof.model.communication.GameLobby;
import no.ntnu.dof.model.communication.User;
import no.ntnu.dof.model.gameplay.event.GameEndListener;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.screen.game.GameScreen;
import no.ntnu.dof.view.screen.lobby.HistoryScreen;
import no.ntnu.dof.view.screen.lobby.LobbiesScreen;
import no.ntnu.dof.view.screen.lobby.LobbyScreen;
import no.ntnu.dof.view.screen.menu.ChooseClassScreen;
import no.ntnu.dof.view.screen.menu.LoginScreen;
import no.ntnu.dof.view.screen.menu.MenuScreen;

public class ScreenController {
    private static final Stack<Screen> screens = new Stack<>();
    private static DuelOfFates application;

    public static void initialize(DuelOfFates application) {
        ScreenController.application = application;
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
        pushScreen(new MenuScreen());
        SoundController.getInstance().start();
    }

    public static void transitionToLobbies() {
        LobbiesScreen lobbiesScreen = new LobbiesScreen();
        new GameLobbiesController(application.getCurrentUser(), lobbiesScreen);
        pushScreen(lobbiesScreen);
    }

    public static void transitionToLobby(GameLobby gameLobby) {
        User currentUser = application.getCurrentUser();
        // The LobbyScreen needs the currentUser and gameLobby in order to load the correct UI to begin with
        LobbyScreen lobbyScreen = new LobbyScreen(currentUser, gameLobby);
        new GameLobbyController(currentUser, lobbyScreen, gameLobby);
        pushScreen(lobbyScreen);
    }

    public static void transitionToChooseClass() {
        pushScreen(new ChooseClassScreen(application.getCurrentUser()));
    }

    public static void transitionToLogin() {
        LoginScreen loginScreen = new LoginScreen();
        new LoginController(application, loginScreen);
        pushScreen(loginScreen);
    }

    public static void transitionToLoginWhenLoggedIn() {
        popScreen();
        SoundController.getInstance().stop();
        LoginScreen loginScreen = (LoginScreen) screens.peek();
        loginScreen.initializeUI();
    }

    public static void transitionToHistory() {
        HistoryScreen historyScreen = new HistoryScreen();
        pushScreen(historyScreen);
        new HistoryController(application.getCurrentUser(), historyScreen);
    }

    public static void transitionToGame(Player host, Player guest, GameComms comms, GameEndListener gameEndListener) {
        popScreen();
        GameController gameController = new GameController(host, guest, comms);
        GameScreen gameScreen = new GameScreen(gameController.getGame());
        gameController.startGame(gameEndListener);
        pushScreen(gameScreen);
    }

    public static void transitionToTutorial() {
        TutorialController tutorialController = new TutorialController();
        pushScreen(tutorialController.getScreen());
        tutorialController.startGame();
    }

    public static void dispose() {
        screens.forEach(Screen::dispose);
    }
}

