package no.ntnu.dof.view.gameplay.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Getter;

@Getter
public class CardTexture {
    private final TextureRegion background;
    private final TextureRegion nameFlag;
    private final TextureRegion line;
    private final Texture mana;
    private final Texture image;
    private final float width;
    private final float height;

    public CardTexture(String cardName, float height) {
        this.height = height;
        this.width = height * 0.708f;
        background = new TextureRegion(new Texture("cardBackground.png"), 214, 96, 591, 834);
        nameFlag = new TextureRegion(new Texture("cardNameFlag.png"), 214, 96, 591, 834);
        line = new TextureRegion(new Texture("cardLine.png"), 214, 96, 591, 834);
        mana = new Texture(Gdx.files.internal("mana.png"));
        image = new Texture("cardsIcon/" + cardName + ".png");
    }

    public void draw(Batch batch, float x, float y) {
        batch.draw(background, x, y, width, height);
        batch.draw(image, x + width * 0.03f, y + height * 0.45f, width * 0.88f, height * 0.41f);
        batch.draw(nameFlag, x, y, width, height);
        batch.draw(line, x, y, width, height);
        batch.draw(mana, x, y + height * 0.85f, height * 0.15f, height * 0.15f);
    }

    public void dispose() {
        background.getTexture().dispose();
        nameFlag.getTexture().dispose();
        line.getTexture().dispose();
        mana.dispose();
        image.dispose();
    }
}
