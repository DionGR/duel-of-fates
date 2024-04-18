package no.ntnu.dof;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import no.ntnu.dof.android.FirebaseAuthService;
import no.ntnu.dof.android.FirebaseGameService;
import no.ntnu.dof.android.FirebaseLobbyService;
import no.ntnu.dof.android.FirebaseUserService;
import no.ntnu.dof.controller.network.ServiceLocator;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// Initialize Firebase services
		ServiceLocator.provideAuthService(new FirebaseAuthService());
		ServiceLocator.provideLobbyService(new FirebaseLobbyService());
		ServiceLocator.provideGameService(new FirebaseGameService());
		ServiceLocator.provideUserService(new FirebaseUserService());

		initialize(new DuelOfFates(), config);
	}
}
