package no.ntnu.dof.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
