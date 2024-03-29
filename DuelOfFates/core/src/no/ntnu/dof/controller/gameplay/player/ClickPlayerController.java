package no.ntnu.dof.controller.gameplay.player;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Optional;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.view.gameplay.CardView;

public class ClickPlayerController extends ClickListener implements PlayerController {
    protected Optional<Card> chosen;
    protected boolean played;

    public ClickPlayerController() {
        this.chosen = Optional.empty();
        this.played = false;
    }

    @Override
    public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (event.getListenerActor() instanceof CardView) {
            chosen = Optional.of(((CardView) event.getListenerActor()).getCard());
        } else {
            chosen = Optional.empty();
        }
        played = true;
        this.notify();
        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public synchronized Optional<Card> choosePlay() {
        while (!played) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {
            }
        }
        played = false;
        return chosen;
    }
}
