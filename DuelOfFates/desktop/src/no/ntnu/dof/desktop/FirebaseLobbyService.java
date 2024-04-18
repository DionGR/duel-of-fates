package no.ntnu.dof.desktop;

import androidx.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.dof.controller.network.LobbyService;
import no.ntnu.dof.model.communication.GameLobby;
import no.ntnu.dof.model.communication.User;

public class FirebaseLobbyService implements LobbyService {
    private final Map<String, ValueEventListener> listeners = new HashMap<>();

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
    public void initializeGame(LobbyUpdateCallback callback, String lobbyId, String gameId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("lobbies");
        DatabaseReference lobbyRef = databaseReference.child(lobbyId);

        lobbyRef.child("gameId").setValue(gameId, (error, ref) -> {});
        lobbyRef.child("gameState").setValue("started", (error, ref) -> {
            if (error == null) callback.onSuccess();
            else callback.onFailure(error.toException());
        });
    }

    @Override
    public void listenForLobbiesChanges(LobbyChangeListener listener) {
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
                Gdx.app.error("Lobby", "The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void listenForLobbyUpdate(String lobbyId, LobbyUpdateListener listener) {
        DatabaseReference lobbyRef = FirebaseDatabase.getInstance().getReference("lobbies").child(lobbyId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GameLobby updatedLobby = dataSnapshot.getValue(GameLobby.class);
                if (updatedLobby != null) {
                    listener.onLobbyUpdated(updatedLobby);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Gdx.app.error("Lobby", "The read failed: " + databaseError.getCode());
            }
        };
        lobbyRef.addValueEventListener(valueEventListener);
        listeners.put(lobbyId, valueEventListener);
    }

    @Override
    public void stopListeningForLobbyUpdates(String lobbyId) {
        if (listeners.containsKey(lobbyId)) {
            DatabaseReference lobbyRef = FirebaseDatabase.getInstance().getReference("lobbies").child(lobbyId);
            lobbyRef.removeEventListener(listeners.get(lobbyId));
            listeners.remove(lobbyId);
        }
    }

    @Override
    public void joinLobby(LobbyJoinCallback callback, GameLobby gameLobby, User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference lobbyRef = databaseReference.child("lobbies").child(gameLobby.getLobbyId());

        // Check if lobby still exists
        lobbyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    callback.onFailure(new Exception("Lobby does not exist."));
                } else
                    lobbyRef.child("guest").setValue(user, (databaseError, databaseReference1) -> {
                        if (databaseError == null) {
                            if (callback != null) {
                                callback.onSuccess();
                            }
                        } else {
                            if (callback != null) {
                                callback.onFailure(databaseError.toException());
                            }
                        }
                    });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });

    }

    @Override
    public void deleteLobby(String lobbyId, LobbyDeletionCallback callback) {
        DatabaseReference lobbyRef = FirebaseDatabase.getInstance().getReference("lobbies").child(lobbyId);
        lobbyRef.removeValue((databaseError, databaseReference) -> {
            if (databaseError == null) {
                if (callback != null) {
                    callback.onSuccess();
                }
            } else {
                if (callback != null) {
                    callback.onFailure(databaseError.toException());
                }
            }
        });
    }

    @Override
    public void guestExitLobby(LobbyExitCallback callback, GameLobby gameLobby) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference lobbyRef = databaseReference.child("lobbies").child(gameLobby.getLobbyId());

        lobbyRef.child("guest").removeValue((databaseError, databaseReference1) -> {
            if (databaseError == null) {
                if (callback != null) {
                    callback.onSuccess();
                }
            } else {
                if (callback != null) {
                    callback.onFailure(databaseError.toException());
                }
            }
        });
    }

}
