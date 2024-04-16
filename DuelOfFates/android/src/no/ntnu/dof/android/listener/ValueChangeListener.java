package no.ntnu.dof.android.listener;

import androidx.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
