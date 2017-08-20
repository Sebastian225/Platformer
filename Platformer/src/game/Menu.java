package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu{
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = WIDTH / 12 * 9;

	BufferedImage image;
	BufferedImage arrowLeft;
	BufferedImage arrowRight;
	BufferedImage logo;
	
	static int arrowLeftX = 50;
	static int arrowLeftY = 50;
	static int arrowRightX;
	static int arrowRightY;
	
	Rectangle playBtn = new Rectangle((WIDTH-100)/2, 150, 100, 50);
	Rectangle settingsBtn = new Rectangle((WIDTH-150)/2, 220, 150, 50);
	Rectangle aboutBtn = new Rectangle((WIDTH-100)/2, 290, 100, 50);
	Rectangle quitBtn = new Rectangle((WIDTH-100)/2, 360, 100, 50);
	
	static boolean isPointing;
	
	Font fnt0 = new Font("arial", Font.BOLD, 28);
	
	public Menu() {
		
		try {
			image = ImageIO.read(new File("img/BG/BG.png"));
			arrowLeft = ImageIO.read(new File("img/arrow_left.png"));
			arrowRight = ImageIO.read(new File("img/arrow_right.png"));
			logo = ImageIO.read(new File("img/logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics2D g){
		
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g.drawImage(logo, (WIDTH-330)/2, 7, 330, 150, null);
		
		//g.draw(playBtn);
		//g.draw(settingsBtn);
		//g.draw(aboutBtn);
		//g.draw(quitBtn);
		g.setColor(new Color(14,84,40));
		
		g.setFont(fnt0);
		g.drawString("PLAY", playBtn.x+15, playBtn.y+36);
		g.drawString("SETTINGS", settingsBtn.x+5, settingsBtn.y+36);
		g.drawString("ABOUT", aboutBtn.x, aboutBtn.y+36);
		g.drawString("QUIT", quitBtn.x+15, quitBtn.y+36);
		if(isPointing){
			g.drawImage(arrowLeft, arrowLeftX, arrowLeftY, 50, 50, null);
			g.drawImage(arrowRight, arrowRightX, arrowRightY, 50, 50, null);
		}
	}
	
}
