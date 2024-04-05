package no.ntnu.dof.android;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void listenForLobbyChanges(LobbyChangeListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("lobbies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<GameLobby> updatedLobbies = new ArrayList<>();
                for (DataSnapshot lobbySnapshot: dataSnapshot.getChildren()) {
                    GameLobby lobby = lobbySnapshot.getValue(GameLobby.class);
                    updatedLobbies.add(lobby);
                }
                listener.onLobbiesUpdated(updatedLobbies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void joinLobby(LobbyJoinCallback callback, GameLobby gameLobby, User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference lobbyRef = databaseReference.child("lobbies").child(gameLobby.getLobbyId());

        lobbyRef.child("guest").setValue(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

}
