package no.ntnu.dof.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Image {
    private Texture img;
    private int width;
    private int height;

    public Image(String path, float Scale) {
        img = new Texture(path);
        float ratio = (float)img.getWidth()/(float)img.getHeight();
        height = (int)(Gdx.graphics.getHeight()*Scale);
        width = (int)(height*ratio);
    }

    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void render(SpriteBatch spriteBatch, float x, float y) {
        // Draw the image
        spriteBatch.draw(img, x, y, width, height);
    }
}
