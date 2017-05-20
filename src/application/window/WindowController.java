package application.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import application.service.PrimatijadaService;
import application.service.ValidationService;


/* Class is in charge of controlling which window is displayed.
 * Default window is SignUp.*/

public class WindowController implements ActionListener{
	
	private CallOffWindow callOffWindow;
	private SignUpWindow signUpWindow;
	private EditWindow editWindow;
	private PrimatijadaService service;
	private ValidationService validationService;
	
	private static final String BACK_BUTTON = "Nazad";
	private static final String CALL_OFF_BUTTON = "Odjava";
	private static final String EDIT_BUTTON = "Izmeni";

	public WindowController() {
		initialize();
	}

	private void initialize() {
		validationService = new ValidationService();
		service = new PrimatijadaService(validationService);
		callOffWindow = new CallOffWindow(this, service, validationService);
		signUpWindow = new SignUpWindow(this, service, validationService);
		editWindow = new EditWindow(this, service, validationService);
		
	}

	/* Detects which ActionEvent is invoked and responds to it*/
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equalsIgnoreCase(BACK_BUTTON)){
			callOffWindow.hide();
			editWindow.hide();
			signUpWindow.run();
		}
		else if(e.getActionCommand().equalsIgnoreCase(CALL_OFF_BUTTON)){
			signUpWindow.hideWindow();
			callOffWindow.run();
		}else if(e.getActionCommand().equalsIgnoreCase(EDIT_BUTTON)){
			signUpWindow.hideWindow();
			editWindow.run();
		}
		
	}
	
	public void run() {
		signUpWindow.run();
		
	}

}
