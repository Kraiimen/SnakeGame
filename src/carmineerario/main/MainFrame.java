package carmineerario.main;

import carmineerario.config.GameConfig;
import carmineerario.panels.*;

import java.awt.*;

import javax.swing.*;

public class MainFrame extends JFrame{
	// Background color for every panel
	public static final Color BACKGROUND_COLOR = new Color(230, 230, 230);
	private final static CardLayout CARD_LAYOUT = new CardLayout();
	private final static JPanel CARD_PANEL = new JPanel(CARD_LAYOUT);

	private static final JPanel GAME_PANEL = new GamePanel();
	private static final JPanel PLAY_AGAIN_PANEL = new PlayAgainPanel();
	private static final JPanel RECORDS_PANEL = new RecordsPanel();
	private static final JPanel SETTINGS_PANEL = new SettingsPanel();
	private static final JPanel HOME_PANEL = new HomePanel();

	public static SettingsPanel settingsPanel = (SettingsPanel) SETTINGS_PANEL;
	public static GamePanel gamePanel = (GamePanel) GAME_PANEL;
	public static PlayAgainPanel playAgainPanel = (PlayAgainPanel) PLAY_AGAIN_PANEL;
	public static RecordsPanel recordsPanel = (RecordsPanel) RECORDS_PANEL;

	MainFrame(){		
		/* CARD LAYOUT */
		CARD_PANEL.add(HOME_PANEL, "Homepage Panel");
		CARD_PANEL.add(GAME_PANEL, "Game Panel");
		CARD_PANEL.add(SETTINGS_PANEL, "Settings Panel");
		CARD_PANEL.add(PLAY_AGAIN_PANEL, "PlayAgain Panel");
		CARD_PANEL.add(RECORDS_PANEL, "Records Panel");
		/* ----------- */
		
		/* FRAME */
		// Icon: https://icons8.com/icon/yGwrZYYkmgaX/year-of-snake
		this.setIconImage(new ImageIcon("assets/icons/snakeIcon.png").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Snake Game");
		
		this.add(CARD_PANEL);
		
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		/* ----- */
	}

	public static void main(String[] args) {
		// Load game configuration
		gamePanel.setDelay(GameConfig.loadDifficulty());
		gamePanel.snakeColor = GameConfig.loadSnakeColor();
		recordsPanel.reloadRecords();
		settingsPanel.slider.setValue(settingsPanel.delayToSlider(GameConfig.loadDifficulty()));

		new MainFrame();
		MainFrame.showPanel("Homepage Panel");
	}

	// Displays the specified panel
	public static void showPanel(String panelName) {
		CARD_LAYOUT.show(CARD_PANEL, panelName);

		// If the "Game Panel" is selected, it requests focus to ensure it captures user input
		if(panelName.equals("Game Panel"))
			GAME_PANEL.requestFocusInWindow();
	}
}
