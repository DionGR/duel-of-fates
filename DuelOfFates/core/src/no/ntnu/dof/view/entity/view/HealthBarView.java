package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import no.ntnu.dof.model.gameplay.player.Player;

public class HealthBarView extends Group {

    private final Label healthLabel;
    private final ShapeRenderer ShapeDrawer = new ShapeRenderer();
    private final Skin skin = new Skin(Gdx.files.internal("UISkin.json"));
    private final Player player;

    public HealthBarView(Player player, float width) {
        super();
        this.player = player;

        this.setWidth(width);
        this.setHeight((float) (Gdx.graphics.getHeight()*0.05));

        skin.setScale(0.6f);
        this.healthLabel = new Label( player.getHealth().getValue() + "/" + (player.getPlayerClass().getMaxHealth().getValue()), skin, "default");
        healthLabel.setBounds(0, 0, getWidth(), getHeight());
        healthLabel.setAlignment(Align.center);
        healthLabel.setColor(Color.WHITE);
        this.addActor(healthLabel);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        float healthPercentage =  Math.max(0,((float) player.getHealth().getValue()/(float) player.getPlayerClass().getMaxHealth().getValue()));

        ShapeDrawer.begin(ShapeRenderer.ShapeType.Filled);
        ShapeDrawer.setColor(Color.BLACK);
        ShapeDrawer.rect(this.getParent().getX()+getX()-2, this.getParent().getY()+getY()-2, getWidth()+4, getHeight()+4);
        ShapeDrawer.setColor(Color.RED);
        ShapeDrawer.rect(this.getParent().getX()+getX(), this.getParent().getY()+getY(), (getWidth())*healthPercentage, getHeight());
        ShapeDrawer.end();

        batch.begin();
        super.draw(batch, parentAlpha);
        update();
        //healthLabel.draw(batch, parentAlpha);
    }

    public void update() {
        healthLabel.setText(Math.max(0, player.getHealth().getValue()) + "/" + (player.getPlayerClass().getMaxHealth().getValue()));
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        System.out.println("HealthBarView: " + getX() + " " + getY() + " " + getWidth() + " " + getHeight());
    }

    public void dispose() {
        healthLabel.clear();
        ShapeDrawer.dispose();
        skin.dispose();
    }
}
