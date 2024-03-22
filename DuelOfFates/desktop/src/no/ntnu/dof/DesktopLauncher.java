package no.ntnu.dof;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.network.ServiceLocator;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Duel of Fates");

		// Initalize mock services to be able to test UI on desktop
		ServiceLocator.provideAuthService(new MockAuthImpl());
		ServiceLocator.provideLobbyService(new MockLobbyService());

		new Lwjgl3Application(new DuelOfFates(), config);
	}
}
