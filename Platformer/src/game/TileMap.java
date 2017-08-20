package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileMap {
	
	private int x;
	private int y;
	
	private int tileSize;
	public int[][] map;
	public Rectangle[][] tile;
	public int mapWidth;
	public int mapHeight;
	public int totalCoins;
	public Rectangle[] coins;
	private int coinIndex = 0;
	
	private BufferedImage image;
	private BufferedImage coin;
	private BufferedImage spikes;
	private BufferedImage sign;
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = WIDTH / 12 * 9;
	
	public TileMap(String s, int tileSize){
		
		this.tileSize = tileSize;
		try{
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(s));
			
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			
			map = new int[mapHeight][mapWidth];
			tile = new Rectangle[mapHeight][mapWidth];
			
			String delimiters = "\\s+";
			for(int row = 0;row<mapHeight;row++){
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for(int col = 0;col<mapWidth;col++){
					map[row][col] = Integer.parseInt(tokens[col]);
					if(map[row][col]==2){
						totalCoins+=1;
					}
				}
			}
			coins = new Rectangle[totalCoins];
		}
		catch(Exception e){
			
		}
		
		try {
			image = ImageIO.read(new File("img/BG/BG.png"));
			coin = ImageIO.read(new File("img/coin.png"));
			spikes = ImageIO.read(new File("img/spikes.png"));
			sign = ImageIO.read(new File("img/Object/sign.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getColTile(int x){
		return x / tileSize;
	}
	
	public int getRowTile(int y){
		return y / tileSize;
	}
	
	public int getTileSize(){
		return tileSize;
	}
	
	public int getTile(int row, int col){
		return map[row][col];
	}
	
	public void setX(int i){
		x = i;
	}
	
	public void setY(int i){
		y = i;
	}
	
	public void fixBounds(){
		
		if(x<WIDTH-mapWidth*tileSize){
			x = WIDTH-mapWidth*tileSize;
		}
		if(y<HEIGHT-mapHeight*tileSize){
			y = HEIGHT-mapHeight*tileSize;
		}
		
		if(x>0){
			x = 0;
		}
		if(y>0){
			y = 0;
		}
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics2D g){
		
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		
		for(int row = 0;row<mapHeight;row++){
			for(int col = 0;col<mapWidth;col++){
				int rc = map[row][col];
				if(rc==0){
					//blank spot
				}
				else if(rc==1){
					g.setColor(new Color(5, 56, 56));
					g.fillRect(x + col * tileSize,y + row * tileSize, tileSize, tileSize);
					g.setColor(Color.WHITE);
					g.drawRect(x + col * tileSize,y + row * tileSize, tileSize, tileSize);
				}
				else if(rc==2){
					g.drawImage(coin, x + col * tileSize+7,y + row * tileSize+7, tileSize-15, tileSize-15, null);
					coins[coinIndex] = new Rectangle(x + col * tileSize+7,y + row * tileSize+7, tileSize-15, tileSize-15);
					g.draw(coins[coinIndex]);
					coinIndex+=1;
					if(Player.coins==totalCoins && coinIndex==1){
						coinIndex = 0;
					}
					if(coinIndex==totalCoins-Player.coins){
						coinIndex = 0;
					}
				}
				else if(rc==3){
					g.drawImage(spikes, x + col * tileSize,y + row * tileSize+5, tileSize, tileSize, null);
				}
				else if(rc==4){
					g.drawImage(sign, x + col * tileSize,y + row * tileSize, tileSize, tileSize, null);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
					g.setColor(Color.black);
				    g.drawString("Finish", x + col * tileSize+3, y + row * tileSize+19);
				}
			}
		}
		for(int row = 0;row<mapHeight;row++){
			for(int col = 0;col<mapWidth;col++){
				tile[row][col] = new Rectangle(x+col*tileSize, y+row*tileSize, tileSize, tileSize);
				//g.draw(tile[row][col]);
			}
	
		}
	}	
}
