import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class MenuState extends State{

    private Texture background;
    private Texture playBtn;
    private Texture tutorialBtn;
    private Texture soundBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("menuBackground.jng");
        // playBtn = new Texture("playBtn.png");
	    // tutorialBtn = new Texture("tutorialBtn.png");
	    // soundBtn = new Texture("soundBtn");
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, duelOfFates.WIDTH, duelOfFates.HEIGHT);
        sb.draw(playBtn, duelOfFates.WIDTH/2 - (playBtn.getWidth()/2), duelOfFates.HEIGHT/3);
        sb.draw(tutorialBtn, duelOfFates.WIDTH/2 - (tutorialBtn.getWidth()/2), duelOfFates.HEIGHT/3);
	    sb.draw(soundBtn, 0, 0);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
	    tutorialBtn.dispose();
	    soundBtn.dispose();
    }
}