package no.ntnu.dof.controller.network;

import lombok.Getter;

public class ServiceLocator {
    @Getter
    private static AuthInterface authService;
    @Getter
    private static LobbyService lobbyService;
    @Getter
    private static GameService gameService;

    public static void provideAuthService(AuthInterface authService) {
        ServiceLocator.authService = authService;
    }

    public static void provideLobbyService(LobbyService lobbyService) {
        ServiceLocator.lobbyService = lobbyService;
    }

    public static void provideGameService(GameService gameService) {
        ServiceLocator.gameService = gameService;
    }
}

