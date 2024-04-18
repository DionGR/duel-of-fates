package no.ntnu.dof.view.gameplay.control;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HighlightingArea extends Actor {
    private final ShapeRenderer shapeDrawer = new ShapeRenderer();

    public HighlightingArea(float x, float y, float width, float height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        shapeDrawer.begin(ShapeRenderer.ShapeType.Line);
        shapeDrawer.setColor(Color.RED);
        shapeDrawer.rect(getX() - 5, getY() - 5, getWidth() + 10, getHeight() + 10);
        shapeDrawer.end();

        batch.begin();
    }

    public void dispose() {
        shapeDrawer.dispose();
    }
}
