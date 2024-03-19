package no.ntnu.dof.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import no.ntnu.dof.view.Image;

import java.util.ArrayList;
import java.util.List;

public class Fight extends GameState {
    /**
     This class is the base class for all the states in the game. It is an abstract class, which means that it cannot be instantiated.
     It contains the methods that are commun in all the states.
     **/
    protected GameStateManager gsm; //This is the GameStateManager object to handle states
    private Vector2 TouchPos;

    private List<Image> hand;
    private Image Card_back = new Image("./assets/Card_back.png", 0.35f);
    private int  start_hand;
    private int end_hand;

    private Image Player1 = new Image("./assets/Player.png", 0.30f);
    private Image Player2 = new Image("./assets/Player.png", 0.30f);

    private Image Mana_pool = new Image("./assets/Mana.png", 0.10f);


    /**
     * Constructor for State, it just initialized variables
     */
    public Fight(){
        this.gsm = GameStateManager.getInstance();
        this.TouchPos = new Vector2();
        hand = new ArrayList<Image>();
        for (int i = 0; i < 5; i++)
        {
            hand.add(new Image("./assets/Card.png", 0.35f));
        }
        if(Card_back==null)
        {
            System.out.println("Card_back is null");
        }
    }

    /**
     * This method handles the input of the user
     */
    public void handleInput()
    {
        //This method is used to handle the input of the user
        if (Gdx.input.justTouched()) {
            System.out.println(Gdx.input.getX());
            System.out.println(Gdx.graphics.getHeight() - Gdx.input.getY());
            TouchPos.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            System.out.println("Start hand = "+start_hand);
            if (TouchPos.x > start_hand && TouchPos.x < end_hand && TouchPos.y < hand.get(0).getHeight()) {
                DisplayCard();
            }
        }

    }

    /**
     * This method updates the state
     *
     * @param dt This is the time between frames
     */
    public void update(float dt) {
        //This method is used to update the state

        start_hand = Gdx.graphics.getWidth()/2- hand.get(0).getWidth()/6 - 2*hand.get(0).getWidth()/3*hand.size()/2;
        end_hand = start_hand + hand.size()*(2*hand.get(0).getWidth()/3);

        handleInput();
    }

    /**
     * This method renders the image
     * @param spriteBatch This is the spriteBatch object to render
     */
    public void render(SpriteBatch spriteBatch)
    {
        //This method is used to render the image
        spriteBatch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);



        for(int i = 0; i < hand.size(); i++)
        {
            spriteBatch.draw(hand.get(0).getImg(),  start_hand + ((float) 2*hand.get(0).getWidth()/3)*(i), 0, hand.get(0).getWidth(), hand.get(0).getHeight());
        }

        spriteBatch.draw(Card_back.getImg(), 0, 0, Card_back.getWidth(), Card_back.getHeight());
        spriteBatch.draw(Card_back.getImg(), Gdx.graphics.getWidth()-Card_back.getWidth(), 0, Card_back.getWidth(), Card_back.getHeight());

        spriteBatch.draw(Player1.getImg(), (float) (Gdx.graphics.getWidth()/4-Player1.getWidth()/2), (float) (Gdx.graphics.getHeight()*0.6), Player1.getWidth(), Player1.getHeight());
        spriteBatch.draw(Player2.getImg(), (float) (3*Gdx.graphics.getWidth()/4-Player2.getWidth()/2), (float) (Gdx.graphics.getHeight()*0.6), Player2.getWidth(), Player2.getHeight());

        spriteBatch.draw(Mana_pool.getImg(), (float) (Gdx.graphics.getWidth()/4-Player1.getWidth()/2-Mana_pool.getWidth()*1.5), (float) (Gdx.graphics.getHeight()*0.6+Player1.getHeight()/2-Mana_pool.getHeight()), Mana_pool.getWidth(), Mana_pool.getHeight());
        spriteBatch.draw(Mana_pool.getImg(), (float) (3*Gdx.graphics.getWidth()/4+Mana_pool.getWidth()*1.5), (float) (Gdx.graphics.getHeight()*0.6+Player2.getHeight()/2-Mana_pool.getHeight()), Mana_pool.getWidth(), Mana_pool.getHeight());

        spriteBatch.end();
    }

    /**
     * This method disposes object of the state
     */
    public void dispose() {

    }

    public void DisplayCard()
    {
        System.out.println("Card displayed");
        int num_card = (int) ((TouchPos.x - start_hand) / ((float) 2*hand.get(0).getWidth()/3));
        System.out.println("Card number = "+num_card);
    }

}
