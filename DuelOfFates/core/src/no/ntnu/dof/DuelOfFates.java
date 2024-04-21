package no.ntnu.dof;

import lombok.Getter;
import lombok.Setter;
import no.ntnu.dof.controller.application.ScreenController;
import no.ntnu.dof.controller.application.SoundController;
import no.ntnu.dof.model.communication.User;

public class DuelOfFates extends com.badlogic.gdx.Game {
    @Getter
    @Setter
    private User currentUser;

    public DuelOfFates() {
    }

    @Override
    public void create() {
        // Initialize first screen and ScreenManager
        ScreenController.initialize(this);
        ScreenController.transitionToLogin();
    }

    @Override
    public void render() {
        super.render(); // Important to call this to ensure the screen is rendered
    }

    @Override
    public void dispose() {
        ScreenController.dispose();
        SoundController.getInstance().dispose();
        super.dispose(); // Dispose the screen and other resources
    }
}
