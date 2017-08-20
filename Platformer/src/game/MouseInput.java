package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener{

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		/**
		*Rectangle playBtn = new Rectangle((WIDTH-100)/2, 150, 100, 50);
		Rectangle settingsBtn = new Rectangle((WIDTH-150)/2, 220, 150, 50);
		Rectangle aboutBtn = new Rectangle((WIDTH-100)/2, 290, 100, 50);
		Rectangle quitBtn = new Rectangle((WIDTH-100)/2, 360, 100, 50);
		*/
		
		//play button
		if(mx>=(Menu.WIDTH-100)/2 && mx<=(Menu.WIDTH-100)/2+100 && my>=150 && my<=150+50){			
			Game.State = Game.STATE.GAME;
		}
		if(mx>=(Menu.WIDTH-150)/2 && mx<=(Menu.WIDTH-150)/2+150 && my>=220 && my<=220+50){
			//Game.State = Game.STATE.SETTINGS;
		}
		if(mx>=(Menu.WIDTH-100)/2 && mx<=(Menu.WIDTH-100)/2+100 && my>=290 && my<=290+50){
			//Game.State = Game.STATE.ABOUT;
		}
		if(mx>=(Menu.WIDTH-100)/2 && mx<=(Menu.WIDTH-100)/2+100 && my>=360 && my<=360+50){
			System.exit(1);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		if(mx>=(Menu.WIDTH-100)/2 && mx<=(Menu.WIDTH-100)/2+100 && my>=150 && my<=150+50){			
			Menu.isPointing = true;
			Menu.arrowLeftX = (Menu.WIDTH-100)/2 - 50;
			Menu.arrowLeftY = 150;
			Menu.arrowRightX = (Menu.WIDTH-100)/2 + 100;
			Menu.arrowRightY = 150;
			
		}
		else if(mx>=(Menu.WIDTH-150)/2 && mx<=(Menu.WIDTH-150)/2+150 && my>=220 && my<=220+50){
			Menu.isPointing = true;
			Menu.arrowLeftX = (Menu.WIDTH-150)/2 - 50;
			Menu.arrowLeftY = 220;
			Menu.arrowRightX = (Menu.WIDTH-150)/2 + 150;
			Menu.arrowRightY = 220;
		}
		else if(mx>=(Menu.WIDTH-100)/2 && mx<=(Menu.WIDTH-100)/2+100 && my>=290 && my<=290+50){
			Menu.isPointing = true;
			Menu.arrowLeftX = (Menu.WIDTH-100)/2 - 50;
			Menu.arrowLeftY = 290;
			Menu.arrowRightX = (Menu.WIDTH-100)/2 + 100;
			Menu.arrowRightY = 290;
		}
		else if(mx>=(Menu.WIDTH-100)/2 && mx<=(Menu.WIDTH-100)/2+100 && my>=360 && my<=360+50){
			Menu.isPointing = true;
			Menu.arrowLeftX = (Menu.WIDTH-100)/2 - 50;
			Menu.arrowLeftY = 360;
			Menu.arrowRightX = (Menu.WIDTH-100)/2 + 100;
			Menu.arrowRightY = 360;
		}
		else{
			Menu.isPointing = false;
		}
	}

}
