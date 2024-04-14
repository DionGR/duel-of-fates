package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.Image;

public class GameView extends Group {
    private final Game game;
    private Image background;
    private final HostPlayerView hostPlayerView;
    private final OpponentPlayerView opponentPlayerView;
    public GameView(Game game) {
        this.game = game;

        background = new Image("./assets/background.png", 1.0f);
        this.addActor(background);

        hostPlayerView = new HostPlayerView(this.game.getPlayers().get(0));
        this.addActor(hostPlayerView);
        opponentPlayerView = new OpponentPlayerView(this.game.getPlayers().get(1));
        this.addActor(opponentPlayerView);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        background.dispose();
        hostPlayerView.dispose();
        opponentPlayerView.dispose();
    }
}
