package no.ntnu.dof.controller.gameplay.player;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Optional;

import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.model.GameComms;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.entity.view.CardView;

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

    public void setPlayer(Player player, GameComms comms) {
        this.player = player;
        this.comms = comms;

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
        ServiceLocator.getGameService().playCard(comms, chosen);

        return chosen;
    }
}
