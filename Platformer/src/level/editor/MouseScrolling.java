package level.editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseScrolling implements MouseMotionListener {
	
	//int width = Game.fullScreenWidth;
	//int height = Game.fullScreenHeight;

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	public void mouseMoved(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		if(mouseX == 0)
			Editor.left = true;
		if(mouseX == (int) Editor.fullScreenWidth - 4)
			Editor.right = true;
		if(mouseY == 0)
			Editor.up = true;
		if(mouseY == (int) Editor.fullScreenHeight - 5)
			Editor.down = true;
		
		if(mouseX > 0)
			Editor.left = false;
		if(mouseX < (int) Editor.fullScreenWidth - 4)
			Editor.right = false;
		if(mouseY > 0)
			Editor.up = false;
		if(mouseY < (int) Editor.fullScreenHeight - 5)
			Editor.down = false;
	}

}
