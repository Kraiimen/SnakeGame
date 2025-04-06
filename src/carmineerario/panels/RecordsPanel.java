package carmineerario.panels;

import carmineerario.config.GameConfig;
import carmineerario.main.MainFrame;

import java.awt.*;
import java.net.URL;
import java.util.List;

import javax.swing.*;

public class RecordsPanel extends JPanel {
	public static List<GameConfig.Player> player;
	private final int BUTTONS_WIDTH = 160;
	private final int BUTTONS_HEIGHT = 50;
	private final JLabel player1Name = new JLabel();
    private final JLabel player1Record = new JLabel();
	private final JLabel player2Name = new JLabel();
    private final JLabel player2Record = new JLabel();
	private final JLabel player3Name = new JLabel();
    private final JLabel player3Record = new JLabel();
	private final JLabel player4Name = new JLabel();
    private final JLabel player4Record = new JLabel();
	private final JLabel player5Name = new JLabel();
    private final JLabel player5Record = new JLabel();

	public RecordsPanel() {
		reloadRecords();

		/* LABELS */
		JLabel recordsLabel = createRecordsLabel(); // Header
		/* ------ */

		/* BUTTONS */
		JButton backBtn = createBackButton();
		/* ------- */

		/* PANEL */
		this.setSize(500, 500);
		this.setBackground(MainFrame.BACKGROUND_COLOR);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout verticale per il pannello principale

		this.add(Box.createVerticalGlue());
		this.add(recordsLabel);
		this.add(Box.createVerticalStrut(25));
		this.add(createRecordPanel(0, player1Name, player1Record));
		this.add(createRecordPanel(1, player2Name, player2Record));
		this.add(createRecordPanel(2, player3Name, player3Record));
		this.add(createRecordPanel(3, player4Name, player4Record));
		this.add(createRecordPanel(4, player5Name, player5Record));
		this.add(Box.createVerticalStrut(25));
		this.add(backBtn);
		this.add(Box.createVerticalGlue());

		this.setVisible(true);
		/* ----- */
	}

	private JLabel createRecordsLabel(){
		JLabel recordsLabel = new JLabel("TOP 5 RECORDS");
		recordsLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
		recordsLabel.setAlignmentX(CENTER_ALIGNMENT);
		return recordsLabel;
	}
	private JButton createBackButton(){
		JButton backBtn = new JButton("\u2B05 Back to home");
		backBtn.setFocusable(false);
		backBtn.setAlignmentX(CENTER_ALIGNMENT);
		backBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
		backBtn.addActionListener(e -> MainFrame.showPanel("Homepage Panel"));
		return backBtn;
	}
	private JPanel createRecordPanel(int pos, JLabel nameLabel, JLabel recordLabel){
		JPanel panel = new JPanel();
		panel.setBackground(MainFrame.BACKGROUND_COLOR);

		nameLabel.setText(String.format("%4s: ", player.get(pos).playerName()));
		nameLabel.setFont(new Font(null, Font.BOLD, 22));

		recordLabel.setText(String.format("%10d", player.get(pos).playerRecord()));
		recordLabel.setFont(new Font(null, Font.BOLD, 22));
		recordLabel.setForeground(Color.black);

		if(pos == 0){
			nameLabel.setFont(new Font(null, Font.BOLD, 32));
			// Icon: https://icons8.com/icon/33852/medal-first-place
			URL imageURL = getClass().getClassLoader().getResource("assets/icons/firstPlace.png");
			if(imageURL != null){
				nameLabel.setIcon(new ImageIcon(imageURL));
			}
			nameLabel.setIconTextGap(30);
			nameLabel.setForeground(new Color(218, 165, 32));
			recordLabel.setForeground(new Color(218, 165, 32));
			recordLabel.setFont(new Font(null, Font.BOLD, 32));
		}
		else if(pos == 1){
			nameLabel.setFont(new Font(null, Font.BOLD, 28));
			// Icon: https://icons8.com/icon/23873/medal-second-place
			URL imageURL = getClass().getClassLoader().getResource("assets/icons/secondPlace.png");
			if(imageURL != null){
				nameLabel.setIcon(new ImageIcon(imageURL));
			}
			nameLabel.setIconTextGap(15);
			nameLabel.setForeground(new Color(192, 192, 192));
			recordLabel.setForeground(new Color(169, 169, 169));
			recordLabel.setFont(new Font(null, Font.BOLD, 28));
		}
		else if(pos == 2){
			nameLabel.setFont(new Font(null, Font.BOLD, 24));
			// Icon: https://icons8.com/icon/33853/medal-third-place
			URL imageURL = getClass().getClassLoader().getResource("assets/icons/thirdPlace.png");
			if(imageURL != null){
				nameLabel.setIcon(new ImageIcon(imageURL));
			}
			nameLabel.setIconTextGap(15);
			nameLabel.setForeground(new Color(205, 127, 50));
			recordLabel.setForeground(new Color(139, 69, 19));
			recordLabel.setFont(new Font(null, Font.BOLD, 24));
		}
		// Other positions
		else {
			nameLabel.setForeground(Color.darkGray);
			recordLabel.setForeground(Color.darkGray);
		}

		panel.add(nameLabel);
		panel.add(recordLabel);
		return panel;
	}
	public void reloadRecords() {
		player = GameConfig.loadRecords();
		updateRecordLabels();
	}
	private void updateRecordLabels() {
		player1Name.setText(String.format("%5s: ", player.getFirst().playerName()));
		player1Record.setText(String.format("%8d", player.getFirst().playerRecord()));
		player2Name.setText(String.format("%5s: ", player.get(1).playerName()));
		player2Record.setText(String.format("%8d", player.get(1).playerRecord()));
		player3Name.setText(String.format("%5s: ", player.get(2).playerName()));
		player3Record.setText(String.format("%8d", player.get(2).playerRecord()));
		player4Name.setText(String.format("%5s: ", player.get(3).playerName()));
		player4Record.setText(String.format("%8d", player.get(3).playerRecord()));
		player5Name.setText(String.format("%5s: ", player.getLast().playerName()));
		player5Record.setText(String.format("%8d", player.getLast().playerRecord()));
	}
}
