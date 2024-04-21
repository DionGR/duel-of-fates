package no.ntnu.dof.android;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.dof.controller.network.UserService;
import no.ntnu.dof.model.communication.GameSummary;
import no.ntnu.dof.model.communication.User;

public class FirebaseUserService implements UserService {
    private final DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    public void uploadGameSummary(String userId, GameSummary summary, GameSummaryCallback callback) {
        DatabaseReference gameHistoryRef = usersReference.child(userId).child("gameshistory");
        String key = gameHistoryRef.push().getKey(); // Generate a unique key for the new game summary
        gameHistoryRef.child(key).setValue(summary, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    @Override
    public void addUser(User user, UserCreationCallback callback) {
        String userId = user.getId();
        DatabaseReference newUserRef = usersReference.child(userId);

        newUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Add new user if not user exists from before
                    newUserRef.setValue(user, (databaseError, databaseReference) -> {
                        if (databaseError == null) {
                            callback.onSuccess(user);
                        } else {
                            callback.onFailure(databaseError.toException());
                        }
                    });
                } else {
                    callback.onFailure(new IllegalStateException("A user with this ID already exists."));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    @Override
    public void fetchUserGameSummaries(String userId, GameSummariesCallback callback) {
        DatabaseReference gameHistoryRef = usersReference.child(userId).child("gameshistory");
        gameHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<GameSummary> summaries = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GameSummary summary = snapshot.getValue(GameSummary.class);
                    summaries.add(summary);
                }
                callback.onSuccess(summaries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    @Override
    public void fetchUserById(String userId, UserCallback callback) {
        DatabaseReference userRef = usersReference.child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    callback.onSuccess(user);
                } else {
                    callback.onFailure(new Exception("User not found"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    @Override
    public void updateUserClass(User user, UserUpdateCallback callback) {
        usersReference.child(user.getId()).child("playerClassName").setValue(user.getPlayerClassName())
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }
}