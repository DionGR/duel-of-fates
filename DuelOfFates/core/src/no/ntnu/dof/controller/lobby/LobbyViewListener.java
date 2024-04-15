package no.ntnu.dof.controller.lobby;

public interface LobbyViewListener {
    void startGame();
    void joinLobby();
    void deleteLobby(boolean withPop);
    void exitLobby();
}
