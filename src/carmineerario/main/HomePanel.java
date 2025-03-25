package carmineerario.main;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class HomePanel extends JPanel {
    HomePanel(){
        /* GAME TITLE */
        JLabel gameNameTitle = new JLabel("Snake Game");
        gameNameTitle.setAlignmentX(CENTER_ALIGNMENT);
        gameNameTitle.setFont(new Font(null, Font.BOLD, 50));
        /* --------------- */
        
        /* BUTTONS */
        final int BUTTONS_WIDTH = 160;
        final int BUTTONS_HEIGHT = 50;

        // PLAY BUTTON
        JButton playBtn = new JButton("Play");
        playBtn.setFocusable(false);
        playBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        playBtn.setMaximumSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        playBtn.setAlignmentX(CENTER_ALIGNMENT);
        playBtn.addActionListener(e -> MainFrame.showPanel("Game Panel"));
        
        JButton recordsBtn = new JButton("Records");
        recordsBtn.setFocusable(false);
        recordsBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        recordsBtn.setMaximumSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        recordsBtn.setAlignmentX(CENTER_ALIGNMENT);
        recordsBtn.addActionListener(e -> {
        	MainFrame.showPanel("Records Panel");
        	GameConfig.loadRecords();
        });
        
        // SETTINGS BUTTON
        JButton settingsBtn = new JButton("Settings");
        settingsBtn.setFocusable(false);
        settingsBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        settingsBtn.setMaximumSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        settingsBtn.setAlignmentX(CENTER_ALIGNMENT);
        settingsBtn.addActionListener(e -> MainFrame.showPanel("Settings Panel"));
        
        // EXIT BUTTON
        JButton exitBtn = new JButton("Exit");
        exitBtn.setFocusable(false);
        exitBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        exitBtn.setMaximumSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        exitBtn.setAlignmentX(CENTER_ALIGNMENT);
        exitBtn.addActionListener(e -> System.exit(0));
        /* ------- */
        
        /* BUTTONS PANEL */
        JPanel btnPanel = new JPanel();
        
        // Set BoxLayout (vertical)
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        
        // Add buttons with vertical spacing
        btnPanel.add(playBtn);
        btnPanel.add(Box.createVerticalStrut(20));
        btnPanel.add(recordsBtn);
        btnPanel.add(Box.createVerticalStrut(20));
        btnPanel.add(settingsBtn);
        btnPanel.add(Box.createVerticalStrut(20));
        btnPanel.add(exitBtn);
        /* ------------- */
        
        /* PANEL */
        this.setSize(500,500);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout verticale per il pannello principale
        
        this.setVisible(true);
        
        this.add(Box.createVerticalGlue());
        this.add(gameNameTitle);
        this.add(Box.createVerticalStrut(70));
        this.add(btnPanel);
        this.add(Box.createVerticalStrut(30));
        /* ----- */
    }
}
