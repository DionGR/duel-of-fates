package no.ntnu.dof.desktop;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import com.google.firebase.database.ValueEventListener;

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

    @Override
    public void listenForLobbyChanges(LobbyChangeListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("lobbies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<GameLobby> updatedLobbies = new ArrayList<>();
                for (DataSnapshot lobbySnapshot: dataSnapshot.getChildren()) {
                    GameLobby lobby = lobbySnapshot.getValue(GameLobby.class);
                    if (lobby != null) {
                        updatedLobbies.add(lobby);
                    }
                }
                listener.onLobbiesUpdated(updatedLobbies);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
