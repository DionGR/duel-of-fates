package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import lombok.Getter;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.view.Image;

@Getter
public class CardView extends Image {

    private final Card card;

    public CardView(float Scale, Card card, int i) {
        super("./assets/Card.png", Scale);

        System.out.println(i);
        this.card = card;
        this.setPosition(((float) 2 * this.getWidth()/3)*i, 0);
        this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println( ((CardView)event.getListenerActor()).getCard().getName() + " clicked");
                return true;
            }
        } );
        System.out.println("CardView created");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the image
        super.draw(batch, parentAlpha);

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

        int cost = card.getCost().getValue();
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
