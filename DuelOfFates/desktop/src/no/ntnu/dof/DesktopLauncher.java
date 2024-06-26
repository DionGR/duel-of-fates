package no.ntnu.dof;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.desktop.FirebaseAuthService;
import no.ntnu.dof.desktop.FirebaseGameService;
import no.ntnu.dof.desktop.FirebaseLobbyService;
import no.ntnu.dof.desktop.FirebaseUserService;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Duel of Fates");

        // Initialize mock services to be able to test UI on desktop
        ServiceLocator.provideAuthService(new FirebaseAuthService());
        ServiceLocator.provideLobbyService(new FirebaseLobbyService());
        ServiceLocator.provideGameService(new FirebaseGameService());
        ServiceLocator.provideUserService(new FirebaseUserService());

        config.setWindowedMode(640, 360);
        new Lwjgl3Application(new DuelOfFates(), config);
    }
}
