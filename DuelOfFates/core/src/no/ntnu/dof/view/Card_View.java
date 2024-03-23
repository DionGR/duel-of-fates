package no.ntnu.dof.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.Getter;

@Getter
public class Card_View extends Image{

    private String name;
    private String description;
    private int cost;

    public Card_View(String path, float Scale, String name, String description, int cost) {
        super(path, Scale);
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public void render(SpriteBatch spriteBatch, float x, float y) {
        // Draw the image
        super.render(spriteBatch, x, y);

        BitmapFont font = new BitmapFont();
        font.setColor(0.8f, 0.2f, 0.2f, 1);
        font.getData().setScale(2);
        font.draw(spriteBatch, name, x + getWidth()*0.05f,y + getHeight()*0.90f);

        font.setColor(0.5f, 0.5f, 0.5f, 1);
        font.getData().setScale(1);
        font.draw(spriteBatch, description, x + getWidth()*0.1f,y + getHeight()*0.37f);

        if(cost>0) {
            font.setColor(0.2f, 0.8f, 0.2f, 1);
            font.getData().setScale(2);
            font.draw(spriteBatch, Integer.toString(cost), x + getWidth() * 0.83f, y + getHeight() * 0.94f);
        }

    }

}
