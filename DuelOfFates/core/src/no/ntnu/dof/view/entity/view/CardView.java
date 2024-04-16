package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
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
    private final TextLabel nameLabel;
    private final TextLabel costLabel;
    private final TextLabel descriptionLabel;
    private final float width;
    private final float height;
    private final boolean playable;

    public CardView(float Scale, Card card, int i, ClickListener playListener) {
        this.card = card;
        this.cardTexture = new CardTexture(card.getName(), Scale * Gdx.graphics.getHeight());
        this.width = cardTexture.getWidth();
        this.height = cardTexture.getHeight();
        this.playable = playListener != null;
        this.setPosition(width * i, playable ? this.height / 4 : 0);
        this.setBounds(this.getX(), this.getY(), width, height);
        if (playable) this.addListener(playListener);
        else this.setColor(new Color(0.7f, 0.7f, 0.7f, 1.0f));

        nameLabel = new TextLabel(card.getName(), width * 0.22f, height * 0.81f, width * 0.7f, height * 0.18f, height * 0.004f, Color.RED);
        nameLabel.getText().setAlignment(Align.center);
        this.addActor(nameLabel.getText());
        costLabel = new TextLabel(Integer.toString(card.getCost().getValue()), width * 0.05f, height * 0.87f, width * 0.1f, height * 0.1f, height * 0.005f, Color.GRAY);
        this.addActor(costLabel.getText());

        String descriptionString = "This card does : \n";
        if(card.getHostEffectNames() != null) {
            for (String effect : card.getHostEffectNames()) {
                descriptionString += effect + "\n";
            }
        }
        if(card.getOpponentEffectNames() != null) {
            for (String effect : card.getOpponentEffectNames()) {
                descriptionString += effect + "\n";
            }
        }

        descriptionLabel = new TextLabel(descriptionString, width * 0.09f, height * 0.06f, width * 0.8f, height * 0.33f, height * 0.004f, Color.WHITE);
        descriptionLabel.getText().setAlignment(Align.topLeft);
        this.addActor(descriptionLabel.getText());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color originalColor = batch.getColor();
        batch.setColor(this.getColor());
        cardTexture.draw(batch, getX(), getY());
        batch.setColor(originalColor);
        super.draw(batch, parentAlpha);
    }

    @Override
    public boolean remove() {
        return super.remove();
    }

    public void dispose() {
        cardTexture.dispose();
        nameLabel.dispose();
        costLabel.dispose();
        descriptionLabel.dispose();
    }

}
