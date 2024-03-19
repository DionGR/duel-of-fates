package no.ntnu.dof.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

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
}
