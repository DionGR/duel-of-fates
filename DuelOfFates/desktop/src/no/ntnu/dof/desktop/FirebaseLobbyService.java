package no.ntnu.dof.desktop;

import androidx.annotation.NonNull;

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
    public void updateLobbyState(LobbyUpdateCallback callback, String lobbyId, String state) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("lobbies");
        DatabaseReference lobbyRef = databaseReference.child(lobbyId).child("gameState");

        lobbyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().setValue(state, (databaseError, databaseReference) -> {
                    if (databaseError == null) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(databaseError.toException());
                    }
                });
                callback.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Data could not be saved " + databaseError.getMessage());
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

    @Override
    public void listenForGameStart(GameStartListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("lobbies");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot lobbySnapshot: dataSnapshot.getChildren()) {
                    GameLobby lobby = lobbySnapshot.getValue(GameLobby.class);
                    if (lobby != null && lobby.getGameState() != null && lobby.getGameState().equals("started")) {
                        listener.onGameStart(lobby);
                    }
                }
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

        // Check if lobby still exists
        lobbyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    callback.onFailure(new Exception("Lobby does not exist."));
                } else
                    lobbyRef.child("guest").setValue(user, (DatabaseReference.CompletionListener) (databaseError, databaseReference1) -> {
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
