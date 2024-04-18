package no.ntnu.dof.view.gameplay.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import lombok.Getter;
import no.ntnu.dof.view.gameplay.texture.Image;
import no.ntnu.dof.view.gameplay.label.TextLabel;

@Getter
public class PoolView extends Group {
    private final Image graphics;
    private final TextLabel text;

    public PoolView(String path, Color color, float scale) {
        super();

        graphics = new Image(path, scale);
        text = new TextLabel("", graphics.getWidth() * 0.28f, graphics.getHeight() * 0.3f, graphics.getWidth() * 0.4f, graphics.getHeight() * 0.4f, graphics.getHeight() * 0.03f, color);
        this.setWidth(graphics.getWidth());
        this.setHeight(graphics.getHeight());
        this.addActor(graphics);
        this.addActor(text.getText());
    }

    public void update(int value) {
        text.getText().setText(Integer.toString(value));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        graphics.dispose();
        text.dispose();
    }
}
