import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleSystem;


public class Projectile extends Particle {
	
	Image bullImg = null;
	public Projectile(ParticleSystem engine) throws SlickException {
		super(engine);
		bullImg = new Image("assets/bullet.png");
		// TODO Auto-generated constructor stub
	}
				
}
