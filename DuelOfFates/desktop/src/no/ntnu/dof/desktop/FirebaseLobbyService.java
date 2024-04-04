package no.ntnu.dof.desktop;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;

public class FirebaseLobbyService implements LobbyService {
    @Override
    public void createLobby(LobbyCreationCallback callback, User user, String title) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String lobbyId = databaseReference.child("lobbies").push().getKey();
        GameLobby lobby = new GameLobby(lobbyId, user, title);
        databaseReference.child("lobbies").child(lobbyId).setValue(lobby, (error, ref) -> {
            if (error == null) callback.onSuccess(lobbyId);
            else callback.onFailure(error.toException());
        });
    }
}
