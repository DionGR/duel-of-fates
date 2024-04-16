package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.entity.texture.CardTexture;

public class OpponentPlayerView extends PlayerView {
    private CardView lastPlayedCard;
    public OpponentPlayerView(Player player) {
        super(player);
        this.getGraphics().reverse();
        this.setPosition((float) (3 * Gdx.graphics.getWidth()) /4- (float) this.getGraphics().getWidth() /2, Gdx.graphics.getHeight()*0.55f);

        this.getManaPool().setPosition(this.getGraphics().getWidth()+10, this.getGraphics().getHeight()/2-this.getManaGraphics().getHeight()/2);
        this.getHealthBarView().setPosition(this.getX(), this.getY() - Gdx.graphics.getHeight() * 0.05f);
        this.getArmorPool().setPosition(-this.getArmorPool().getWidth()-10, -this.getHealthBarView().getHeight()/2-this.getArmorPool().getHeight()/2);

        updateLastPlayedCard();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateLastPlayedCard();
        super.draw(batch, parentAlpha);
    }

    public void updateLastPlayedCard() {
        if(lastPlayedCard != null) lastPlayedCard.dispose();
        Card lastPlayed = getPlayer().getLastPlayedCard();
        if(lastPlayed != null) {
            lastPlayedCard = new CardView(0.35f, lastPlayed, 0, null);
            lastPlayedCard.setPosition(-this.getX()+Gdx.graphics.getWidth()-lastPlayedCard.getWidth(), -this.getY());
            this.addActor(lastPlayedCard);
        }
    }

    public void dispose() {
        super.dispose();
        if(lastPlayedCard != null) lastPlayedCard.dispose();
    }
}
