package no.ntnu.dof.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Image extends Group {
    @Getter
    private Texture img;

    public Image(String path, float Scale) {
        img = new Texture(path);
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
}
