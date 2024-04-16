package no.ntnu.dof.controller.gameplay.player;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Optional;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.view.entity.view.CardView;

public class ClickPlayerController extends ClickListener implements PlayerController {
    protected Optional<Card> chosen;

    public ClickPlayerController() {
        this.chosen = Optional.empty();
    }

    @Override
    public synchronized boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (event.getListenerActor() instanceof CardView) {
            chosen = Optional.of(((CardView) event.getListenerActor()).getCard());
        } else {
            chosen = Optional.empty();
        }
        this.notify();
        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public synchronized Optional<Card> choosePlay(long timeout) throws InterruptedException {
        chosen = Optional.empty();
        this.wait(timeout);
        return chosen;
    }
}
