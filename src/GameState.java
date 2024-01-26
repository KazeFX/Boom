
import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * The state containing the actual game interface and logic
 * 
 * @author KazeFX
 * @version 1.0
 *
 */
public class GameState extends BasicGameState{

	final static int ID = 2;
	ParticleSystem system = new ParticleSystem("ohai");	
	Music stageOne = null;
	Image bgImg = null;
	Image planeImg = null;
	Image bullImg = null;
	Image enemyImg = null;
	Image shotImg = null;
	Image ultiImg = null;
	Image enemyBullImg = null;
	Image laserImg = null;
	Image ultiShotImg = null;

	Projectile bullet = null;
	AwesomeShip plane = null;
	Background bg = new Background(system);
	float x = 400;
	float y = 400;
	
	public ArrayList<Projectile> List = new ArrayList<Projectile>();
	public ArrayList<Projectile> laserList = new ArrayList<Projectile>();
	public ArrayList<Projectile> shotList = new ArrayList<Projectile>();
	public ArrayList<Projectile> missileList = new ArrayList<Projectile>();
	public ArrayList<Projectile> enemyShotList = new ArrayList<Projectile>();
	public ArrayList<Projectile> ultiList = new ArrayList<Projectile>();
	public ArrayList<Enemy> EnemyList = new ArrayList<Enemy>();
	public LinkedList<Integer> timeList = new LinkedList<Integer>();
	long timer = 0;
	long levelTime = 0;
	long score = 0;
	int level;
	long ultiTimer = 0;
	long attackTimer = 0;
	long attackTime = 0;  
	String weaponMode = null;
	int leftCannon;
	int rightCannon;
	int cannon = 0;
	int killCount = 0;
	boolean dead = false;
	
	/**
	 * Contructor for GameState()
	 */
	public GameState() {
		super();
	}
	
	/**
	 * Returns the ID of this game state
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Init method that initializes resources used in the class.
	 */
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		bullet = new Projectile(system);
		plane = new AwesomeShip(system);
		bgImg = new Image("assets/bg.png");
		bullImg = new Image("assets/bullet.png");
		shotImg = new Image("assets/shot.png");
		planeImg = new Image("assets/ship.png");
		enemyImg = new Image("assets/ship2.png");
		ultiImg = new Image("assets/ultiImg.png");
		ultiShotImg = new Image("assets/ultiShot.png");
		enemyBullImg = new Image("assets/enemybullet.png");
		cannon = 5;
		level = 1;
		
		plane.setImage(new Image("assets/ship.png"));
		plane.setPosition(180f,500f);

		bullet.setImage(new Image("assets/bullet.png"));
		bullet.setPosition(180f, 500f);

		timer = System.currentTimeMillis();
		levelTime = System.currentTimeMillis();
		attackTimer = System.currentTimeMillis();
		score = System.currentTimeMillis();

		weaponMode = "B";
		
		stageOne = new Music("assets/stage1.ogg");
		stageOne.loop();		
	}
		
	/**
	 * Render method that draws the space ship, projectiles and background
	 */
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {

			gc.setTargetFrameRate(50);
			
			bgImg.draw(bg.getX(), bg.getY());



			if (bg.getY() > 0){
				bg.kill();
				bg.setPosition(0, -600);
				bgImg.draw(bg.getX(), bg.getY());
			}

			for (Projectile p: List){
				bullImg.draw(p.getX(), p.getY());
			}
			for (Projectile p: shotList){
				ultiImg.draw(p.getX(), p.getY());			
			}

			for (Enemy p: EnemyList){
				enemyImg.draw(p.getX(), p.getY());
			}

			for (Projectile p: ultiList){
				ultiShotImg.draw(p.getX(), p.getY());
			}

			for (Projectile p: enemyShotList){
				enemyBullImg.draw(p.getX(), p.getY());
			}
			
			for (Projectile p: laserList){
				laserImg.draw(p.getX(), p.getY());
			}

			planeImg.draw(plane.getX(), plane.getY());
				
		}
		
		/**
		 * Update method for the controllers, space ship movement and weapon fire
		 */
		public void update(GameContainer gc, StateBasedGame arg1, int arg2)
				throws SlickException {
			
			if (dead){
				System.out.println("Score: " + (killCount + (System.currentTimeMillis()-score)/1000));
				gc.exit();
			}
			Input input = gc.getInput();
			float xPos = plane.getX();
			float yPos = plane.getY();

			if ((System.currentTimeMillis() - levelTime) > 6000){
				level++;
				levelTime = System.currentTimeMillis();

			}

			if ((System.currentTimeMillis() - timer) > 6000){
				for (int i = 0; i < level; i++){

					Enemy e = new Enemy(system);
					e.setPosition((float) (Math.random()*400), (float) (Math.random()*(-400)));
					EnemyList.add(e);
					timer = System.currentTimeMillis();
				}

			}

			bg.adjustPosition(0, 4);
			for (Projectile p : laserList){
				p.setPosition(plane.getX()+12, plane.getY()-100);
			}
			boolean canHasFired = false;
			for (Enemy e: EnemyList){
				attackTime = 200 +((long) (Math.random() *3000));
				if (System.currentTimeMillis() - attackTimer > attackTime){
					Projectile shot = new Projectile(system);

					canHasFired = true;
					shot.setImage(new Image("assets/enemybullet.png"));
					shot.setPosition(e.getX()+15, e.getY()+40);
					enemyShotList.add(shot);

				}

				if (plane.getX() > e.getX()-25 && plane.getX() < e.getX()+40 && plane.getY() < e.getY() && plane.getY() > e.getY()-30)
					
					dead = true;
				e.adjustPosition(0, 3);
				if (e.getY() > 600){
					e.kill();
					EnemyList.remove(e);
					break;
				}
			}
			if(canHasFired)
				attackTimer = System.currentTimeMillis();
			
			for (Projectile p: enemyShotList){
				p.adjustPosition(0, 5);
				if (p.getY() > 600){
					enemyShotList.remove(p);
					p.kill();
					break;
				}
				for (Projectile e: enemyShotList){
					if (e.getX() > plane.getX() && e.getX() < plane.getX()+35 && e.getY() < plane.getY() && e.getY() > plane.getY()-10){
						dead = true;
					}
				}
			}
			boolean stop = false;
			for (Projectile p: List){
				if (p.getY() < -50){
					p.kill();
					List.remove(p);
					break;
				}
				p.adjustPosition(0, -5);
				for (Enemy e: EnemyList){
					if (p.getX() > e.getX() && p.getX() < e.getX() + 35 && p.getY() < e.getY() && p.getY() > e.getY() -20){
						killCount++;
						EnemyList.remove(e);
						e.kill();
						List.remove(p);
						p.kill();
						stop = true;
						break;
					}
				}
				if (stop){
					break;	
				}
			}
			for (Projectile p: shotList){
				if (p.getY() < -50){
					shotList.remove(p);
					p.kill();
					break;
				}
				p.adjustPosition(0, -5);

				for (Enemy e: EnemyList){
					if (p.getX() > e.getX() && p.getX() < e.getX() + 35 && p.getY() < e.getY() && p.getY() > e.getY() -20){
						killCount++;
						EnemyList.remove(e);
						e.kill();
						shotList.remove(p);
						p.kill();
						for (int i = 0; i < 30; i++){
							Projectile shot = new Projectile(system);
							shot.setImage(new Image("assets/shot.png"));
							shot.setPosition(p.getX(), p.getY());
							ultiList.add(shot);						
						}
						stop = true;
						break;
					}
				}
				if (stop){
					break;	
				}
			}
			int ultiDirectionY;
			int ultiDirectionX;
			for (Projectile p: ultiList){
				//ultiDirectionY = (int) (Math.random()*-50);
				ultiDirectionY = (int) ((Math.random()*25)-17);
				ultiDirectionX = (int) ((Math.random()*60)-30);
				
				p.adjustPosition(ultiDirectionX, ultiDirectionY);
				if (p.getY() < -50){
					p.kill();
					ultiList.remove(p);
					break;
				}
				
			}
			for (Particle p: ultiList){
				for (Enemy e: EnemyList){
					if (p.getX() > e.getX() && p.getX() < e.getX() + 35 && p.getY() < e.getY() && p.getY() > e.getY() -20){
						killCount++;
						EnemyList.remove(e);
						shotList.remove(p);
						ultiList.remove(p);
						p.kill();
						stop = true;
						break;
					}
				}
				if (stop){
					break;	
				}
			}

			if(input.isKeyDown(Input.KEY_ESCAPE)) {
				gc.exit();
			}

			if(input.isKeyDown(Input.KEY_LEFT)) {
				if ((xPos < 390) && (xPos > 12)) {
					plane.adjustPosition(-3, 0);
				}
			}

			if(input.isKeyDown(Input.KEY_RIGHT)) {

				if ((xPos > 10) && (xPos < 365)) {
					plane.adjustPosition(3, 0);
				}
			}

			if(input.isKeyDown(Input.KEY_UP)) {

				if ((yPos > 11) && (yPos < 600)) {
					plane.adjustPosition (0, -3);
				}
			}

			if(input.isKeyDown(Input.KEY_DOWN)) {

				if ((yPos < 560) && (yPos > 10)) {
					plane.adjustPosition(0, 3);
				}
			}

			if (input.isKeyPressed(Input.KEY_B)){
				weaponMode = "B";
			}
			if (input.isKeyPressed(Input.KEY_N)){
				weaponMode = "N";
			}

			if (input.isKeyPressed(Input.KEY_M)){
				weaponMode = "M";
			}
		
			if (input.isKeyPressed(Input.KEY_SPACE)){

				if (weaponMode.equals("B")){

					Projectile shot = new Projectile(system);
					shot.setImage(new Image("assets/bullet.png"));
					shot.setPosition(plane.getX()+ cannon, plane.getY());
					if (cannon == 25){
						cannon = 5;
					}
					else if (cannon == 5){
						cannon = 25;
					}

					List.add(shot);

				}
				
				if (weaponMode.equals("N")){
					Projectile shot = new Projectile(system);
					Projectile shot2 = new Projectile(system);


					shot2.setImage(new Image("assets/bullet.png"));
					shot.setImage(new Image("assets/bullet.png"));

					shot.setPosition(plane.getX()+5, plane.getY());
					shot2.setPosition(plane.getX()+25, plane.getY());
					List.add(shot);
					List.add(shot2);
				}
				
				if (weaponMode.equals("M")){
					Projectile shot = new Projectile(system);

					shot.setImage(new Image("assets/ultiImg.png"));
					shot.setPosition(plane.getX(), plane.getY());
					shotList.add(shot);
				}
			}
						
		}
	
}