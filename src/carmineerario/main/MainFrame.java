package carmineerario.main;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
	static CardLayout cardLayout;
	static JPanel cardPanel;
	static JPanel gamePanel = new GamePanel();
	static JPanel settingsPanel = new SettingsPanel();
	static SettingsPanel sp = (SettingsPanel) settingsPanel;
	
	MainFrame(){		
		/* CARD LAYOUT */
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		
		JPanel homePanel = new HomePanel();
		JPanel playAgainPanel = new PlayAgainPanel();
		JPanel recordsPanel = new RecordsPanel();
		
		cardPanel.add(homePanel, "Homepage Panel");
		cardPanel.add(gamePanel, "Game Panel");
		cardPanel.add(settingsPanel, "Settings Panel");
		cardPanel.add(playAgainPanel, "PlayAgain Panel");
		cardPanel.add(recordsPanel, "Records Panel");
		/* ----------- */
		
		/* FRAME */		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Snake Game");
		
		this.add(cardPanel);
		
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		/* ----- */
	}
	
	public static void showPanel(String panelName) {
		cardLayout.show(cardPanel, panelName);
		
		if(panelName.equals("Game Panel"))
			gamePanel.requestFocusInWindow();
	}
	
	public static void main(String[] args) {
		new MainFrame();
		MainFrame.showPanel("Homepage Panel");
		
		sp.gp.setDelay(GameConfig.loadDifficulty());
		sp.gp.snakeColor = GameConfig.loadSnakeColor();
		sp.slider.setValue(sp.delayToSlider(GameConfig.loadDifficulty()));
	}

}
