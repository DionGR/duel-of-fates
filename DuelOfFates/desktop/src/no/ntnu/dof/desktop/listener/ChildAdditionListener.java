package no.ntnu.dof.desktop.listener;

import com.badlogic.gdx.Gdx;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.internal.NonNull;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChildAdditionListener implements ChildEventListener {
    private final Consumer<DataSnapshot> childAdditionListener;

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
        childAdditionListener.accept(snapshot);
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Gdx.app.log("DatabaseChange", "Database update cancelled.");
    }
}
