package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import lombok.Getter;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.view.entity.label.TextLabel;
import no.ntnu.dof.view.entity.texture.CardTexture;

@Getter
public class CardView extends Group {

    private final Card card;
    private final CardTexture cardTexture;
    private final float width;
    private final float height;
    private final boolean playable;

    public CardView(float Scale, Card card, int i, ClickListener playListener) {
        //super("./assets/Card.png", Scale);
        this.card = card;
        this.cardTexture = new CardTexture(card.getName(), Scale * Gdx.graphics.getHeight());
        this.width = cardTexture.getWidth();
        this.height = cardTexture.getHeight();
        this.playable = playListener != null;
        this.setPosition(((float) 2 * width / 3) * i, playable ? this.height / 4 : 0);
        this.setBounds(this.getX(), this.getY(), width, height);
        if (playable) this.addListener(playListener);
        else this.setColor(new Color(0.7f, 0.7f, 0.7f, 1.0f));

        Label name = (new TextLabel(card.getName(), width * 0.05f, height * 0.80f, width * 0.8f, height * 0.18f, height * 0.006f, Color.RED)).getText();
        name.setAlignment(Align.center);
        this.addActor(name);
        Label cost = (new TextLabel(Integer.toString(card.getCost().getValue()), width * 0.82f, height * 0.86f, width * 0.1f, height * 0.1f, height * 0.005f, Color.GREEN)).getText();
        this.addActor(cost);

        String descriptionString = "";
        for (String effect : card.getHostEffectNames()) {
            descriptionString += effect + "\n";
        }
        for (String effect : card.getOpponentEffectNames()) {
            descriptionString += effect + "\n";
        }
        Label description = (new TextLabel(descriptionString, width * 0.09f, height * 0.06f, width * 0.8f, height * 0.33f, height * 0.004f, Color.WHITE)).getText();
        description.setAlignment(Align.topLeft);
        this.addActor(description);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color originalColor = batch.getColor();
        batch.setColor(this.getColor());
        cardTexture.draw(batch, getX(), getY());
        originalColor.set(originalColor);
        super.draw(batch, parentAlpha);
    }

    @Override
    public boolean remove() {
        return super.remove();
    }
}
