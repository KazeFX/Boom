import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Helper class that extends Particle
 * 
 * @author KazeFX
 * @version 1.0
 *
 */
public class MainMenu extends Particle{
	
	ParticleSystem system = new ParticleSystem("menu");
	
	

	public MainMenu(ParticleSystem engine) {
		super(engine);
		// TODO Auto-generated constructor stub
	}
}
