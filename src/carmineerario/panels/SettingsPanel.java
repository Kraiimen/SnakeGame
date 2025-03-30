package carmineerario.panels;

import carmineerario.config.GameConfig;
import carmineerario.main.MainFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

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
	public JSlider slider = new JSlider(0,100);
	int difficultyValue = 0;
	
	public SettingsPanel(){
		/* BUTTONS */
		JButton colorBtn = createColorButton();
		JButton backBtn = createBackButton();
		/* ------- */

		/* DIFFICULTY SLIDER */
		JPanel sliderPanel = createSliderPanel();
		JLabel difficultyLabel = createDifficultyLabel();
		setupSlider();
		sliderPanel.add(slider);
		/* ------- */
		
		/* PANEL */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(500,500));
		this.setBackground(MainFrame.BACKGROUND_COLOR);

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

	private JButton createColorButton(){
		JButton colorBtn = new JButton("Change snake color");
		colorBtn.setFocusable(false);
		colorBtn.setAlignmentX(CENTER_ALIGNMENT);
		colorBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
		colorBtn.addActionListener(e -> {
			/* COLOR CHOOSER */
			new JColorChooser();
			Color color = JColorChooser.showDialog(null, "Change snake color", new Color(0, 0, 0));

			if(color != null) {
				MainFrame.gamePanel.snakeColor[0] = color.getRed();
				MainFrame.gamePanel.snakeColor[1] = color.getGreen();
				MainFrame.gamePanel.snakeColor[2] = color.getBlue();
			}
			/* ------------- */
		});
		return colorBtn;
	}
	private JButton createBackButton(){
		JButton backBtn = new JButton("\u2B05 Back to home");
		backBtn.setFocusable(false);
		backBtn.setAlignmentX(CENTER_ALIGNMENT);
		backBtn.setPreferredSize(new Dimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
		backBtn.addActionListener(e -> {
			MainFrame.showPanel("Homepage Panel");

			GameConfig.saveConfig(sliderToDelay(getDifficultyValue()), MainFrame.gamePanel.snakeColor);
			MainFrame.gamePanel.setDelay(sliderToDelay(this.getDifficultyValue()));
		});
		return backBtn;
	}
	private JPanel createSliderPanel(){
		JPanel sliderPanel = new JPanel(new FlowLayout());
		sliderPanel.setPreferredSize(new Dimension(300,0));
		sliderPanel.setBackground(MainFrame.BACKGROUND_COLOR);
		return  sliderPanel;
	}
	private JLabel createDifficultyLabel(){
		JLabel difficultyLabel = new JLabel("Change difficulty");
		difficultyLabel.setAlignmentX(CENTER_ALIGNMENT);
		difficultyLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		return difficultyLabel;
	}
	private void setupSlider(){
		slider.setBackground(MainFrame.BACKGROUND_COLOR);
		slider.setPreferredSize(new Dimension(300,50));
		slider.setMajorTickSpacing(33);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		slider.addChangeListener(e -> {
			difficultyValue = slider.getValue();

			if(difficultyValue > 81) slider.setValue(100);
			else if(difficultyValue > 48) slider.setValue(66);
			else if(difficultyValue > 15) slider.setValue(33);
			else slider.setValue(0);

			this.setDifficultyValue(difficultyValue);
		});

		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put(0, new JLabel("Apple Eater"));
		labelTable.put(33, new JLabel("Normal"));
		labelTable.put(66, new JLabel("Pro"));
		labelTable.put(100, new JLabel("Snake Eater"));

		slider.setLabelTable(labelTable);
	}

	// Setter and Getter for difficultyValue
	private void setDifficultyValue(int difficultyValue) {
		this.difficultyValue = difficultyValue;
	}
	private int getDifficultyValue() {
		return this.difficultyValue;
	}
}