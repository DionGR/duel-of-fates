package no.ntnu.dof.android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.gdx.Gdx;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Optional;

import no.ntnu.dof.controller.network.GameService;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;

public class FirebaseGameService implements GameService {
    @Override
    public GameComms createComms(String startingPlayerName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String gameId = databaseReference.child("games").push().getKey();
        GameComms comms = new GameComms(gameId, startingPlayerName);
        databaseReference.child("games").child(gameId).setValue(comms);

        return comms;
    }

    @Override
    public void getComms(@NonNull String gameId, GetCommsCallback callback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference gameRef = databaseReference.child("games").child(gameId);

        gameRef.get()
            .addOnSuccessListener(dataSnapshot -> callback.onSuccess(dataSnapshot.getValue(GameComms.class)))
            .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void addPlayListener(GameComms comms, PlayListener listener) {
        FirebaseChangeAdapter firebaseChangeAdapter = new FirebaseChangeAdapter(listener);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("games").child(comms.getGameId()).child("cards")
                .addChildEventListener(firebaseChangeAdapter);
        databaseReference.child("games").child(comms.getGameId()).child("playerLastTurn")
                .addValueEventListener(firebaseChangeAdapter);
    }

    @Override
    public void playCard(GameComms comms, Optional<Card> card) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if (card.isPresent()) {
            String cardId = databaseReference.child("games").child(comms.getGameId())
                    .child("cards").push().getKey();
            databaseReference.child("games").child(comms.getGameId())
                    .child("cards").child(cardId).setValue(card.get());
            // TODO implement callbacks if necessary
//                    .addOnSuccessListener(aVoid -> callback.onSuccess(lobbyId))
//                    .addOnFailureListener(callback::onFailure);
        } else {
            databaseReference.child("games").child(comms.getGameId())
                    .child("playerLastTurn").setValue(comms.getPlayerLastTurn());
            // TODO implement callbacks if necessary
//                    .addOnSuccessListener(aVoid -> callback.onSuccess(lobbyId))
//                    .addOnFailureListener(callback::onFailure);
        }
    }

    public static class FirebaseChangeAdapter implements ValueEventListener, ChildEventListener {
        private final PlayListener listener;

        public FirebaseChangeAdapter(PlayListener listener) {
            this.listener = listener;
        }

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            listener.onTurnEnd(snapshot.getValue(String.class));
        }

        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            listener.onCardPlayed(snapshot.getValue(Card.class));
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Gdx.app.log("DatabaseChange", "Database update cancelled.");
        }
    }
}
