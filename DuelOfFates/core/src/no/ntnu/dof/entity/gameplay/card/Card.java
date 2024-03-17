package no.ntnu.dof.entity.gameplay.card;

import java.util.ArrayList;

import no.ntnu.dof.entity.gameplay.GameplayEntity;
import no.ntnu.dof.entity.gameplay.effect.Effect;

public abstract class Card extends GameplayEntity {

    private ArrayList<Effect> effects;

    public Card(String name) {
        super(name);
        this.effects = new ArrayList<>();
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void removeEffect(Effect effect) {
        effects.remove(effect);
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

}
