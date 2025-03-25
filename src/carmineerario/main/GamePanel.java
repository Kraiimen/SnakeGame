package carmineerario.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

//DA MODIFICARE: SCRITTA SPACE TO START DEVE COMPARIRE ANCHE DOPO IL PLAY AGAIN


public class GamePanel extends JPanel implements ActionListener{
	
	private final int PANEL_WIDTH = 500;
	private final int PANEL_HEIGHT = 500;
	private final int UNIT_SIZE = 20;
	private final int MAX_UNITS = (PANEL_WIDTH*PANEL_HEIGHT)/UNIT_SIZE;
	private final int snakeX[] = new int[MAX_UNITS];
	private final int snakeY[] = new int[MAX_UNITS];
	private int appleX, appleY, applesEaten;
	private int bodyParts = 4;
	private int delay;

	char direction = 'R';
	boolean running = false;
	Random random;
	Timer timer;
	
	int snakeColor[] = new int[3];
	
	JLabel scoreLabel = new JLabel("Score: "+applesEaten);
	JLabel startLabel = new JLabel("Press space to start");
	
	//Constructor
	GamePanel() {
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		scoreLabel.setFont(new Font(null, Font.BOLD, 25));
		scoreLabel.setHorizontalAlignment(JLabel.LEADING);
		this.add(scoreLabel);
		
		startLabel.setFont(new Font(null, Font.BOLD, 35));
		startLabel.setPreferredSize(new Dimension(500,400));
		startLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(startLabel);
		
		random = new Random();
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.addKeyListener(new MyKeyAdapter());
		
		startGame();
	}
	
	//Method to start the game
	public void startGame() {
		newApple();
		running = true;
		
		timer = new Timer(getDelay(), this);
		startLabel.setVisible(true);
	}
	
	//Method to get delay value
	public int getDelay() {
		return delay;
	}
	
	//Method to set a new value to delay
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
	
	//JComponent method override
	@Override
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	//Method to draw background pattern, apple and snake
	
	private void draw(Graphics g) {
		//If the game is running
		if(running) {
			g.setColor(Color.GRAY);
			
			//BG COLOR
			for(int i=0; i<PANEL_HEIGHT/UNIT_SIZE; i++) {
				for(int j=0; j<PANEL_WIDTH/UNIT_SIZE; j++){
					if((i + j) % 2 == 0) g.setColor(new Color(94, 232, 141));
					else g.setColor(new Color(94, 252, 141));
					
					g.fillRect(j * UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//BG GRID
			for(int i=0; i<PANEL_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, PANEL_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, PANEL_WIDTH, i*UNIT_SIZE);
			}
			
			//APPLE COLOR
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			//SNAKE COLOR
			for(int i=0; i<bodyParts; i++) {
					g.setColor(new Color(snakeColor[0], snakeColor[1], snakeColor[2])); //body
					g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
			}
		}
		
		//If the game is not running
		else {
			running = false;
			MainFrame.showPanel("PlayAgain Panel");
		}
	}
	
	//Method to spawn a new apple
	
	private void newApple() {
		appleX = random.nextInt((int) (PANEL_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (PANEL_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
	}
	
	//Method to check if the apple has been eaten

	private void checkApple() {
		if((snakeX[0] == appleX) && (snakeY[0] == appleY)) {
			bodyParts++;
			updateScore();
			newApple();
		}
	}
	
	//Method to update score
	
	private void updateScore() {
		applesEaten++;
		scoreLabel.setText("Score: "+applesEaten);
	}
	
	//Method to move the snake
	
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
	
	//Method to check if the snake collides with the walls or itself
	
	private void checkCollisions() {
		for(int i=bodyParts; i>0; i--) {
			//head collides with body
			if((snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])){
				running = false;
			}
			//head touches left border
			if(snakeX[0] < 0){
				running = false;
			}
			//head touches right border
			if(snakeX[0] > PANEL_WIDTH - UNIT_SIZE) {
				running = false;
			}
			//head touches top border
			if(snakeY[0] < 0) {
				running = false;
			}
			//head touches bottom border
			if(snakeY[0] > PANEL_HEIGHT - UNIT_SIZE) {
				running = false;
			}
			if(!running) {
				timer.stop();
			}
		}
	}
	

	//If the game is running this method allows the player to move and checks apples and collisions
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	//Method to reset the game and start again
	public void reset() {		
		applesEaten = 0;
		bodyParts = 4;
	    direction = 'R';
	    running = true;
	    
	    startLabel.setVisible(true);
	    
	    for (int i = 0; i < bodyParts; i++) {
	        snakeX[i] = 0 - (i * UNIT_SIZE);
	        snakeY[i] = 0;
	    }
	    
	    newApple();
	    repaint();
	}
	
	//Class to check key pressed allowing the snake to change direction when the player gives an input
	
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
				running = true;
				timer.start();
				startLabel.setVisible(false);
				break;
			}
		}
	}
		
}
