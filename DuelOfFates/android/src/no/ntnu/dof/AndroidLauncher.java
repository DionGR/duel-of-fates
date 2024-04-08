package no.ntnu.dof;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.dof.android.FirebaseAuthImpl;
import no.ntnu.dof.android.FirebaseGameService;
import no.ntnu.dof.android.FirebaseLobbyService;
import no.ntnu.dof.controller.DuelOfFates;
import no.ntnu.dof.controller.network.ServiceLocator;

@Slf4j
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		log.info("Starting Duel of Fates");
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// Initialize Firebase services
		ServiceLocator.provideAuthService(new FirebaseAuthImpl());
		ServiceLocator.provideLobbyService(new FirebaseLobbyService());
		ServiceLocator.provideGameService(new FirebaseGameService());

		initialize(new DuelOfFates(), config);
	}
}
