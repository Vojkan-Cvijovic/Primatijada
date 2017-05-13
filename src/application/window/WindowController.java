package application.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowController implements ActionListener{
	private CallOffWindow callOffWindow;
	private SignUpWindow signUpWindow;
	private EditWindow editWindow;
	
	private static final String BACK_BUTTON = "Nazad";
	private static final String CALL_OFF_BUTTON = "Odjava";
	private static final String EDIT_BUTTON = "Izmeni";

	public WindowController() {
		initialize();
	}

	private void initialize() {		
		callOffWindow = new CallOffWindow(this);
		signUpWindow = new SignUpWindow(this);
		editWindow = new EditWindow(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equalsIgnoreCase(BACK_BUTTON)){
			callOffWindow.hide();
			editWindow.hide();
			signUpWindow.run();
		}
		else if(e.getActionCommand().equalsIgnoreCase(CALL_OFF_BUTTON)){
			signUpWindow.hide();
			callOffWindow.run();
		}else if(e.getActionCommand().equalsIgnoreCase(EDIT_BUTTON)){
			signUpWindow.hide();
			editWindow.run();
		}
		
	}

	public void run() {
		signUpWindow.run();
		
	}

}
