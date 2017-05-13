package application.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowController implements ActionListener{
	private CallOffWindow callOffWindow;
	private SignUpWindow signUpWindow;
	private static final String BACK_BUTTON = "Nazad";
	private static final String CALL_OFF_BUTTON = "Odjava";

	public WindowController() {
		initialize();
	}

	private void initialize() {		
		callOffWindow = new CallOffWindow(this);
		signUpWindow = new SignUpWindow(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equalsIgnoreCase(BACK_BUTTON)){
			callOffWindow.hide();
			signUpWindow.run();
		}
		else if(e.getActionCommand().equalsIgnoreCase(CALL_OFF_BUTTON)){
			signUpWindow.hide();
			callOffWindow.run();
		}
		
	}

	public void run() {
		signUpWindow.run();
		
	}

}
