package no.ntnu.dof.model.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.card.AttackCard;
import no.ntnu.dof.model.gameplay.card.Card;
import no.ntnu.dof.model.gameplay.stats.mana.Mana;

@Module
public class CardModule {

        @Provides
        @Named("attackCard_2")
        public Card provideAttackCard2() {
            return AttackCard.builder()
                    .name("attackCard_2")
                    .cost(new Mana(2))
                    .opponentEffectName("damageEffect_2")
                    .build();
        }

    @Provides
    @Named("healthCard_2")
    public Card provideHealthCard2() {
        return AttackCard.builder()
                .name("attackCard_2")
                .cost(new Mana(2))
                .hostEffectName("healthEffect_2")
                .build();
    }

    @Provides
    @Named("attackCard_Poison3")
    public Card provideAttackCardPoison3() {
        return AttackCard.builder()
                .name("attackCard_Poison3")
                .cost(new Mana(3))
                .opponentEffectName("poisonEffect_3")
                .build();
    }

    @Provides
    @Named("doubleStrikeCard")
    public Card provideDoubleStrikeCard() {
        return AttackCard.builder()
                .name("doubleStrikeCard")
                .cost(new Mana(4))
                .hostEffectName("doubleStrikeEffect")
                .build();
    }

    @Provides
    @Named("freezeCard")
    public Card provideFreezeCard() {
        return AttackCard.builder()
                .name("freezeCard")
                .cost(new Mana(5))
                .opponentEffectName("freezeEffect")
                .build();
    }

    @Provides
    @Named("armorBoostCard_5")
    public Card provideArmorBoostCard5() {
        return DefenseCard.builder()
                .name("armorBoostCard_5")
                .cost(new Mana(3))
                .hostEffectName("armorBoostEffect_5")
                .build();
    }

}
