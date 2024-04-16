package no.ntnu.dof.controller.network;

import lombok.Getter;

public class ServiceLocator {
    @Getter
    private static AuthService authService;
    @Getter
    private static LobbyService lobbyService;
    @Getter
    private static GameService gameService;
    @Getter
    private static UserService userService;

    public static void provideAuthService(AuthService authService) {
        ServiceLocator.authService = authService;
    }

    public static void provideLobbyService(LobbyService lobbyService) {
        ServiceLocator.lobbyService = lobbyService;
    }

    public static void provideGameService(GameService gameService) {
        ServiceLocator.gameService = gameService;
    }

    public static void provideUserService(UserService userService) {
        ServiceLocator.userService = userService;
    }
}

