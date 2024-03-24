package no.ntnu.dof.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import lombok.Getter;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Getter
public class Card_View extends Image{

    private Card card;

    public Card_View(float Scale, Card card) {
        super("./assets/Card.png", Scale);
        this.card = card;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the image
        super.render((SpriteBatch) batch, 0, 0);

        BitmapFont font = new BitmapFont();
        font.setColor(0.8f, 0.2f, 0.2f, 1);
        font.getData().setScale(2);
        font.draw(batch, card.getName(), 0 + getWidth()*0.05f,0 + getHeight()*0.90f);

        font.setColor(0.5f, 0.5f, 0.5f, 1);
        font.getData().setScale(1);
        //font.draw(spriteBatch, description, x + getWidth()*0.1f,y + getHeight()*0.37f);

        int cost = card.getCost().getMana().getValue();
        if(cost>0) {
            font.setColor(0.2f, 0.8f, 0.2f, 1);
            font.getData().setScale(2);
            font.draw(batch, Integer.toString(cost), 0 + getWidth() * 0.83f, 0 + getHeight() * 0.94f);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (EventListener listener : getListeners()) {
            if(listener instanceof ClickListener) {
                if (((ClickListener) listener).touchDown(new InputEvent(), 0, 0, 0, 0)) {
                    System.out.println("Card clicked");
                }
            }
        }
    }

}
