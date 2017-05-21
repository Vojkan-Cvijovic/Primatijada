package application;

import application.window.WindowController;

public class Application {
	
	private WindowController windowController;

	/**
	 * Create the application, and runs it.
	 */
	public static void main(String[] args) {
		
		WindowController windowController = new WindowController();
		
		windowController.run();
	}

}
