package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HighlightingArea extends Actor {
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

        ShapeRenderer ShapeDrawer = new ShapeRenderer();
        ShapeDrawer.begin(ShapeRenderer.ShapeType.Line);
        ShapeDrawer.setColor(Color.RED);
        ShapeDrawer.rect(getX()-5, getY()-5, getWidth()+10, getHeight()+10);
        ShapeDrawer.end();

        batch.begin();
    }

}
