import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.CrossStateTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.Input;


/**
 * The game state containing the main menu.
 * 
 * @author KazeFX
 * @version 1.0
 *
 */
public class MainMenuState extends BasicGameState {


	public static final int ID = 1;
	Image mainMenuImg = null;
	MainMenu mainMenu = null;
	private StateBasedGame game;
	GameState gameState = new GameState();
	
	@Override
	/**
	 * returns the ID of this game state
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Initializes the main menu image
	 */
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		this.game = game;
		mainMenuImg = new Image("assets/boomTitle.png");
		
	}

	/**
	 * Draws the main menu
	 */
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		mainMenuImg.draw(0,0);
		
	}

	/**
	 * Empty method
	 */
	public void update(GameContainer gc, StateBasedGame game, int g) {			
	}

	/**
	 * Method that exits this state and starts the main game state once ENTER has been pressed
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ENTER) {
			GameState nextState = (GameState) game.getState(gameState.ID);
			
			final long start = System.currentTimeMillis();
			CrossStateTransition t = new CrossStateTransition(nextState) {				
				public boolean isComplete() {
					return (System.currentTimeMillis() - start) > 2000;
				}

				public void init(GameState firstState, GameState secondState) {
				}

				public void init(org.newdawn.slick.state.GameState arg0,
						org.newdawn.slick.state.GameState arg1) {	
				}
			};			
			game.enterState(gameState.ID, t, new FadeOutTransition(Color.black));
		}
		
	}
	
}
