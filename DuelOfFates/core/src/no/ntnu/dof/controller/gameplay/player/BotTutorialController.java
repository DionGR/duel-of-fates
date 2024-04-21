package no.ntnu.dof.controller.gameplay.player;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

public class BotTutorialController implements PlayerController {
    private final ArrayList<Card> hand = new ArrayList<>();
    private final Random random = new Random();
    private boolean played = false;

    public BotTutorialController() {
        hand.add(Card.builder()
                .name("attackCard_8")
                .cost(new Mana(3))
                .opponentEffectName("damageEffect_8")
                .build());
        hand.add(Card.builder()
                .name("attackCard_4")
                .cost(new Mana(1))
                .opponentEffectName("damageEffect_4")
                .build());
    }

    public Optional<Card> choosePlay(long timeout) throws InterruptedException {
        sleep(1000);

        if (!played) {
            played = true;
            return Optional.of(hand.get(random.nextInt(hand.size())));
        } else {
            played = false;
            return Optional.empty();
        }
    }
}
