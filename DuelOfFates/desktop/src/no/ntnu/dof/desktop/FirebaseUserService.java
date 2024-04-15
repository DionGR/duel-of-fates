package no.ntnu.dof.desktop;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.firebase.database.ValueEventListener;

import no.ntnu.dof.controller.network.UserService;
import no.ntnu.dof.model.User;

public class FirebaseUserService implements UserService {
    private final DatabaseReference usersReference;

    public FirebaseUserService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("users");
    }

    public void addUser(User user, UserCreationCallback callback) {
        String userId = user.getId(); // Assuming User class has a getId() method that returns a String.
        DatabaseReference newUserRef = usersReference.child(userId);

        newUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // No existing data found with the same ID, safe to add new user.
                    newUserRef.setValue(user, (databaseError, databaseReference) -> {
                        if (databaseError == null) {
                            callback.onSuccess(user);
                        } else {
                            callback.onFailure(databaseError.toException());
                        }
                    });
                } else {
                    // Handle the case where a user with the same ID already exists
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
