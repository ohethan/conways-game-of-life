package a8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LifeController implements ActionListener, ChangeListener, MouseListener, LifeObserver, Runnable {

	LifeModel model;
	LifeView view;
	
	int speed;
	boolean running;
	int surviveMin;
	int surviveMax;
	int birthMin;
	int birthMax;
	boolean torusOn;
	
	public LifeController(LifeModel model, LifeView view) {
		this.model = model;
		this.view = view;
		
		// default game rules
		speed = 1000;
		running = false;
		surviveMin = 2;
		surviveMax = 3;
		birthMin = 3;
		birthMax = 3;
		torusOn = false;
		model.setSize(10);
		
		view.addChangeListener(this);
		view.addMouseListener(this);
		view.addActionListener(this);
		
		
		model.addObserver(this);
	}

	@Override
	public void update(LifeModel life, boolean[][] grid) {
		view.setState(grid);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String name = button.getName();
		
		switch (name) {
		case "start":
			running = true;
			(new Thread(this)).start();
			view.startEnabled(false);
			view.stopEnabled(true);
			break;
		case "stop":
			running = false;
			view.startEnabled(true);
			view.stopEnabled(false);
			break;
		case "next":
			if (torusOn) {
				model.torusNext(surviveMin, surviveMax, birthMin, birthMax);
			} else {
				model.next(surviveMin, surviveMax, birthMin, birthMax);
			}
			break;
		case "clear":
			model.clear();
			break;
		case "rand":
			model.randomize();
			break;
		case "torusOn":
			torusOn = true;
			view.torusOnEnabled(false);
			view.torusOffEnabled(true);
			break;
		case "torusOff":
			torusOn = false;
			view.torusOnEnabled(true);
			view.torusOffEnabled(false);
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().getClass().isInstance(new JSlider())) {
			JSlider source = (JSlider) e.getSource();
			if (source.getName().equals("size")) {
				if (!source.getValueIsAdjusting()) {
			        int size = (int) source.getValue();
			        if (size != model.getSize()) {
			        	model.setSize(size);		        	
			        }
			    }
			} else if (source.getName().equals("speed")) {
				if (!source.getValueIsAdjusting()) {
					speed = (int) source.getValue();
				}
			}
		} else if (e.getSource().getClass().isInstance(new JSpinner())) {
			JSpinner source = (JSpinner) e.getSource();
			SpinnerNumberModel spinnerModel = (SpinnerNumberModel) source.getModel();
			String name = source.getName();
			
			switch (name) {
			case "surviveMin":
				surviveMin = (int) spinnerModel.getNumber();
				break;
			case "surviveMax":
				surviveMax = (int) spinnerModel.getNumber();
				break;
			case "birthMin":
				birthMin = (int) spinnerModel.getNumber();
				break;
			case "birthMax":
				birthMax = (int) spinnerModel.getNumber();
				break;
			}
		}
	}
	
	@Override
	public void run() {
		while (running) {
			try {
				if (torusOn) {
					model.torusNext(surviveMin, surviveMax, birthMin, birthMax);
				} else if (!torusOn) {
					model.next(surviveMin, surviveMax, birthMin, birthMax);
				}
				Thread.sleep(speed);
			} catch (Exception e) {}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int row = (int)(e.getX() / (LifeView.DEFAULT_SCREEN_WIDTH/(double)model.getSize()));
		int col = (int)(e.getY() / (LifeView.DEFAULT_SCREEN_HEIGHT/(double)model.getSize()));
		model.toggleTileAt(row, col);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
