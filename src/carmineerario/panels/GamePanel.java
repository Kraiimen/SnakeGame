package carmineerario.panels;

import carmineerario.config.GameConfig;
import carmineerario.main.MainFrame;
import com.google.gson.annotations.SerializedName;
import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener{
	private final int PANEL_WIDTH = 500;
	private final int PANEL_HEIGHT = 500;
	private final int UNIT_SIZE = 20;
	private final int MAX_UNITS = (PANEL_WIDTH*PANEL_HEIGHT)/UNIT_SIZE;

	private final int[] snakeX = new int[MAX_UNITS];
	private final int[] snakeY = new int[MAX_UNITS];
	private int appleX, appleY, applesEaten;
	private int bodyParts = 4;
	private int delay;

	char direction = 'R';
	boolean running = false;
	Random random;
	Timer timer;

	public int[] snakeColor;

	JLabel scoreLabel = new JLabel("Score: "+applesEaten);
	JLabel startLabel = new JLabel("Press space to start");

	public GamePanel() {
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		scoreLabel.setFont(new Font(null, Font.BOLD, 25));
		scoreLabel.setHorizontalAlignment(JLabel.LEADING);
		this.add(scoreLabel);
		
		startLabel.setFont(new Font(null, Font.BOLD, 35));
		startLabel.setPreferredSize(new Dimension(500,400));
		startLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(startLabel);
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.addKeyListener(new MyKeyAdapter());

		snakeColor = new int[3];
		random = new Random();
		startGame();
	}
	
	// Starts the game by spawning an apple, initializing the timer, and displaying the start label
	public void startGame() {
		newApple();
		running = true;

		timer = new Timer(getDelay(), this);
		startLabel.setVisible(true);
	}

	// Draws the game elements: background, grid, apple, and snake
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	private void draw(Graphics g) {
		if(running) {
			g.setColor(Color.GRAY);
			
			// Background
			for(int i=0; i<PANEL_HEIGHT/UNIT_SIZE; i++) {
				for(int j=0; j<PANEL_WIDTH/UNIT_SIZE; j++){
					if((i + j) % 2 == 0) g.setColor(new Color(94, 232, 141));
					else g.setColor(new Color(94, 252, 141));
					
					g.fillRect(j * UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			// Grid
			for(int i=0; i<PANEL_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, PANEL_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, PANEL_WIDTH, i*UNIT_SIZE);
			}
			
			// Apple
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			// Snake
			for(int i=0; i<bodyParts; i++) {
					g.setColor(new Color(snakeColor[0], snakeColor[1], snakeColor[2])); //body
					g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
			}
		}
	}
	
	// Generates a new apple at a random position on the grid
	private void newApple() {
		appleX = random.nextInt((int) (PANEL_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (PANEL_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
	}
	
	// Checks if the snake ate the apple, and if so, grows the snake,
	// updates the score, and generates a new apple
	private void checkApple() {
		if((snakeX[0] == appleX) && (snakeY[0] == appleY)) {
			bodyParts++;
			updateScore();
			newApple();
		}
	}
	
	// Increments the score and updates the score label
	private void updateScore() {
		applesEaten++;
		scoreLabel.setText("Score: "+applesEaten);
	}
	
	// Moves the snake
	private void move() {
		for(int i=bodyParts; i>0; i--) {
			snakeX[i] = snakeX[i-1];
			snakeY[i] = snakeY[i-1];
		}
		
		switch (direction) {
		case 'U':
			snakeY[0] = snakeY[0] - UNIT_SIZE;
			break;
		case 'D':
			snakeY[0] = snakeY[0] + UNIT_SIZE;
			break;
		case 'L':
			snakeX[0] = snakeX[0] - UNIT_SIZE;
			break;
		case 'R':
			snakeX[0] = snakeX[0] + UNIT_SIZE;
			break;
		}
	}
	
	// Checks for collisions with the snake's body or the borders,
	// and stops the game if a collision is detected
	private void checkCollisions() {
		for(int i=bodyParts; i>0; i--) {
			// Head collides with body
			if((snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])){
				running = false;
			}
			// Head touches any border
			if(snakeX[0] < 0 || snakeY[0] < 0 ||
			   snakeX[0] > PANEL_WIDTH - UNIT_SIZE ||
			   snakeY[0] > PANEL_HEIGHT - UNIT_SIZE){
				running = false;
			}

			if(!running) {
				timer.stop();
				System.out.println("Score: "+applesEaten);
				MainFrame.showPanel("PlayAgain Panel");

				MainFrame.playAgainPanel.canRecordBeSaved(applesEaten);

				break;
			}
		}
	}

	// Updates the game state and triggers screen update
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	// Resets the game state: score, snake position, and restarts the game
	public void reset() {
		applesEaten = 0;
		scoreLabel.setText("Score: "+applesEaten);
		bodyParts = 4;
	    direction = 'R';

		// Reset snake start position (only the head is visible)
		snakeX[0] = 0;
		snakeY[0] = 0;

		// Reposition old snake body segments
	    for (int i = 1; i < bodyParts; i++) {
	        snakeX[i] = 0;
	        snakeY[i] = 0;
	    }

		startGame();
		startLabel.setVisible(true);
		repaint();
	}
	
	// KeyAdapter to handle direction changes and start the game when space is pressed
	private class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP: 
				if(direction != 'D') direction = 'U';
				break;
			case KeyEvent.VK_DOWN: 
				if(direction != 'U') direction = 'D';
				break;
			case KeyEvent.VK_LEFT: 
				if(direction != 'R') direction = 'L';
				break;
			case KeyEvent.VK_RIGHT: 
				if(direction != 'L') direction = 'R';
				break;
			case KeyEvent.VK_SPACE:
				timer.start();
				startLabel.setVisible(false);
				break;
			}
		}
	}

	// Getter and Setter for delay
	public int getDelay() {
		return delay;
	}
	public void setDelay(int newDelay) {
		this.delay = newDelay;

		if (timer != null) {
			timer.setDelay(newDelay);

			if (timer.isRunning()) {
				timer.stop();
				timer.start();
			}
		}
	}

	// Getter for applesEaten
	public int getApplesEaten() {
		return applesEaten;
	}
}
