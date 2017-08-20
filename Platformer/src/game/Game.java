package game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable, KeyListener{
	
	//http://www.gameart2d.com/

	private static final long serialVersionUID = 1L;
	int xOffset = 0;
	int yOffset = 0;

	public static final int WIDTH = 600;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final String NAME = "Platformer";
	public boolean running = false;
	public int tickCount = 0;
	
	private BufferedImage image;
	Graphics2D g;

	JFrame frame;
	
	private TileMap tileMap;
	private Player player;
	private Menu menu;
	private MouseInput mouseInput;
	
	public static enum STATE {
			MENU,
			LEVEL,
			SETTINGS,
			ABOUT,
			GAME,
			DEATH,
			WIN
	};
	public static STATE State = STATE.MENU;
	
	public Game(){
		
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		init();
		requestFocus();
	};
	
	public synchronized void start() {
		running = true;
		this.addKeyListener(this);
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int frames = 0;
		int ticks = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(frames + "," + ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void init(){
		
		tileMap = new TileMap("levels/level1.txt", 40);
		player = new Player(tileMap);
		player.setX(100);
		player.setY(100);
		
		menu = new Menu();
		
		mouseInput = new MouseInput();
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);
	}
	
	public void tick(){
		
		tickCount++;
		if(State==STATE.GAME){
			tileMap.tick();
			player.tick();
		}
		else if(State==STATE.MENU){
			menu.tick();
		}
	}
	
	@SuppressWarnings("static-access")
	public void render(){
		
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		
		if(State==STATE.GAME){
			tileMap.render(g);
			player.render(g);
			
			g.setColor(Color.RED);
			Font fnt0 = new Font("arial", Font.BOLD, 18);
			
			g.setFont(fnt0);
			g.drawString("Coins: " + player.coins + "/" + tileMap.totalCoins, 30, 30);
		}
		else if(State==STATE.MENU){
			menu.render(g);
		}
		else if(State==STATE.DEATH){
			Font fnt1 = new Font("arial", Font.BOLD, 30);
			
			g.setFont(fnt1);
			g.drawString("YOU DIED!", 250, 200);
		}
		else if(State==STATE.WIN){
			Font fnt1 = new Font("arial", Font.BOLD, 30);
			
			g.setFont(fnt1);
			g.drawString("YOU WON!", 250, 200);
		}
		
	}
	
	public static void main(String[] args){
		
		new Game().start();
	}

	@Override
	public void keyPressed(KeyEvent key) {
		
		int code = key.getKeyCode();
		if(code==KeyEvent.VK_LEFT){
			player.setLeft(true);
		}
		if(code==KeyEvent.VK_RIGHT){
			player.setRight(true);
		}
		if(code==KeyEvent.VK_UP){
			player.setJumping();
		}
		if(code==KeyEvent.VK_ESCAPE){
			State = STATE.MENU;
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		
		int code = key.getKeyCode();
		if(code==KeyEvent.VK_LEFT){
			player.setLeft(false);
		}
		if(code==KeyEvent.VK_RIGHT){
			player.setRight(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
		
	}
}
