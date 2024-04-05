package no.ntnu.dof.android;

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

        // Set lobbyId to the new unique key
        String lobbyId = newLobbyRef.getKey();
        lobby.setLobbyId(lobbyId);

        // Save the lobby object, now including the lobbyId, to Firebase
        newLobbyRef.setValue(lobby)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(lobby);
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                });
    }

}
