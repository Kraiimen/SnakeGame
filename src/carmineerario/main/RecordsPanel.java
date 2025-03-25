package carmineerario.main;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RecordsPanel extends JPanel{
	static List<GameConfig.RecordsArray> records;
	
	RecordsPanel() {
		records = GameConfig.loadRecords();
		System.out.println(records);
		
		/* LABELS */
		JLabel recordsLabel = new JLabel("TOP 5 RECORDS");
		recordsLabel.setFont(new Font(null, Font.BOLD, 40));
		recordsLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel player1 = new JLabel(records.getFirst().getPlayerName()+": "+records.getFirst().getPlayerRecord());
		player1.setFont(new Font(null, Font.BOLD, 25));
		player1.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel player2 = new JLabel(records.get(1).getPlayerName()+": "+records.get(1).getPlayerRecord());
		player2.setFont(new Font(null, Font.BOLD, 25));
		player2.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel player3 = new JLabel(records.get(2).getPlayerName()+": "+records.get(2).getPlayerRecord());
		player3.setFont(new Font(null, Font.BOLD, 25));
		player3.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel player4 = new JLabel(records.get(3).getPlayerName()+": "+records.get(3).getPlayerRecord());
		player4.setFont(new Font(null, Font.BOLD, 25));
		player4.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel player5 = new JLabel(records.getLast().getPlayerName()+": "+records.getLast().getPlayerRecord());
		player5.setFont(new Font(null, Font.BOLD, 25));
		player5.setAlignmentX(CENTER_ALIGNMENT);
		/* ------ */
		
		/* BUTTONS */
		final int BUTTONS_WIDTH = 160;
		final int BUTTONS_HEIGHT = 50;

		// BACK TO HOME BUTTON
		JButton backBtn = new JButton("\u2B05 Back to home");
		backBtn.setFocusable(false);
		backBtn.setAlignmentX(CENTER_ALIGNMENT);
		backBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));	
		backBtn.addActionListener(e -> MainFrame.showPanel("Homepage Panel"));
		/* ------- */
		
		/* PANEL */
	    this.setSize(500,500);
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout verticale per il pannello principale
	    
	    this.add(Box.createVerticalGlue());
	    this.add(recordsLabel);
	    this.add(Box.createVerticalStrut(20));
	    this.add(player1);
	    this.add(Box.createVerticalStrut(10));
	    this.add(player2);
	    this.add(Box.createVerticalStrut(10));
	    this.add(player3);
	    this.add(Box.createVerticalStrut(10));
	    this.add(player4);
	    this.add(Box.createVerticalStrut(10));
	    this.add(player5);
	    this.add(Box.createVerticalStrut(30));
	    this.add(backBtn);
	    this.add(Box.createVerticalGlue());
	    
	    this.setVisible(true);
	    /* ----- */
	}
}
