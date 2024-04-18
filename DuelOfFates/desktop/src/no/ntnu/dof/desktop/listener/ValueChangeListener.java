package no.ntnu.dof.desktop.listener;

import com.badlogic.gdx.Gdx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.NonNull;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValueChangeListener implements ValueEventListener {
    private final Consumer<DataSnapshot> valueChangeListener;

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        valueChangeListener.accept(snapshot);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Gdx.app.log("DatabaseChange", "Database update cancelled.");
    }
}
