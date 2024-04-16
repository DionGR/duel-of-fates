package no.ntnu.dof.android;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Optional;

import no.ntnu.dof.android.listener.ChildAdditionListener;
import no.ntnu.dof.android.listener.ValueChangeListener;
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
            .addChildEventListener(new ChildAdditionListener(
                snapshot -> listener.onCardPlayed(snapshot.getValue(Card.class))
            ));
        databaseReference.child("games").child(comms.getGameId()).child("playerLastTurn")
            .addValueEventListener(new ValueChangeListener(
                snapshot -> listener.onTurnEnd(snapshot.getValue(String.class))
            ));
        databaseReference.child("games").child(comms.getGameId()).child("abort")
            .addValueEventListener(new ValueChangeListener(
                snapshot -> {
                    if (Boolean.TRUE.equals(snapshot.getValue(Boolean.class))) listener.onAbort();
                }
            ));
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

    @Override
    public void abort(GameComms comms) {
        FirebaseDatabase.getInstance().getReference().child("games")
            .child(comms.getGameId()).child("abort").setValue(true);
        // TODO implement callbacks if necessary
    }
}
