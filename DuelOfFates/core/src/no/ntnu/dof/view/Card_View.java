package no.ntnu.dof.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.time.format.TextStyle;

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
        batch.draw(getImg(), getX(), getY(), getWidth(), getHeight());

/*
        TextField.TextFieldStyle NameStyle = new TextField.TextFieldStyle();
        NameStyle.fontColor = Color.RED;
        NameStyle.font = new BitmapFont();
        TextArea Name = new TextArea(card.getName(), NameStyle);
        this.addActor(Name);
*/

        BitmapFont font = new BitmapFont();
        font.setColor(0.8f, 0.2f, 0.2f, 1);
        font.getData().setScale(2);
        font.draw(batch, card.getName(), getX() + getWidth()*0.05f,0 + getHeight()*0.90f);

        font.setColor(0.5f, 0.5f, 0.5f, 1);
        font.getData().setScale(1);
        //font.draw(spriteBatch, description, x + getWidth()*0.1f,y + getHeight()*0.37f);

        int cost = card.getCost().getMana().getValue();
        if(cost>0) {
            font.setColor(0.2f, 0.8f, 0.2f, 1);
            font.getData().setScale(2);
            font.draw(batch, Integer.toString(cost), getX() + getWidth() * 0.83f, getY() + getHeight() * 0.94f);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (EventListener listener : getListeners()) {
            if(listener instanceof ClickListener) {
                System.out.println("Card clicked");
            }
        }
    }

}
