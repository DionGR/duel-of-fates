package no.ntnu.dof.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Image extends Group {
    @Getter
    private Sprite img;

    public Image(String path, float Scale) {
        Texture texture = new Texture(path);
        img = new Sprite(texture);
        float ratio = (float) img.getWidth() / (float) img.getHeight();
        this.setHeight((int) (Gdx.graphics.getHeight() * Scale));
        this.setWidth((int) (getHeight() * ratio));
        setBounds(0, 0, getWidth(), getHeight());
    }

    public void render(SpriteBatch spriteBatch, float x, float y) {
        // Draw the image
        spriteBatch.draw(img, x, y, this.getWidth(), this.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the image
        batch.draw(img, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        img.getTexture().dispose();
    }

    public void reverse() {
        img.flip(true, false);
    }
}
