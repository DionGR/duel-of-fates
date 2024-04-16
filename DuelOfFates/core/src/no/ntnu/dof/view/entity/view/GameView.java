package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import lombok.Getter;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.Image;
import no.ntnu.dof.view.entity.view.HostPlayerView;

@Getter
public class GameView extends Group {
    private final Game game;
    private Image background;
    private Image activePlayerView;
    private final HostPlayerView hostPlayerView;
    private final OpponentPlayerView opponentPlayerView;

    public GameView(Game game) {
        this.game = game;

        background = new Image("background.png", 1.0f);
        this.addActor(background);

        activePlayerView = new Image("arrow.png", 0.15f);
        this.addActor(activePlayerView);

        opponentPlayerView = new OpponentPlayerView(game.getOpponent());
        this.addActor(opponentPlayerView);

        hostPlayerView = new HostPlayerView(game.getHost());
        this.addActor(hostPlayerView);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(game.getNextPlayer() == game.getHost()) {
            activePlayerView.setPosition(hostPlayerView.getX() + hostPlayerView.getGraphics().getWidth() / 2 - activePlayerView.getWidth() / 2, hostPlayerView.getY() + hostPlayerView.getGraphics().getHeight());
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
    }
}
