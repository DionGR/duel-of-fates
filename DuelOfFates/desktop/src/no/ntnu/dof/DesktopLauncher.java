package no.ntnu.dof;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.desktop.FirebaseAuthImpl;
import no.ntnu.dof.desktop.FirebaseGameService;
import no.ntnu.dof.desktop.FirebaseLobbyService;
import no.ntnu.dof.desktop.FirebaseUserService;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
@Slf4j
public class DesktopLauncher {
    public static void main(String[] arg) {
        log.info("Starting Duel of Fates");
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Duel of Fates");

        // Initalize mock services to be able to test UI on desktop
        ServiceLocator.provideAuthService(new FirebaseAuthImpl());
        ServiceLocator.provideLobbyService(new FirebaseLobbyService());
        ServiceLocator.provideGameService(new FirebaseGameService());
        ServiceLocator.provideUserService(new FirebaseUserService());

        config.setWindowedMode(640, 360);
        new Lwjgl3Application(new DuelOfFates(), config);
    }
}
