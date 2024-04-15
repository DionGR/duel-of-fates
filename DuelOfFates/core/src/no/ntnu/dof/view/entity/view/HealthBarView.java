package no.ntnu.dof.view.gameplay;

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

    private Label healthLabel;

    private final Player player;
    public HealthBarView(Player player, float x, float y, float width) {
        this.player = player;

        this.setPosition(x, y);

        this.setWidth(width);
        this.setHeight((float) (Gdx.graphics.getHeight()*0.05));

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.setScale(0.6f);
        this.healthLabel = new Label( (player.getHealth().getValue()+player.getArmor().getValue()) + "/" + (player.getPlayerClass().getMaxHealth().getValue()), skin, "default");
        healthLabel.setBounds(getX(), getY(), getWidth(), getHeight());
        healthLabel.setAlignment(Align.center);
        healthLabel.setColor(Color.WHITE);
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
        ShapeDrawer.rect(getX(), getY(), getWidth(), getHeight());
        ShapeDrawer.setColor(Color.RED);
        ShapeDrawer.rect(getX(), getY(), (getWidth())*healthPercentage, getHeight());
        ShapeDrawer.setColor(Color.GRAY);
        ShapeDrawer.rect(getX()+(getWidth())*healthPercentage, getY(), (getWidth())*armorPercentage, getHeight());
        ShapeDrawer.end();

        batch.begin();
        update();
        healthLabel.draw(batch, parentAlpha);
    }

    public void update() {
        healthLabel.setText((player.getHealth().getValue()+player.getArmor().getValue()) + "/" + (player.getPlayerClass().getMaxHealth().getValue()));
    }

    public void dispose() {
        healthLabel.clear();
    }
}
