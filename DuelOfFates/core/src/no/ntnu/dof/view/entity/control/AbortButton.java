package no.ntnu.dof.view.entity.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import javax.inject.Inject;
import javax.inject.Named;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.view.di.AbortButtonComponent;
import no.ntnu.dof.view.di.DaggerAbortButtonComponent;
import no.ntnu.dof.view.entity.texture.CardTexture;
import no.ntnu.dof.view.entity.view.CardView;

public class AbortButton extends CardView {
    @Inject
    @Named("abortCard")
    protected Card abortCard;

    public AbortButton(float scale, ClickListener playListener) {
        AbortButtonComponent component = DaggerAbortButtonComponent.create();
        component.inject(this);

        this.card = abortCard;
        this.addListener(playListener);
        this.cardTexture = new CardTexture(card.getName(), scale * Gdx.graphics.getHeight());
        this.width = cardTexture.getHeight() * scale;
        this.height = cardTexture.getHeight() * scale;
        this.setBounds(getX(), getY(), width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(this.cardTexture.getImage(), getX(), getY(), width, height);
    }

    @Override
    public boolean remove() {
        return true;
    }

    @Override
    public void dispose() {
        this.cardTexture.dispose();
    }
}
