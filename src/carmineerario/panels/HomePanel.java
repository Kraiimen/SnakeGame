package carmineerario.panels;

import carmineerario.main.MainFrame;

import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;
import javax.swing.*;

public class HomePanel extends JPanel {
    private final int BUTTONS_WIDTH = 160;
    private final int BUTTONS_HEIGHT = 50;

    public HomePanel(){
        JLabel gameNameTitle = createGameTitleLabel();

        /* BUTTONS PANEL */
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(MainFrame.BACKGROUND_COLOR);
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS)); // Set BoxLayout (vertical)

        JButton playBtn = createPlayButton();
        JButton recordsBtn = createRecordsButton();
        JButton settingsBtn = createSettingsButton();
        JButton exitBtn = createExitButton();

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

        this.setBackground(MainFrame.BACKGROUND_COLOR);
        this.add(Box.createVerticalGlue());
        this.add(gameNameTitle);
        this.add(Box.createVerticalStrut(70));
        this.add(btnPanel);
        this.add(Box.createVerticalStrut(30));

        this.setVisible(true);
        /* ----- */
    }

    private JLabel createGameTitleLabel(){
        JLabel gameNameTitle = new JLabel("Snake Game");
        // Icon: https://icons8.com/icon/yGwrZYYkmgaX/year-of-snake
        URL imageURL = getClass().getClassLoader().getResource("assets/icons/snakeIcon.png");
        if(imageURL != null){
            gameNameTitle.setIcon(new ImageIcon(imageURL));
        }
        gameNameTitle.setIconTextGap(10);
        gameNameTitle.setAlignmentX(CENTER_ALIGNMENT);
        gameNameTitle.setFont(new Font(null, Font.BOLD, 50));
        return gameNameTitle;
    }
    private JButton createPlayButton(){
        JButton playBtn = new JButton("Play");
        playBtn.setFocusable(false);
        playBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        playBtn.setMaximumSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        playBtn.setAlignmentX(CENTER_ALIGNMENT);
        playBtn.addActionListener(e -> MainFrame.showPanel("Game Panel"));
        return playBtn;
    }
    private JButton createRecordsButton() {
        JButton recordsBtn = new JButton("Records");
        recordsBtn.setFocusable(false);
        recordsBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        recordsBtn.setMaximumSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        recordsBtn.setAlignmentX(CENTER_ALIGNMENT);
        recordsBtn.addActionListener(e -> {
            MainFrame.recordsPanel.reloadRecords();
            MainFrame.showPanel("Records Panel");
        });
        return recordsBtn;
    }
    private JButton createSettingsButton() {
        JButton settingsBtn = new JButton("Settings");
        settingsBtn.setFocusable(false);
        settingsBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        settingsBtn.setMaximumSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        settingsBtn.setAlignmentX(CENTER_ALIGNMENT);
        settingsBtn.addActionListener(e -> MainFrame.showPanel("Settings Panel"));
        return settingsBtn;
    }
    private JButton createExitButton(){
        JButton exitBtn = new JButton("Exit");
        exitBtn.setFocusable(false);
        exitBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        exitBtn.setMaximumSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
        exitBtn.setAlignmentX(CENTER_ALIGNMENT);
        exitBtn.addActionListener(e -> System.exit(0));
        return exitBtn;
    }
}
