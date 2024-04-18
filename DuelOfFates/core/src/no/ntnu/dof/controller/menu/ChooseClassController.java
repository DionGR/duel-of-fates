package no.ntnu.dof.controller.menu;

import com.badlogic.gdx.Gdx;

import no.ntnu.dof.controller.network.ServiceLocator;
import no.ntnu.dof.controller.network.UserService;
import no.ntnu.dof.model.communication.User;
import no.ntnu.dof.model.gameplay.playerclass.PlayerClass;
import no.ntnu.dof.view.screen.menu.ChooseClassScreen;

public class ChooseClassController implements ChooseClassScreen.ChooseClassListener {
    private final User user;

    public ChooseClassController(User user) {
        this.user = user;
    }

    @Override
    public void onClassChoice(PlayerClass playerClass) {
        user.setPlayerClassName(playerClass.getName());
        ServiceLocator.getUserService().updateUserClass(user, new UserService.UserUpdateCallback() {
            @Override
            public void onSuccess() {
                Gdx.app.log("ChooseClass", "User's player class updated to " + user.getPlayerClassName() + ".");
            }

            @Override
            public void onFailure(Exception e) {
                Gdx.app.error("ChooseClass", "Failed to update user's player class: " + e.getMessage());
            }
        });
    }
}
