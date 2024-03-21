package no.ntnu.dof;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import no.ntnu.dof.Game;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Duel of Fates");
		config.height = 360;
		config.width = 640;
		initialize(new Game(), config);
	}
}
