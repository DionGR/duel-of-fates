package no.ntnu.dof.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Image extends Group {
    @Getter
    private Texture img;
    private int width;
    private int height;

    public Image(String path, float Scale) {
        img = new Texture(path);
        float ratio = (float) img.getWidth() / (float) img.getHeight();
        height = (int) (Gdx.graphics.getHeight() * Scale);
        width = (int) (height * ratio);
        setBounds(0, 0, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the image
        batch.draw(img, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        img.dispose();
    }

    public void reverse() {
        this.setWidth(-this.getWidth());
    }
}
