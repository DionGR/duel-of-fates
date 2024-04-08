package no.ntnu.dof.controller.gameplay.player;

import java.util.Optional;

import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

public class BotTutorialController implements PlayerController{
    public Optional<Card> choosePlay(){
        return Optional.of(AttackCard.builder()
                .name("Card1")
                .cost(new Mana(3))
                .opponentEffectName("damage")
                .build());
    }
}
