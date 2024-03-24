package no.ntnu.dof;

import no.ntnu.dof.controller.network.LobbyService;

public class MockLobbyService implements LobbyService {
    @Override
    public void createLobby(LobbyCreationCallback callback) {
        // Simulate lobby creation with a mock ID
        String mockLobbyId = "mockLobby123";
        callback.onSuccess(mockLobbyId);
        // Or call callback.onFailure(new Exception("Error message")) to simulate a failure
    }
}
