package no.ntnu.dof.model.gameplay.event;

import java.util.ArrayList;
import java.util.List;

public abstract class GameEvent<Listener extends GameEventListener> {
    protected final List<Listener> listeners = new ArrayList<>();

    public void register(Listener listener) {
        listeners.add(listener);
    }

    public void deregister(Listener listener) {
        listeners.remove(listener);
    }
}
