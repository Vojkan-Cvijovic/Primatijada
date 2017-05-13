package application.window;

import javax.swing.JFrame;

public abstract class Window {
	
    private static final int WIDTH = 600;
    private static final int HEIGHT = 380;
    private static final String TITLE = "Primatijada";
    
	protected JFrame frame;
	
	public Window() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(TITLE);
		frame.setBounds(100, 100, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	}

}
