package level.editor;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Editor extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	InputHandler IH = new InputHandler(this);
	Point mouseP = new Point(-1,-1);
	//MouseScrolling MS = new MouseScrolling();

	int xOffset = 0;
	int yOffset = 0;
	int tileIdCurrent = 1;
	int tilesWidth;
	int tilesHeight;
	static String fileTitle;

	public static int fullScreenWidth;

	public static int fullScreenHeight;

	public static final int WIDTH = 600;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final String NAME = "Level Editor";
	public boolean running = false;
	public int tickCount = 0;

	public static int tileWidth = 500;
	public static int tileHeight = 500;

	static Tile tileArray[][] = new Tile[tileWidth][tileHeight];
	int loadedLevel[][];

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	BufferedImage spikes;
	BufferedImage coin;
	BufferedImage sign;
	
	JFrame frame;
	JPanel panel;
	JLabel label1, label2;
	JTextField input1, input2, loadField;
	JButton button, loadBtn;

	// Controls
	public static boolean left, right, up, down;
	
	public Editor() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		this.addKeyListener(IH);

		init();
		
		panel = new JPanel();
		panel.setVisible(true);
		panel.setMinimumSize(new Dimension(WIDTH, 35));
		panel.setMaximumSize(new Dimension(WIDTH, 35));
		panel.setPreferredSize(new Dimension(WIDTH, 35));
		
		label1 = new JLabel("Width: ");
		input1 = new JTextField(10);
		label2 = new JLabel("Height: ");
		input2 = new JTextField(10);
		button = new JButton("Save");
		button.addActionListener(new ActionListener() {          
		    public void actionPerformed(ActionEvent e) {
		    	if(input1.getText().equals("") || input2.getText().equals("")){
		    		JOptionPane.showMessageDialog(null, "Please set the width and height of the level (in tiles).");
		    	}
		    	else{
		    		fileTitle = loadField.getText();
		    		tileWidth = Integer.parseInt(input1.getText());
					tileHeight = Integer.parseInt(input2.getText());
			        file();
			        JOptionPane.showMessageDialog(null, "Level saved!");
		    	}
		    }
		}); 
		loadField = new JTextField(10);
		loadBtn = new JButton("Load");
		loadBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileTitle = loadField.getText();
				load();
			}
		});
		
		panel.add(loadField);
		panel.add(loadBtn);
		panel.add(label1);
		panel.add(input1);
		panel.add(label2);
		panel.add(input2);
		panel.add(button);
		
		frame.add(panel, BorderLayout.SOUTH);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		requestFocus();
		
		try {
			coin = ImageIO.read(new File("img/coin.png"));
			spikes = ImageIO.read(new File("img/spikes.png"));
			sign = ImageIO.read(new File("img/Object/sign.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void file(){
		
		File file = new File("levels/" + fileTitle);
		//System.out.println(file.getAbsolutePath().replace(File.separator, "\\\\"));
		PrintWriter writer;
		try {
			writer = new PrintWriter(file.getAbsolutePath().replace("\\", "\\\\") + ".txt", "UTF-8");
			writer.println(tileWidth);
			writer.println(tileHeight);
			for (int y = 0; y < tileHeight; y++) {
				for (int x = 0; x < tileWidth; x++) {
					writer.print(tileArray[x][y].tileId + " ");
				}
				writer.println("");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void load(){
		try{
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("levels/" + fileTitle + ".txt"));
			
			tilesWidth = Integer.parseInt(br.readLine());
			tilesHeight = Integer.parseInt(br.readLine());
			
			loadedLevel = new int[tilesWidth][tilesHeight];
			
			String delimiters = "\\s+";
			for(int row = 0;row<tilesHeight;row++){
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for(int col = 0;col<tilesWidth;col++){
					loadedLevel[col][row] = Integer.parseInt(tokens[col]);
					//System.out.println(loadedLevel[row][col]);
				}
			}
			
		}
		catch(Exception e){
			
		}
		
		for(int i = 0;i<tilesHeight;i++){
			for(int j = 0;j<tilesWidth;j++){
				tileArray[j][i].tileId = loadedLevel[j][i];
			}
		}
	}

	public synchronized void start() {
		running = true;
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

	private void init() {
		for (int y = 0; y < tileHeight; y++) {
			for (int x = 0; x < tileWidth; x++) {
				tileArray[x][y] = new Tile(x * 32, y * 32, 0);
			}
		}
		
	}

	public void tick() {
		tickCount++;
		for (int x = 0; x < tileWidth; x++) {
			for (int y = 0; y < tileHeight; y++) {
				tileArray[x][y].tick(this);
				
				if(tileArray[x][y].bounding.contains(mouseP)){
					
					tileArray[x-xOffset/(32)][y-yOffset/(32)].tileId = tileIdCurrent;
					System.out.println((x-xOffset/(32)+1-1) + " " + (y-yOffset/(32)+1-1));
					mouseP = new Point(-1,-1);
				}
			}
		}
		
		moveMap();
	}

	public void moveMap() {

		if (left) {
			xOffset += 2;
		}
		if (right) {
			xOffset -= 2;
		}
		if (up) {
			yOffset += 2;
		}
		if (down) {
			yOffset -= 2;
		}
		fixBounds();
	}
	
	public void fixBounds(){
		
		if(xOffset<WIDTH-tileWidth*32){
			xOffset = WIDTH-tileWidth*32;
		}
		if(yOffset<HEIGHT-tileWidth*32){
			yOffset = HEIGHT-tileWidth*32;
		}
		
		if(xOffset>0){
			xOffset = 0;
		}
		if(yOffset>0){
			yOffset = 0;
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		for (int x = 0; x < tileWidth; x++) {
			for (int y = 0; y < tileHeight; y++) {
				if (tileArray[x][y].x >= 0 - 32 && tileArray[x][y].x <= getWidth() + 32 & tileArray[x][y].y >= 0 - 32
						&& tileArray[x][y].y <= getHeight() + 32) {
					tileArray[x][y].render(g);
				}
			}
		}
		
		g.dispose();
		bs.show();

		fullScreenWidth = getWidth();
		fullScreenHeight = getHeight();
	}
	
	public static void main(String[] args) {
		
		new Editor().start();
		//file();
	}
	
}
