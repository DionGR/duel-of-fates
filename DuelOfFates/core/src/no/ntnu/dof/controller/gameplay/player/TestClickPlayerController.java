package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.model.gameplay.card.Card;

// TODO remove, just for testing
/*
 * To use, add TestClickPlayerController.get() as both controllers in Game Controller. Then, the
 * next play can be chosen in 2 ways. Either by adding the instance as a listener:
 *      actor.addListener(TestClickPlayerController.get());
 * Or by setting the chosen play directly, e.g. using a custom listener:
 *      actor.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TestClickPlayerController.setPlay(Optional.of(card));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
 */
public class TestClickPlayerController extends ClickPlayerController {
    private static final TestClickPlayerController instance = new TestClickPlayerController();

    private TestClickPlayerController() {}

    public static TestClickPlayerController get() {
        return instance;
    }

    public static void setPlay(Optional<Card> play) {
        instance.setChosen(play);
    }

    private synchronized void setChosen(Optional<Card> play) {
        this.chosen = play;
        played = true;
        this.notify();
    }
}
