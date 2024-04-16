package no.ntnu.dof.view.entity.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import lombok.NonNull;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.event.CardPlayedListener;
import no.ntnu.dof.model.gameplay.event.TurnListener;
import no.ntnu.dof.model.gameplay.player.Player;

public class TimerView extends ProgressBar implements CardPlayedListener, TurnListener {
    private final Texture hourglass;

    public TimerView(int width, int height, long maxValue) {
        super(0, maxValue, 100.0f, false, new ProgressBarStyle());

        Pixmap background = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        background.setColor(new Color(0x292c23ff));
        background.fill();
        getStyle().background = new TextureRegionDrawable(new TextureRegion(new Texture(background)));
        background.dispose();

        Pixmap bar = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        bar.setColor(new Color(0xac9067ff));
        bar.fill();
        getStyle().knobBefore = new TextureRegionDrawable(new TextureRegion(new Texture(bar)));
        bar.dispose();

        hourglass = new Texture("hourglass.png");
        getStyle().knob = new TextureRegionDrawable(new TextureRegion(hourglass));
        getStyle().knob.setMinWidth(25);
        getStyle().knob.setMinHeight(20);

        setWidth(width);
        setHeight(height);
        setPosition(0, 5);
        setProgrammaticChangeEvents(false);
        reset();
    }

    private void reset() {
        setAnimateDuration(0.0f);
        setValue(getMaxValue());
        setAnimateDuration(getMaxValue() / 1000.0f);
        setValue(getMinValue());
    }

    @Override
    public boolean onCardPlayed(@NonNull Card card, @NonNull Player player) {
        reset();
        return false;
    }

    @Override
    public boolean onTurn(@NonNull Player player) {
        reset();
        return false;
    }

    public void dispose() {
        hourglass.dispose();
    }
}
