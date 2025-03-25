package carmineerario.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SettingsPanel extends JPanel{
	private final int BUTTONS_WIDTH = 160;
	private final int BUTTONS_HEIGHT = 50;
	JSlider slider = new JSlider(0,100);
	GamePanel gp = (GamePanel) MainFrame.gamePanel;
	int value = 0;
	
	SettingsPanel(){
//		GameConfig.loadDifficulty();
//		GameConfig.loadSnakeColor();
		
		/* BUTTONS */
		// CHANGE SNAKE COLOR BUTTON
		JButton colorBtn = new JButton("Change snake color");
		colorBtn.setFocusable(false);
		colorBtn.setAlignmentX(CENTER_ALIGNMENT);
		colorBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));	
		colorBtn.addActionListener(e -> {
			/* COLOR CHOOSER */
			new JColorChooser();
			Color color = JColorChooser.showDialog(null, "Change snake color", new Color(0, 0, 0));
			
			if(color != null) {
				gp.snakeColor[0] = color.getRed();
				gp.snakeColor[1] = color.getGreen();
				gp.snakeColor[2] = color.getBlue();
			}
			/* ------------- */
		});
		
		// BACK TO HOME BUTTON
		JButton backBtn = new JButton("\u2B05 Back to home");
		backBtn.setFocusable(false);
		backBtn.setAlignmentX(CENTER_ALIGNMENT);
		backBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));	
		backBtn.addActionListener(e -> {
			MainFrame.showPanel("Homepage Panel");
			
			GameConfig.saveConfig(sliderToDelay(gp.getDelay()), gp.snakeColor);
			gp.setDelay(sliderToDelay(this.getDifficultyValue()));
		});
		/* ------- */

		/* DIFFICULTY SLIDER */
		JPanel sliderPanel = new JPanel(new FlowLayout());
		sliderPanel.setPreferredSize(new Dimension(300,0));
		
		JLabel difficultyLabel = new JLabel("Change difficulty");
		difficultyLabel.setAlignmentX(CENTER_ALIGNMENT);
		difficultyLabel.setFont(new Font("Dialog", Font.BOLD, 18));

		slider.setPreferredSize(new Dimension(300,50));
		slider.setMajorTickSpacing(33);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		slider.addChangeListener(e -> {
			value = slider.getValue();
			
			if(value > 81) slider.setValue(100);
			else if(value > 48 && value <= 81) slider.setValue(66);
			else if(value > 15 && value <= 48) slider.setValue(33);
			else slider.setValue(0);
			
			this.setDifficultyValue(value);
		});
		
		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put(0, new JLabel("Apple Eater"));
		labelTable.put(33, new JLabel("Normal"));
		labelTable.put(66, new JLabel("Pro"));
        labelTable.put(100, new JLabel("Snake Eater"));
        
        slider.setLabelTable(labelTable);
        
        sliderPanel.add(slider);
		/* ------- */
		
		/* PANEL */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(500,500));
		
		this.add(Box.createVerticalGlue());
		this.add(difficultyLabel);
		this.add(sliderPanel);
		this.add(colorBtn);
		this.add(Box.createVerticalStrut(50));
		this.add(backBtn);
		this.add(Box.createVerticalGlue());
		
		this.setVisible(true);
		/* ----- */
	}
	
	private void setDifficultyValue(int value) {
		this.value = value;
	}
	
	private int getDifficultyValue() {
		return this.value;
	}

	public int sliderToDelay(int value) {
		if(value == 0) return 100;
		else if(value == 33) return 80;
		else if(value == 66) return 30;
		else return 15;
	}
	
	public int delayToSlider(int value) {
		if(value == 100) return 0;
		else if(value == 80) return 33;
		else if(value == 30) return 66;
		else return 100;
	}
}