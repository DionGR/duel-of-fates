package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

public class BotTutorialController implements PlayerController {
    private boolean played = false;
    public Optional<Card> choosePlay(long timeout) {
        if(!played){
            played = true;
            return Optional.of(Card.builder()
                    .name("attackCard_8")
                    .cost(new Mana(3))
                    .opponentEffectName("damageEffect_8")
                    .build());
        }
        else {
            played = false;
            return Optional.empty();
        }
    }
}
