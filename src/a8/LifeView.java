package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LifeView extends JPanel {
	static final int DEFAULT_SCREEN_WIDTH = 500;
	static final int DEFAULT_SCREEN_HEIGHT = 500;
	
	private int size = 10;
	private boolean[][] state;
	
	JPanel board;
	JSlider sizeSlider;
	JSlider speedSlider;
	
	JButton startButton;
	JButton stopButton;
	JButton nextButton;
	JButton clearButton;
	JButton randButton;
	
	JButton torusOn;
	JButton torusOff;
	
	JSpinner surviveMinSpinner;
	JSpinner surviveMaxSpinner;
	JSpinner birthMinSpinner;
	JSpinner birthMaxSpinner;

	public LifeView() {
		setLayout(new BorderLayout());
		setBackground(new Color(250, 250, 250));
		state = new boolean[size][size];
		
		// game board
		board = new JPanel();
		board.setPreferredSize(new Dimension(DEFAULT_SCREEN_WIDTH,DEFAULT_SCREEN_HEIGHT));
		add(board, BorderLayout.WEST);
		
		// ui panel
		JPanel subpanel = new JPanel();
		subpanel.setPreferredSize(new Dimension(250,DEFAULT_SCREEN_HEIGHT));
		subpanel.setLayout(new BoxLayout(subpanel, BoxLayout.Y_AXIS));
		add(subpanel, BorderLayout.EAST);
		
		// size slider
		JLabel sizeLabel = new JLabel("Size");
		sizeLabel.setAlignmentX(CENTER_ALIGNMENT);
		sizeLabel.setPreferredSize(new Dimension(200, 20));
		subpanel.add(sizeLabel);
		
		JPanel sizeSliderPanel = new JPanel();
		sizeSlider = new JSlider(10, 500, size);
		sizeSlider.setName("size");
		sizeSlider.setPreferredSize(new Dimension(200, 50));
		sizeSliderPanel.add(sizeSlider);
		sizeSlider.setMajorTickSpacing(490);
		sizeSlider.setMinorTickSpacing(35);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setPaintTicks(true);
		subpanel.add(sizeSliderPanel);
		
		// speed slider
		JLabel speedLabel = new JLabel("Speed (ms between ticks)");
		speedLabel.setAlignmentX(CENTER_ALIGNMENT);
		speedLabel.setPreferredSize(new Dimension(200, 20));
		subpanel.add(speedLabel);
		
		JPanel speedSliderPanel = new JPanel();
		speedSlider = new JSlider(10, 1000, 1000);
		speedSlider.setName("speed");
		speedSlider.setInverted(true);
		speedSlider.setPreferredSize(new Dimension(200, 50));
		speedSliderPanel.add(speedSlider);
		speedSlider.setMajorTickSpacing(990);
		speedSlider.setMinorTickSpacing(99);
		speedSlider.setPaintLabels(true);
		speedSlider.setPaintTicks(true);
		subpanel.add(speedSliderPanel);
		
		
		JPanel buttonPanel1 = new JPanel();
		buttonPanel1.setPreferredSize(new Dimension(200, 20));
		buttonPanel1.setLayout(new FlowLayout());
		// start button
		startButton = new JButton("Start");
		startButton.setFocusable(false);
		startButton.setName("start");
		buttonPanel1.add(startButton);
		// stop button
		stopButton = new JButton("Stop");
		stopButton.setFocusable(false);
		stopButton.setName("stop");
		stopButton.setEnabled(false);
		buttonPanel1.add(stopButton);
		// next button
		nextButton = new JButton("Next");
		nextButton.setFocusable(false);
		nextButton.setName("next");
		buttonPanel1.add(nextButton);
		
		subpanel.add(buttonPanel1);
		
		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.setPreferredSize(new Dimension(200, 20));
		buttonPanel2.setLayout(new FlowLayout());
		// clear button
		clearButton = new JButton("Clear");
		clearButton.setFocusable(false);
		clearButton.setName("clear");
		buttonPanel2.add(clearButton);
		// randomize button
		randButton = new JButton("Randomize");
		randButton.setFocusable(false);
		randButton.setName("rand");
		buttonPanel2.add(randButton);
		subpanel.add(buttonPanel2);
		
		JPanel torusMode = new JPanel();
		torusMode.setPreferredSize(new Dimension(200, 20));
		torusMode.setLayout(new FlowLayout());
		// torus toggle
		torusMode.add(new JLabel("Torus Mode:"));
		torusOn = new JButton("On");
		torusOn.setFocusable(false);
		torusOn.setName("torusOn");
		torusMode.add(torusOn);
		torusOff = new JButton("Off");
		torusOff.setFocusable(false);
		torusOff.setName("torusOff");
		torusOff.setEnabled(false);
		torusMode.add(torusOff);
		subpanel.add(torusMode);
		
		JPanel survivePanel = new JPanel();
		survivePanel.setLayout(new BoxLayout(survivePanel, BoxLayout.Y_AXIS));
		
		JPanel surviveMin = new JPanel();
		surviveMin.setPreferredSize(new Dimension(200, 20));
		surviveMin.add(new JLabel("Survival Rate Min:"));
		surviveMinSpinner = new JSpinner(new SpinnerNumberModel(2, 0, 8, 1));
		surviveMinSpinner.setEditor(new JSpinner.DefaultEditor(surviveMinSpinner));
		surviveMinSpinner.setName("surviveMin");
		surviveMin.add(surviveMinSpinner);
		survivePanel.add(surviveMin);
		
		JPanel surviveMax = new JPanel();
		surviveMax.setPreferredSize(new Dimension(200, 20));
		surviveMax.add(new JLabel("Survival Rate Max:"));
		surviveMaxSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 8, 1));
		surviveMaxSpinner.setEditor(new JSpinner.DefaultEditor(surviveMaxSpinner));
		surviveMaxSpinner.setName("surviveMax");
		surviveMax.add(surviveMaxSpinner);
		survivePanel.add(surviveMax);
		
		JPanel birthMin = new JPanel();
		birthMin.setPreferredSize(new Dimension(200, 20));
		birthMin.add(new JLabel("Birth Rate Min:"));
		birthMinSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 8, 1));
		birthMinSpinner.setEditor(new JSpinner.DefaultEditor(birthMinSpinner));
		birthMinSpinner.setName("birthMin");
		birthMin.add(birthMinSpinner);
		survivePanel.add(birthMin);
		
		JPanel birthMax = new JPanel();
		birthMax.setPreferredSize(new Dimension(200, 20));
		birthMax.add(new JLabel("Birth Rate Max:"));
		birthMaxSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 8, 1));
		birthMaxSpinner.setEditor(new JSpinner.DefaultEditor(birthMaxSpinner));
		birthMaxSpinner.setName("birthMax");
		birthMax.add(birthMaxSpinner);
		survivePanel.add(birthMax);
		
		subpanel.add(survivePanel);
		
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		double width = DEFAULT_SCREEN_WIDTH/ (double) size;
		double height = DEFAULT_SCREEN_HEIGHT/ (double) size;
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				boolean isAlive = state[r][c];
				if (isAlive) {
					g.setColor(Color.black);
					g.fillRect((int) (r*width), (int) (c*height), (int) width, (int) height);
				} else {
					g.setColor(new Color(250, 250, 250));
					g.fillRect((int) (r*width), (int) (c*height), (int) width, (int) height);
				}
			}
		}
	}
	
	public void setState(boolean[][] state) {
		this.state = state;
		this.size = state.length;
		repaint();
	}
	
	public void startEnabled(boolean b) {
		startButton.setEnabled(b);
	}
	
	public void stopEnabled(boolean b) {
		stopButton.setEnabled(b);
	}
	
	public void torusOnEnabled(boolean b) {
		torusOn.setEnabled(b);
	}
	
	public void torusOffEnabled(boolean b) {
		torusOff.setEnabled(b);
	}
	
	public void addChangeListener(ChangeListener l) {
		sizeSlider.addChangeListener(l);
		speedSlider.addChangeListener(l);
		surviveMinSpinner.addChangeListener(l);
		surviveMaxSpinner.addChangeListener(l);
		birthMinSpinner.addChangeListener(l);
		birthMaxSpinner.addChangeListener(l);
	}
	
	public void addMouseListener(MouseListener l) {
		board.addMouseListener(l);
	}
	
	public void addActionListener(ActionListener l) {
		startButton.addActionListener(l);
		stopButton.addActionListener(l);
		nextButton.addActionListener(l);
		clearButton.addActionListener(l);
		randButton.addActionListener(l);
		torusOn.addActionListener(l);
		torusOff.addActionListener(l);
	}
}
