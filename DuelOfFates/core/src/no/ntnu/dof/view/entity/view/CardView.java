package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.di.DaggerGameComponent;
import no.ntnu.dof.controller.gameplay.di.GameComponent;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.model.gameplay.effect.EffectInvoker;
import no.ntnu.dof.view.entity.di.CardViewComponent;
import no.ntnu.dof.view.entity.di.DaggerCardViewComponent;
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

    @Inject
    @Named("effectInvoker")
    EffectInvoker<String, Effect> effectInvoker;

    public CardView(float Scale, Card card, int i, ClickListener playListener) {
        CardViewComponent cardViewComponent = DaggerCardViewComponent.create();
        cardViewComponent.inject(this);

        this.card = card;
        this.cardTexture = new CardTexture(card.getName(), Scale * Gdx.graphics.getHeight());
        this.width = cardTexture.getWidth();
        this.height = cardTexture.getHeight();
        this.playable = playListener != null;
        this.setPosition(width * i, playable ? this.height / 8 : 0);
        this.setBounds(this.getX(), this.getY(), width, height);
        if (playable) this.addListener(playListener);
        else this.setColor(new Color(0.7f, 0.7f, 0.7f, 1.0f));

        nameLabel = new TextLabel(card.getViewName(), width * 0.22f, height * 0.81f, width * 0.7f, height * 0.18f, height * 0.004f, Color.RED);
        nameLabel.getText().setAlignment(Align.center);
        this.addActor(nameLabel.getText());
        costLabel = new TextLabel(Integer.toString(card.getCost().getValue()), width * 0.05f, height * 0.87f, width * 0.1f, height * 0.1f, height * 0.005f, Color.GRAY);
        this.addActor(costLabel.getText());

        String descriptionString = "";
        for (String effectName : card.getHostEffectNames()) {
            Effect effect = effectInvoker.invoke(effectName);
            descriptionString += "+" + effect.getViewName() +  ":" + effect.getDescription() + "\n";
        }
        for (String effectName : card.getOpponentEffectNames()) {
            Effect effect = effectInvoker.invoke(effectName);
            descriptionString += "-" + effect.getViewName() +  ":" + effect.getDescription() + "\n";
        }

        descriptionLabel = new TextLabel(descriptionString, width * 0.09f, height * 0.06f, width * 0.8f, height * 0.33f, height * 0.005f, Color.WHITE);
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
