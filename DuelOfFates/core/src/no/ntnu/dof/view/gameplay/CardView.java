package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Optional;

import lombok.Getter;
import no.ntnu.dof.controller.gameplay.player.TestClickPlayerController;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.effect.Effect;
import no.ntnu.dof.view.Image;

@Getter
public class CardView extends Image {

    private final Card card;

    public CardView(float Scale, Card card, int i) {
        super("./assets/Card.png", Scale);
        this.card = card;
        this.setPosition(((float) 2 * this.getWidth()/3)*i, 0);
        this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.addListener(new ClickListener() {
            @Override
            public synchronized boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                TestClickPlayerController.setPlay(Optional.of(card));
                return super.touchDown(event, x, y, pointer, button);
            }
        } );

        Label name = (new TextLabel(card.getName(), getWidth()*0.05f,getHeight()*0.80f,getWidth()*0.6f,getHeight()*0.2f,getHeight()*0.008f, Color.RED)).getText();
        name.setAlignment(Align.left);
        this.addActor(name);
        Label cost = (new TextLabel(Integer.toString(card.getCost().getValue()), getWidth()*0.82f,getHeight()*0.86f,getWidth()*0.1f,getHeight()*0.1f,getHeight()*0.005f, Color.GREEN)).getText();
        this.addActor(cost);


        String descriptionString = "";
        for (Effect effect : card.getHostEffects()){
            descriptionString += effect.toString() + "\n";
        }
        for (Effect effect : card.getOpponentEffects()){
            descriptionString += effect.toString() + "\n";
        }
        Label description = (new TextLabel(descriptionString, getWidth()*0.09f,getHeight()*0.06f,getWidth()*0.8f,getHeight()*0.33f,getHeight()*0.004f, Color.BLACK)).getText();
        description.setAlignment(Align.topLeft);
        this.addActor(description);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public boolean remove () {
        return super.remove();
    }

}
