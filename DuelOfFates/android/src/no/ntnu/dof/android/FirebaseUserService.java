package no.ntnu.dof.android;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.firebase.database.ValueEventListener;

import no.ntnu.dof.controller.network.UserService;
import no.ntnu.dof.model.GameSummary;
import no.ntnu.dof.model.User;

public class FirebaseUserService implements UserService {
    private final DatabaseReference usersReference;

    public FirebaseUserService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("users");
    }

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
            public void onDataChange(DataSnapshot dataSnapshot) {
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
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }
}