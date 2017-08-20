package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Game.STATE;

public class Player {
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private int width;
	private int manWidth;
	private int height;
	
	private boolean left;
	private boolean right;
	private boolean jumping;
	private boolean falling;
	public boolean isDead;
	public boolean hasFinished;
	
	private double moveSpeed;
	private double maxSpeed;
	private double maxFallingSpeed;
	private double stopSpeed;
	private double jumpStart;
	private double gravity;
	
	private TileMap tileMap;
	
	private boolean topLeft;
	private boolean topRight;
	private boolean bottomLeft;
	private boolean bottomRight;
	
	private boolean topLeftCoin;
	private boolean topRightCoin;
	private boolean bottomLeftCoin;
	private boolean bottomRightCoin;
	
	private boolean topLeftSpike;
	private boolean topRightSpike;
	private boolean bottomLeftSpike;
	private boolean bottomRightSpike;
	
	private boolean topLeftWin;
	private boolean topRightWin;
	private boolean bottomLeftWin;
	private boolean bottomRightWin;
	
	BufferedImage img;
	public static int coins;
	
	private Animation walkRight;
	private Animation walkLeft;
	private Animation stand;
	private Animation animation;

	public Player(TileMap tm) {
		
		tileMap = tm;
		coins = 0;
		
		width = 50;
		manWidth = 45*width/128;
		height = 50;
		
		moveSpeed = 0.6;
		maxSpeed = 4.2;
		maxFallingSpeed = 12;
		stopSpeed = 0.30;
		jumpStart = -11.0;
		gravity = 0.64;
		
		try {
		    img = ImageIO.read(new File("img/dude.png"));
		} catch (IOException e) {
		}
		walkRight = new Animation(2, 
				img.getSubimage(128*4, 128*0, 128, 128), 
				img.getSubimage(128*5, 128*0, 128, 128), 
				img.getSubimage(128*6, 128*0, 128, 128), 
				img.getSubimage(128*7, 128*0, 128, 128), 
				img.getSubimage(128*8, 128*0, 128, 128),
				img.getSubimage(128*9, 128*0, 128, 128),
				img.getSubimage(128*10, 128*0, 128, 128),
				img.getSubimage(128*0, 128*1, 128, 128),
				img.getSubimage(128*1, 128*1, 128, 128), 
				img.getSubimage(128*2, 128*1, 128, 128), 
				img.getSubimage(128*3, 128*1, 128, 128), 
				img.getSubimage(128*4, 128*1, 128, 128), 
				img.getSubimage(128*5, 128*1, 128, 128), 
				img.getSubimage(128*6, 128*1, 128, 128), 
				img.getSubimage(128*7, 128*1, 128, 128), 
				img.getSubimage(128*8, 128*1, 128, 128),
				img.getSubimage(128*9, 128*1, 128, 128),
				img.getSubimage(128*10, 128*1, 128, 128),
				img.getSubimage(128*0, 128*2, 128, 128),
				img.getSubimage(128*1, 128*2, 128, 128), 
				img.getSubimage(128*2, 128*2, 128, 128));
		walkLeft = new Animation(2, 
				img.getSubimage(128*6, 128*5, 128, 128),
				img.getSubimage(128*7, 128*5, 128, 128), 
				img.getSubimage(128*8, 128*5, 128, 128),
				img.getSubimage(128*9, 128*5, 128, 128),
				img.getSubimage(128*10, 128*5, 128, 128),
				img.getSubimage(128*0, 128*6, 128, 128),
				img.getSubimage(128*1, 128*6, 128, 128), 
				img.getSubimage(128*2, 128*6, 128, 128), 
				img.getSubimage(128*3, 128*6, 128, 128), 
				img.getSubimage(128*4, 128*6, 128, 128), 
				img.getSubimage(128*5, 128*6, 128, 128), 
				img.getSubimage(128*6, 128*6, 128, 128), 
				img.getSubimage(128*7, 128*6, 128, 128), 
				img.getSubimage(128*8, 128*6, 128, 128),
				img.getSubimage(128*9, 128*6, 128, 128),
				img.getSubimage(128*10, 128*6, 128, 128),
				img.getSubimage(128*0, 128*7, 128, 128),
				img.getSubimage(128*1, 128*7, 128, 128),
				img.getSubimage(128*2, 128*7, 128, 128),
				img.getSubimage(128*3, 128*7, 128, 128));
		stand = new Animation(2, img.getSubimage(128*0, 128*0, 128, 128));
	
		animation = stand;
	}
	
	public void setX(int i){
		x = i;
	}
	
	public void setY(int i){
		y = i;
	}
	
	public void setLeft(boolean b){
		left = b;
	}
	
	public void setRight(boolean b){
		right = b;
	}
	
	public void setJumping(){
		if(!falling){
			jumping = true;
		}
	}
	
	public boolean collision(Rectangle one, Rectangle two){
		return one.intersects(two);
	}
	
	private void calculateCorners(double x, double y){
		int leftTile = tileMap.getColTile((int) (x - manWidth / 2));
		int rightTile = tileMap.getColTile((int) (x + manWidth / 2) - 1);
		int topTile = tileMap.getRowTile((int) (y - height / 2));
		int bottomTile = tileMap.getRowTile((int) (y + height / 2) - 1);
		topLeft = tileMap.getTile(topTile, leftTile) == 1;
		topRight = tileMap.getTile(topTile, rightTile) == 1;
		bottomLeft = tileMap.getTile(bottomTile, leftTile) == 1;
		bottomRight = tileMap.getTile(bottomTile, rightTile) == 1;
		
		topLeftCoin = tileMap.getTile(topTile, leftTile) == 2;
		topRightCoin = tileMap.getTile(topTile, rightTile) == 2;
		bottomLeftCoin = tileMap.getTile(bottomTile, leftTile) == 2;
		bottomRightCoin = tileMap.getTile(bottomTile, rightTile) == 2;
		
		topLeftSpike = tileMap.getTile(topTile, leftTile) == 3;
		topRightSpike = tileMap.getTile(topTile, rightTile) == 3;
		bottomLeftSpike = tileMap.getTile(bottomTile, leftTile) == 3;
		bottomRightSpike = tileMap.getTile(bottomTile, rightTile) == 3;
		
		topLeftWin = tileMap.getTile(topTile, leftTile) == 4;
		topRightWin = tileMap.getTile(topTile, rightTile) == 4;
		bottomLeftWin = tileMap.getTile(bottomTile, leftTile) == 4;
		bottomRightWin = tileMap.getTile(bottomTile, rightTile) == 4;
	}
	
	public void tick(){
		
		//movement
		if(left){
			dx -= moveSpeed;
			animation = walkLeft;
			if(dx<-maxSpeed){
				dx = -maxSpeed;
			}
		}
		else if(right){
			dx += moveSpeed;
			animation = walkRight;
			if(dx>maxSpeed){
				dx = maxSpeed;
			}
		}
		else{
			if(dx>0){
				dx -= stopSpeed;
				animation = stand;
				if(dx<0){
					dx = 0;
				}
			}
			else if(dx<0){
				dx += stopSpeed;
				animation = stand;
				if(dx>0){
					dx = 0;
				}
			}
		}
		
		if(jumping){
			dy = jumpStart;
			falling = true;
			jumping = false;
		}
		
		if(falling){
			dy += gravity;
			animation = stand;
			if(dy>maxFallingSpeed){
				dy = maxFallingSpeed;
			}
		}
		else{
			dy = 0;
		}
		
		//check collision
		int currCol = tileMap.getColTile((int) x);
		int currRow = tileMap.getRowTile((int) y);
		
		double toX = x+dx;
		double toY = y+dy;
		
		double tempX = x;
		double tempY = y;
		
		calculateCorners(x, toY);
		if(dy<0){
			if(topLeft || topRight){
				dy = 0;
				tempY = currRow * tileMap.getTileSize() + height / 2;
			}
			else{
				tempY += dy;
			}
			
			spike();
			win();
		}
		if(dy>0){
			if(bottomLeft || bottomRight){
				dy = 0;
				falling = false;
				tempY = (currRow + 1) * tileMap.getTileSize() - height / 2;
			}
			else{
				tempY += dy; 
			}

			spike();
			win();
		}
		
		calculateCorners(toX, y);
		if(dx<0){
			if(topLeft || bottomLeft){
				dx = 0;
				tempX = currCol * tileMap.getTileSize() + manWidth / 2;
			}
			else{
				tempX += dx;
			}
			
			spike();
			win();
		}
		if(dx>0){
			if(topRight || bottomRight){
				dx = 0;
				tempX = (currCol + 1) * tileMap.getTileSize() - manWidth / 2;
			}
			else{
				tempX += dx;
			}
			
			spike();
			win();
		}
		
		if(!falling){
			calculateCorners(x, y+1);
			if(!bottomLeft && !bottomRight){
				falling = true;
			}
		}
		
		x = tempX;
		y = tempY;
		
		if(dx<0 || dx>0 || dy>0 || dy<0){
			checkCoin();
		}
		
		animation.runAnimation();
		
		//move map
		tileMap.setX((int) (Game.WIDTH / 2 - x));
		tileMap.setY((int) (Game.HEIGHT / 2 - y));
		tileMap.fixBounds();
	}
	
	public void coin(){
		
		int leftTile = tileMap.getColTile((int) (x - manWidth / 2));
		int rightTile = tileMap.getColTile((int) (x + manWidth / 2) - 1);
		int topTile = tileMap.getRowTile((int) (y - height / 2));
		int bottomTile = tileMap.getRowTile((int) (y + height / 2) - 1);
		
		if(topLeftCoin && topRightCoin){
			
			if(tileMap.map[topTile][leftTile] == 2){
				tileMap.map[topTile][leftTile] = 0;
			}
			if(tileMap.map[topTile][rightTile] == 2){
				tileMap.map[topTile][rightTile] = 0;
			}
			//System.out.println(topTile + " " + leftTile);
			//System.out.println(topTile + " " + rightTile);
			coins +=1;
		}
		
		if(bottomLeftCoin && bottomRightCoin){
			if(tileMap.map[bottomTile][leftTile] == 2){
				tileMap.map[bottomTile][leftTile] = 0;
			}
			if(tileMap.map[bottomTile][rightTile] == 2){
				tileMap.map[bottomTile][rightTile] = 0;
			}
			coins +=1;
		}
		
	}
	
	public void checkCoin(){
		for(int i = 0;i<tileMap.totalCoins;i++){
			if(collision(tileMap.coins[i], this.getBounds())){
				int row = ((int)tileMap.coins[i].getX()-tileMap.getX())/tileMap.getTileSize();
				int col = ((int)tileMap.coins[i].getY()-tileMap.getY())/tileMap.getTileSize();
				//System.out.println(col + " @@@ " + row);
				tileMap.map[col][row] = 0;
				coins+=1;
				if(coins>10){
					coins-=1;
				}
			}
		}
	}
	
	public void spike(){
		
		if(topLeftSpike && topRightSpike){
			
			if(!isDead){
				System.out.println("u died");
				isDead = true;
				Game.State = STATE.DEATH;
			}
		}
		
		if(bottomLeftSpike && bottomRightSpike){
			
			if(!isDead){
				System.out.println("u died");
				isDead = true;
				Game.State = STATE.DEATH;
			}
		}
		
	}
	
	public void win(){
		
		if(topLeftWin && topRightWin){
			
			if(!hasFinished && !isDead && coins==tileMap.totalCoins){
				System.out.println("u won");
				Game.State = STATE.WIN;
				hasFinished = true;
			}
		}
		
		if(bottomLeftWin && bottomRightWin){
			
			if(!hasFinished && !isDead && coins==tileMap.totalCoins){
				System.out.println("u won");
				Game.State = STATE.WIN;
				hasFinished = true;
			}
		}
		
	}
	
	public Rectangle getBounds(){
		int tx = tileMap.getX();
		int ty = tileMap.getY();
		return new Rectangle((int) (tx + x - width/2+15),
				(int) (ty + y - height/2),  manWidth, height);
	}
	
	public void render(Graphics2D g){
		
		int tx = tileMap.getX();
		int ty = tileMap.getY();
		
		g.draw(getBounds());
		
		animation.drawAnimation(g, 
				(int) (tx + x - width/2),
				(int) (ty + y - height/2), 
				width, 
				height);
	}
}
