package carmineerario.panels;

import carmineerario.config.GameConfig;
import carmineerario.main.MainFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class PlayAgainPanel extends JPanel{
	private final int BUTTONS_WIDTH = 160;
	private final int BUTTONS_HEIGHT = 50;
	JLabel recordCantBeSaved = new JLabel("Too bad, the record can't be saved this time.");
	JLabel recordSaved = new JLabel("Record saved successfully.");
	JLabel playerNameLabel = new JLabel("Enter a 3-letter name");
	JTextField playerName = new JTextField("ABC"); // Player name input field
	JButton savePlayerNameBtn = new JButton("Save record");

	public PlayAgainPanel() {
		/* LABELS */
		JLabel gameOverLabel = createGameOverLabel();
		setupRecordCantBeSavedLabel();
		setupRecordSavedLabel();
		setupPlayerNameLabel();
		/* ----- */

		/* TEXT FIELD */
		setupPlayerNameTextField();
		/* ----------- */
		
		/* BUTTONS */
		setupSavePlayerNameButton();
		JButton playAgainBtn = createPlayAgainButton();
		JButton backBtn = createBackButton();
		/* ------ */
		
		/* PANEL */
		this.setSize(500,500);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(MainFrame.BACKGROUND_COLOR);

		this.add(Box.createVerticalGlue());
		this.add(gameOverLabel); // Game over text
		this.add(Box.createVerticalStrut(15));	
		this.add(recordCantBeSaved); // Record can't be saved warning
		this.add(recordSaved); // Record can be saved message
		this.add(Box.createVerticalStrut(30));		
		this.add(playerNameLabel); // Player name input field label
		this.add(Box.createVerticalStrut(5));
		this.add(playerName); // Player name input field 
		this.add(Box.createVerticalStrut(5));
		this.add(savePlayerNameBtn); // Save record button
		this.add(Box.createVerticalStrut(40));
		this.add(playAgainBtn); // Play again button
		this.add(Box.createVerticalStrut(20));
		this.add(backBtn); // Back to home button
		this.add(Box.createVerticalGlue());
		
		this.setVisible(true);
		/* ----- */
	}

	private JLabel createGameOverLabel(){
		JLabel gameOver = new JLabel("Game Over");
		gameOver.setAlignmentX(CENTER_ALIGNMENT);
		gameOver.setFont(new Font(null, Font.BOLD, 50));
		return gameOver;
	}
	private void setupRecordCantBeSavedLabel(){
		recordCantBeSaved.setAlignmentX(CENTER_ALIGNMENT);
		recordCantBeSaved.setFont(new Font(null, Font.ITALIC, 18));
	}
	private void setupRecordSavedLabel(){
		recordSaved.setAlignmentX(CENTER_ALIGNMENT);
		recordSaved.setFont(new Font(null, Font.ITALIC, 18));
		recordSaved.setVisible(false);
	}
	private void setupPlayerNameLabel(){
		playerNameLabel.setAlignmentX(CENTER_ALIGNMENT);
		playerNameLabel.setFont(new Font(null, Font.PLAIN, 15));
		playerNameLabel.setVisible(false);
	}
	private void setupPlayerNameTextField(){
		// Limit player name to 3 characters
		((AbstractDocument) playerName.getDocument()).setDocumentFilter(new CharacterLimitFilter(3));
		playerName.setAlignmentX(CENTER_ALIGNMENT);
		playerName.setPreferredSize(new Dimension(50,30));
		playerName.setMaximumSize(new Dimension(50,30));
		playerName.setFont(new Font("Consolas", Font.BOLD, 15));
		playerName.setHorizontalAlignment(JTextField.CENTER);
		playerName.setMargin(new Insets(5, 10, 5, 10));
		playerName.setForeground(Color.GRAY);
		playerName.setVisible(false);

		// Simulate placeholder behavior
		playerName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Remove the placeholder when the player select the input field
				if (playerName.getText().equals("ABC")) {
					playerName.setText("");
					playerName.setForeground(Color.DARK_GRAY);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// If the field is empty, reset the placeholder
				if (playerName.getText().isEmpty()) {
					playerName.setText("ABC");
					playerName.setForeground(Color.GRAY);
				}
			}
		});
	}
	private void setupSavePlayerNameButton(){
		savePlayerNameBtn.setFocusable(false);
		savePlayerNameBtn.setAlignmentX(CENTER_ALIGNMENT);
		savePlayerNameBtn.setPreferredSize(new Dimension(130, 40));
		savePlayerNameBtn.setVisible(false);
		savePlayerNameBtn.addActionListener(e -> {
			confirmSavedRecord();
			if(playerName.getText().isEmpty()) { playerName.setText("ABC");}
			GameConfig.saveNewRecord(new GameConfig.Player(playerName.getText(), MainFrame.gamePanel.getApplesEaten()));
			playerName.setText("");
		});
	}
	private JButton createPlayAgainButton(){
		JButton playAgainBtn = new JButton("Play Again");
		playAgainBtn.setFocusable(false);
		playAgainBtn.setAlignmentX(CENTER_ALIGNMENT);
		playAgainBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
		playAgainBtn.addActionListener(e -> {
			MainFrame.showPanel("Game Panel");
			MainFrame.gamePanel.reset();
			recordSaved.setVisible(false);
			recordCantBeSaved.setVisible(true);
		});
		return playAgainBtn;
	}
	private JButton createBackButton(){
		JButton backBtn = new JButton("\u2B05 Back to home");
		backBtn.setFocusable(false);
		backBtn.setAlignmentX(CENTER_ALIGNMENT);
		backBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
		backBtn.addActionListener(e -> {
			MainFrame.showPanel("Homepage Panel");
			MainFrame.gamePanel.reset();
			recordSaved.setVisible(false);
			recordCantBeSaved.setVisible(true);
		});
		return backBtn;
	}

	// Checks if the record can be saved
	public void canRecordBeSaved(int score){
		// If the record can be saved, the warning is not visible,
		// and the player can enter their name
		if(GameConfig.canRecordBeSaved(score)) {
			recordCantBeSaved.setVisible(false);
			playerNameLabel.setVisible(true);
			playerName.setVisible(true);
			savePlayerNameBtn.setVisible(true);
		}
	}
	// Confirms that the record has been saved
	public void confirmSavedRecord(){
		playerNameLabel.setVisible(false);
		playerName.setVisible(false);
		savePlayerNameBtn.setVisible(false);
		recordSaved.setVisible(true);
	}

	static class CharacterLimitFilter extends DocumentFilter {
	    private final int MAX_CHARS;

	    public CharacterLimitFilter(int maxChars) {
	        this.MAX_CHARS = maxChars;
	    }

	    @Override
	    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
	    	// Check if, adding the new text, it stays in the limit
	        if (fb.getDocument().getLength() + string.length() <= MAX_CHARS) {
	            super.insertString(fb, offset, string.toUpperCase(), attr);
	        }
	    }

	    @Override
	    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
	    	// Check if, replacing the current text, it stays in the limit
	        if (fb.getDocument().getLength() - length + text.length() <= MAX_CHARS) {
	            super.replace(fb, offset, length, text.toUpperCase(), attrs);
	        }
	    }
	}
}
