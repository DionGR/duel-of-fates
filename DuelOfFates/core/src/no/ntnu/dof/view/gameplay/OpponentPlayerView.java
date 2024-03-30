package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import no.ntnu.dof.model.gameplay.player.Player;

public class OpponentPlayerView extends PlayerView{
    public OpponentPlayerView(Player player) {
        super(player);
        this.setPosition((float) (3 * Gdx.graphics.getWidth()) /4- (float) this.getGraphics().getWidth() /2, Gdx.graphics.getHeight()*0.6f);
        this.getManaPool().setPosition(this.getGraphics().getWidth()+10, this.getGraphics().getHeight()/2-this.getManaGraphics().getHeight()/2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
