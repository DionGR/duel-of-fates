package no.ntnu.dof.desktop;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;

public class FirebaseLobbyService implements LobbyService {
    @Override
    public void createLobby(LobbyCreationCallback callback, GameLobby lobby) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference newLobbyRef = databaseReference.child("lobbies").push(); // This generates a new unique key

        // Retrieve the unique key and set it as the lobbyId
        String lobbyId = newLobbyRef.getKey();
        lobby.setLobbyId(lobbyId);

        // Upload the complete lobby object including the lobbyId
        newLobbyRef.setValue(lobby, (databaseError, databaseReference1) -> {
            if (databaseError == null) {
                callback.onSuccess(lobby);
            } else {
                callback.onFailure(databaseError.toException());
            }
        });
    }
}
