package a8;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		LifeModel model = new LifeModel();
		LifeView view = new LifeView();
		LifeController controller = new LifeController(model, view);
		
		JFrame window = new JFrame();
		window.setTitle("Conway's Game of Life");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		window.setContentPane(view);
		
		window.pack();
		window.setVisible(true);
	}
}
