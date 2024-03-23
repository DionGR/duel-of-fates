package no.ntnu.dof;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import no.ntnu.dof.controller.DuelOfFates;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Duel of Fates");
		config.setWindowedMode(640,360);
		new Lwjgl3Application(new DuelOfFates(new MockAuthImpl()), config);
	}
}
