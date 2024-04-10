package no.ntnu.dof.view.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import no.ntnu.dof.model.gameplay.player.Player;

public class HealthBarView extends Actor {


    private final Player player;
    public HealthBarView(Player player, float x, float y, float width) {
        this.player = player;
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight((float) (Gdx.graphics.getHeight()*0.05));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        float healthPercentage =  Math.max(0,((float) player.getHealth().getValue()/((float) player.getPlayerClass().getMaxHealth().getValue()+ (float) player.getArmor().getValue())));
        float armorPercentage = Math.max(0,((float) player.getArmor().getValue()/((float) player.getPlayerClass().getMaxHealth().getValue()+ (float) player.getArmor().getValue())));

        ShapeRenderer ShapeDrawer = new ShapeRenderer();
        ShapeDrawer.begin(ShapeRenderer.ShapeType.Filled);
        ShapeDrawer.setColor(Color.BLACK);
        ShapeDrawer.rect(getX()-2, getY()-2, getWidth()+4, getHeight()+4);
        ShapeDrawer.setColor(Color.RED);
        ShapeDrawer.rect(getX(), getY(), (getWidth())*healthPercentage, getHeight());
        ShapeDrawer.setColor(Color.GRAY);
        ShapeDrawer.rect(getX()+(getWidth())*healthPercentage, getY(), (getWidth())*armorPercentage, getHeight());
        ShapeDrawer.end();

        batch.begin();
    }
}
