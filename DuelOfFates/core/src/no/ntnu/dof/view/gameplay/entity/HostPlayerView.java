package no.ntnu.dof.view.gameplay.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.view.gameplay.texture.Image;
import no.ntnu.dof.view.gameplay.control.AbortButton;

@Getter
public class HostPlayerView extends PlayerView {
    private final Group hostInterface;
    private final Group handView;
    private final Image deckView;
    private final TextButton endTurnButton;
    private final AbortButton abortButton;
    private final Player player;
    private final Skin skin;

    private static ClickListener playListener;

    public HostPlayerView(Player player) {
        super(player);
        this.player = player;
        this.setPosition((float) Gdx.graphics.getWidth() / 4 - this.getGraphics().getWidth() / 2, Gdx.graphics.getHeight() * 0.6f);

        this.getHealthBarView().setPosition(0, -this.getHealthBarView().getHeight() - 5);
        this.getManaPool().setPosition(-this.getManaPool().getWidth() - 10, this.getGraphics().getHeight() / 2 - this.getManaPool().getHeight() / 2);
        this.getArmorPool().setPosition(this.getHealthBarView().getWidth() + 10, this.getHealthBarView().getY() + this.getHealthBarView().getHeight() / 2 - this.getArmorPool().getHeight() / 2);

        hostInterface = new Group();

        deckView = new Image("cardBackside.png", 0.25f);
        deckView.setPosition(0, 20);
        hostInterface.addActor(deckView);

        handView = new Group();
        updateHandView();
        hostInterface.addActor(handView);

        skin = new Skin(Gdx.files.internal("UISkin.json"));
        endTurnButton = new TextButton("End Turn", skin, "default");
        endTurnButton.addListener(playListener);
        endTurnButton.setPosition(deckView.getWidth() * 1.4f, Gdx.graphics.getHeight() * 0.375f);
        hostInterface.addActor(endTurnButton);

        abortButton = new AbortButton(0.35f, playListener);
        abortButton.setPosition(5, Gdx.graphics.getHeight() - abortButton.getHeight() - 5);
        hostInterface.addActor(abortButton);

        this.addActor(hostInterface);
        hostInterface.setPosition(-this.getX(), -this.getY());
    }

    public void draw(Batch batch, float parentAlpha) {
        updateHandView();
        super.draw(batch, parentAlpha);
    }


    public void updateHandView() {
        for (int i = 0; i < handView.getChildren().size; i++) {
            ((CardView) handView.getChild(i)).dispose();
        }
        handView.clear();
        List<Card> temporaryList = new ArrayList<>(player.getHand().getCards());
        //has to use iterator to avoid ConcurrentModificationException
        for (Iterator<Card> iterator = temporaryList.iterator(); iterator.hasNext(); ) {
            Card card = iterator.next();
            ClickListener cardListener = player.canPlay(card) ? playListener : null;
            handView.addActor(new CardView(0.35f, card, handView.getChildren().size, cardListener));
        }
        if (handView.getChildren().size > 0) {
            handView.setBounds(Gdx.graphics.getWidth() / 2f - (handView.getChild(handView.getChildren().size - 1).getX() + handView.getChild(handView.getChildren().size - 1).getWidth()) / 2f, 25, handView.getChild(handView.getChildren().size - 1).getWidth() * handView.getChildren().size, (9f / 8) * handView.getChild(handView.getChildren().size - 1).getHeight());
        }
    }

    public static void provideClickListener(ClickListener playListener) {
        HostPlayerView.playListener = playListener;
    }

    public void dispose() {
        super.dispose();
        deckView.dispose();
        skin.dispose();
        endTurnButton.clear();
        abortButton.dispose();
        for (int i = 0; i < handView.getChildren().size; i++) {
            ((CardView) handView.getChild(i)).dispose();
        }
    }

    public void removeEndTurnButton() {
        endTurnButton.remove();
    }

    public void addEndTurnButton() {
        hostInterface.addActor(endTurnButton);
    }
}
