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
    private Image graphics;
    private final HostPlayerView hostPlayerView;
    private final OpponentPlayerView opponentPlayerView;

    public GameView(Game game) {
        this.game = game;
        hostPlayerView = new HostPlayerView(game.getHost());
        this.addActor(hostPlayerView);
        opponentPlayerView = new OpponentPlayerView(game.getOpponent());
        this.addActor(opponentPlayerView);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
