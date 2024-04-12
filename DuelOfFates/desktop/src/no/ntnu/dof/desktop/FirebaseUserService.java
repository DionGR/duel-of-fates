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
        DatabaseReference newUserRef = usersReference.push();

        newUserRef.setValue(user, (databaseError, databaseReference1) -> {
            if (databaseError == null) {
                callback.onSuccess(user);
            } else {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    public void fetchUsers(UserFetchCallback callback) {
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> users = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        users.add(user);
                    }
                }
                callback.onSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    public void removeUser(String userId, UserDeletionCallback callback) {
        DatabaseReference userRef = usersReference.child(userId);
        userRef.removeValue((databaseError, databaseReference) -> {
            if (databaseError == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(databaseError.toException());
            }
        });
    }

}
