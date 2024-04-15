package no.ntnu.dof.view.entity.label;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import lombok.Getter;

@Getter
public class TextLabel{
    private Label text;

    public TextLabel(String string, float x, float y, float width, float height, float scale, Color color){
        Skin skin = new Skin(Gdx.files.internal("UISkin.json"));
        BitmapFont font = skin.getFont("default-font");
        text = new Label(string, new Label.LabelStyle(font, color));
        text.setBounds(x,y,width,height);
        text.setAlignment(Align.center);
        text.setFontScale(scale);
    }
}
