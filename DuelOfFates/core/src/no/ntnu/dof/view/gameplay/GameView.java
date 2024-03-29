package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.Image;

public class GameView extends Actor {
    private final Game game;
    private Image graphics;
    private final List<PlayerView> playerViews;

    public GameView(Game game) {
        this.game = game;
        this.playerViews = new ArrayList<>();
        this.game.getPlayers().forEach(p -> playerViews.add(new PlayerView(p)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // TODO render
    }
}
