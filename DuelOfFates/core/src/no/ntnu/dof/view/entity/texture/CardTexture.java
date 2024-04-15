package no.ntnu.dof.view.entity.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Getter;

@Getter
public class CardTexture {
    TextureRegion background;
    TextureRegion nameFlag;
    TextureRegion line;
    Texture mana;
    Texture image;
    float width;
    float height;

    public CardTexture(String cardName, float height) {
        this.height = height;
        this.width = height * 0.708f;
        background = new TextureRegion(new Texture(Gdx.files.internal("cards/" + "background.png")), 214, 96, 591, 834);
        nameFlag = new TextureRegion(new Texture(Gdx.files.internal("cards/" + "nameFlag.png")), 214, 96, 591, 834);
        line = new TextureRegion(new Texture(Gdx.files.internal("cards/" + "line.png")), 214, 96, 591, 834);
        mana = new Texture(Gdx.files.internal("cards/" + "mana.png"));
        image = new Texture(Gdx.files.internal("cards/" + "Card1" + ".png"));
    }

    public void draw(Batch batch, float x, float y) {
        batch.draw(background, x, y, width, height);
        batch.draw(image,x + width*0.03f, y+height*0.45f, width*0.88f, height*0.41f);
        batch.draw(nameFlag, x, y, width, height);
        batch.draw(line, x, y, width, height);
        batch.draw(mana, x, y+height*0.85f, height*0.15f, height*0.15f);
    }

    public void dispose() {
        image.dispose();
    }
}
