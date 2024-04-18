package no.ntnu.dof.android;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import no.ntnu.dof.controller.network.AuthCallback;
import no.ntnu.dof.controller.network.AuthInterface;
import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.controller.network.UserService;
import no.ntnu.dof.model.User;

public class FirebaseAuthImpl implements AuthInterface {

    @Override
    public void signIn(String email, String password, AuthCallback callback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onError(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void signUp(String email, String password, AuthCallback callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (firebaseUser != null) {
                            User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());
                            try {
                                UserService userService = ServiceLocator.getUserService();
                                if (userService != null) {
                                    userService.addUser(user, new UserService.UserCreationCallback() {
                                        @Override
                                        public void onSuccess(User user) {
                                            callback.onSuccess();
                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            callback.onError(e.getMessage());
                                        }
                                    });
                                } else {
                                    callback.onError("UserService is not available");
                                }
                            } catch (Exception e) {
                                callback.onError("An error occurred while registering the user: " + e.getMessage());
                            }
                        } else {
                            callback.onError("Failed to retrieve Firebase user data.");
                        }
                    } else {
                        callback.onError(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public User createGameUserFromFirebaseUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Get the unique user ID from the FirebaseUser object
            String userId = user.getUid();
            String userMail = user.getEmail();
            return new User(userId, userMail);
        } else {
            return null;
        }
    }
}
