package application.window;

import javax.swing.JFrame;

import application.service.PrimatijadaService;

public abstract class Window {
	
    protected static final int WIDTH = 600;
    protected static final int HEIGHT = 380;
    protected static final String TITLE = "Primatijada";

	protected PrimatijadaService service;
	
	abstract public void run();

}
