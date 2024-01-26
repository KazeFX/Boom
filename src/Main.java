

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Main class that controls the states and boots the game
 *
 * @author KazeFX
 */
public class Main extends StateBasedGame {

	/**
	 * Creates a new Main object
	 */
	public Main() {
		super("BOOM!");
	}
	
	/**
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
	 */
	public void initStatesList(GameContainer gc) {
		addState(new MainMenuState());
		addState(new GameState());
	}
	
	/**
	 * Main method
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new Main());
			container.setDisplayMode(400,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
