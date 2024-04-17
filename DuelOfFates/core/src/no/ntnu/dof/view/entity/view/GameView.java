package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.player.PlayerController;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.Image;
import no.ntnu.dof.view.entity.control.TimerView;

@Getter
public class GameView extends Group {
    private final Game game;
    private final Image background;
    private final Image activePlayerView;
    private final HostPlayerView hostPlayerView;
    private final OpponentPlayerView opponentPlayerView;
    private final TimerView timerView;

    public GameView(Game game) {
        this.game = game;

        background = new Image("background.png", 1.0f);
        this.addActor(background);

        activePlayerView = new Image("arrow.png", 0.10f);
        this.addActor(activePlayerView);

        opponentPlayerView = new OpponentPlayerView(game.getOpponent());
        this.addActor(opponentPlayerView);

        hostPlayerView = new HostPlayerView(game.getHost());
        this.addActor(hostPlayerView);

        timerView = new TimerView(Gdx.graphics.getWidth(), 10, PlayerController.TURN_TIMEOUT);
        this.addActor(timerView);
        game.getHost().beginTurnEvent.register(timerView);
        game.getHost().cardPlayedEvent.register(timerView);
        game.getOpponent().beginTurnEvent.register(timerView);
        game.getOpponent().cardPlayedEvent.register(timerView);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(game.getNextPlayer() == game.getHost()) {
            activePlayerView.setPosition(hostPlayerView.getX() + hostPlayerView.getGraphics().getWidth() / 2 - activePlayerView.getWidth() / 1.5f, hostPlayerView.getY() + hostPlayerView.getGraphics().getHeight());
            hostPlayerView.addEndTurnButton();
        }
        else {
            activePlayerView.setPosition(opponentPlayerView.getX() + opponentPlayerView.getGraphics().getWidth() / 2 - activePlayerView.getWidth() / 2, opponentPlayerView.getY() + opponentPlayerView.getGraphics().getHeight());
            hostPlayerView.removeEndTurnButton();
        }
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        background.dispose();
        activePlayerView.dispose();
        hostPlayerView.dispose();
        opponentPlayerView.dispose();
        timerView.dispose();
    }
}
