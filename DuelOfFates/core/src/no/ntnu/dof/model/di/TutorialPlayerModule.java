package no.ntnu.dof.model.di;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import no.ntnu.dof.model.gameplay.player.Player;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.model.gameplay.Game;
import no.ntnu.dof.view.screens.game.TutorialScreen;

@Module(includes = {PlayerClassModule.class})
public class TutorialPlayerModule {

    @Provides
    @Named("hostTutorialPlayer")
    public Player provideHostTutorialPlayer(@Named("warriorPlayerClass") PlayerClass playerClass) {
        return Player.builder()
                .name("host")
                .playerClass(playerClass)
                .build();
    }

    @Provides
    @Named("botTutorialPlayer")
    public Player provideBotTutorialPlayer(@Named("warriorPlayerClass") PlayerClass playerClass) {
        return Player.builder()
                .name("bot")
                .playerClass(playerClass)
                .build();
    }

    @Provides
    @Named("tutorialGame")
    public Game provideTutorialGame(@Named("hostTutorialPlayer") Player host,
                                    @Named("botTutorialPlayer") Player bot) {
        return new Game(host, bot);
    }

    @Provides
    @Named("tutorialScreen")
    public TutorialScreen provideTutorialScreen(@Named("tutorialGame") Game game) {
        return new TutorialScreen(game);
    }
}
