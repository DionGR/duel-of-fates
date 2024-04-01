package no.ntnu.dof.android;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.model.GameLobby;
import no.ntnu.dof.model.User;

public class FirebaseLobbyService implements LobbyService {

    @Override
    public void createLobby(LobbyCreationCallback callback, User user) {
        // FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        // DatabaseReference databaseReference = database.getReferenceFromUrl("https://duel-of-fates-default-rtdb.europe-west1.firebasedatabase.app/");
        String lobbyId = databaseReference.child("lobbies").push().getKey();
        GameLobby lobby = new GameLobby(lobbyId, user, "waiting");
        databaseReference.child("lobbies").child(lobbyId).setValue(lobby)
                .addOnSuccessListener(aVoid -> callback.onSuccess(lobbyId))
                .addOnFailureListener(callback::onFailure);
    }
}
