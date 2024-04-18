package no.ntnu.dof.view.gameplay.label;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import lombok.Getter;

@Getter
public class TextLabel {
    private final Skin skin;
    private final BitmapFont font;
    private final Label text;

    public TextLabel(String string, float x, float y, float width, float height, float scale, Color color) {
        skin = new Skin(Gdx.files.internal("UISkin.json"));
        font = skin.getFont("default-font");
        text = new Label(string, new Label.LabelStyle(font, color));
        text.setBounds(x, y, width, height);
        text.setAlignment(Align.center);
        text.setFontScale(scale);
    }

    public void dispose() {
        skin.dispose();
        font.dispose();
    }
}
