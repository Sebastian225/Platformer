package level.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile{
	
	int x, y;
	int oX, oY;
	Editor game;
	int tileId;
	Rectangle bounding;
	
	Color colors[] = {Color.WHITE, Color.BLUE};
	
	public Tile(int x, int y, int tileId) {
		this.oX = x;
		this.oY = y;
		this.tileId = tileId;
		bounding = new Rectangle(oX, oY, 32, 32);
		bounding.setBounds(oX, oY, 32, 32);
	}
	
	public void load(){
		
	}

	public void tick(Editor game){
		this.game = game;		
		
		x = oX + game.xOffset;
		y = oY + game.yOffset;
	}
	
	public void render(Graphics g){
		
		if(tileId==0 || tileId==1){
			g.setColor(colors[tileId]);
			g.fillRect(x, y, 32, 32);
		}
		else if(tileId==2){
			g.drawImage(game.coin, x, y, 32, 32, null);
		}
		else if(tileId==3){
			g.drawImage(game.spikes, x, y, 32, 32, null);
		}
		else if(tileId==4){
			g.drawImage(game.sign, x, y, 32, 32, null);
		}
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 32, 32);
	}
}
