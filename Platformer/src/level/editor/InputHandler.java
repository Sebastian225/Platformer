package level.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {
	
	Editor editor;
	
	public InputHandler(Editor editor){
		editor.addMouseListener(this);
		this.editor = editor;
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_A)
			Editor.left = true;
		if(keyCode == KeyEvent.VK_D)
			Editor.right = true;
		if(keyCode == KeyEvent.VK_W)
			Editor.up = true;
		if(keyCode == KeyEvent.VK_S)
			Editor.down = true;
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		//move map
		if(keyCode == KeyEvent.VK_A)
			Editor.left = false;
		if(keyCode == KeyEvent.VK_D)
			Editor.right = false;
		if(keyCode == KeyEvent.VK_W)
			Editor.up = false;
		if(keyCode == KeyEvent.VK_S)
			Editor.down = false;
		//change tile
		if(keyCode==KeyEvent.VK_0){
			editor.tileIdCurrent = 0;
		}
		if(keyCode==KeyEvent.VK_1){
			editor.tileIdCurrent = 1;
		}
		if(keyCode==KeyEvent.VK_2){
			editor.tileIdCurrent = 2;
		}
		if(keyCode==KeyEvent.VK_3){
			editor.tileIdCurrent = 3;
		}
		if(keyCode==KeyEvent.VK_4){
			editor.tileIdCurrent = 4;
		}
	}

	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			
			editor.mouseP = e.getPoint();
			//System.out.println(editor.mouseP);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
