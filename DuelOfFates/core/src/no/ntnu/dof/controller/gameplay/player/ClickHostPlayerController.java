package no.ntnu.dof.controller.gameplay.player;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Optional;

import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.gameplay.CardView;

public class ClickHostPlayerController extends ClickListener implements PlayerController{

    protected Player player;
    protected Optional<Card> chosen;
    protected boolean played;
    private GameComms comms;
    private static final ClickHostPlayerController instance = new ClickHostPlayerController();

    public ClickHostPlayerController() {
        this.player = null;
        this.chosen = Optional.empty();
        this.played = false;
    }

    public void setPlayer(Player player) {
        this.player = player;
        comms = ServiceLocator
                .getGameService()
                .createComms("-NuBZPuG4gkubhYI_FsN"); // TODO inject gameId

        comms.setPlayerLastTurn(this.player.getName());
    }

    public static ClickHostPlayerController get() {
        return instance;
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

    public static void setPlay(Optional<Card> play) {
        instance.setChosen(play);
    }

    private synchronized void setChosen(Optional<Card> play) {
        this.chosen = play;
        played = true;
        this.notify();
    }
}
