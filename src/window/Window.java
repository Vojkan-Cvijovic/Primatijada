package window;

import javax.swing.JFrame;

public abstract class Window {
	
	protected static final int WIDTH = 600;
	protected static final int HEIGHT = 380;
	protected JFrame frame;
	
	public Window() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Primatijada ");
		frame.setBounds(100, 100, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	}

}
