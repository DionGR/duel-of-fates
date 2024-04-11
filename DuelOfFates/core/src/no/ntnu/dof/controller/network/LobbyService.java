package no.ntnu.dof.controller.network;

import java.util.List;

import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;

public interface LobbyService {
    void createLobby(LobbyCreationCallback callback, GameLobby gameLobby);

    void listenForLobbiesChanges(LobbyChangeListener listener);

    void listenForLobbyUpdate(String lobbyId, LobbyUpdateListener listener);

    void stopListeningForLobbyUpdates(String lobbyId);
    void updateLobbyState(LobbyUpdateCallback callback, String lobbyId, String state);

    void listenForLobbyChanges(LobbyChangeListener listener);

    void listenForGameStart(GameStartListener listener);

    void joinLobby(LobbyJoinCallback callback, GameLobby gameLobby, User user);

    void deleteLobby(String lobbyId, LobbyDeletionCallback callback);

    void guestExitLobby(LobbyExitCallback callback, GameLobby gameLobby);

    interface LobbyCreationCallback {
        void onSuccess(GameLobby gameLobby);
        void onFailure(Throwable throwable);
    }

    interface GameStartListener {
        void onGameStart(GameLobby gameLobby);
    }

    interface LobbyChangeListener {
        void onLobbiesUpdated(List<GameLobby> lobbies);
    }

    interface LobbyUpdateListener {
        void onLobbyUpdated(GameLobby updatedLobby);
    }

    interface LobbyJoinCallback {
        void onSuccess();
        void onFailure(Throwable throwable);
    }

    interface LobbyExitCallback {
        void onSuccess();
        void onFailure(Throwable throwable);
    }

    interface LobbyDeletionCallback {
        void onSuccess();
        void onFailure(Throwable throwable);
    }

    public interface LobbyUpdateCallback {
        void onSuccess();
        void onFailure(Throwable throwable);
    }
}
