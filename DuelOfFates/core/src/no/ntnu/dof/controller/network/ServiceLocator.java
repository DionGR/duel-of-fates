package no.ntnu.dof.controller.network;

import lombok.Getter;

public class ServiceLocator {
    @Getter
    private static AuthInterface authService;
    @Getter
    private static LobbyService lobbyService;
    // Other services can be added here

    public static void provideAuthService(AuthInterface authService) {
        ServiceLocator.authService = authService;
    }

    public static void provideLobbyService(LobbyService lobbyService) {
        ServiceLocator.lobbyService = lobbyService;
    }

    // Methods for other services go here
}

