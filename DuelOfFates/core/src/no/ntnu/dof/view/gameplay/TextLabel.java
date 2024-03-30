package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import lombok.Getter;

@Getter
public class TextLabel{
    private Label text;

    public TextLabel(String string, float x, float y, float width, float height, float scale, Color color){
        Label.LabelStyle style = new Label.LabelStyle();
        BitmapFont font = new BitmapFont();
        style.font = font;
        style.fontColor = color;
        text = new Label(string, style);
        text.setBounds(x,y,width,height);
        text.setAlignment(Align.center);
        text.setFontScale(scale);

    }
}
