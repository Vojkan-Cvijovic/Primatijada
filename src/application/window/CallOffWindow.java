package application.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import application.exception.RecordNotExistsException;
import application.model.Primatijada;
import application.service.PrimatijadaService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import com.ibm.db2.jcc.am.de;

public class CallOffWindow extends Window {

	private WindowController windowController;
	private JFrame frame;
	private JTextField indeksInput;
	
	public CallOffWindow(WindowController windowController, PrimatijadaService service) {
		this.windowController = windowController;
		initialize();
	}
	
	public void run(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		/* 
		 * Initializing base frame
		 */
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(TITLE);
		frame.setBounds(100, 100, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel indeksPanel = new JPanel();
		indeksPanel.setBounds(187, 80, 83, 29);
		frame.getContentPane().add(indeksPanel);

		JLabel indeksLabel = new JLabel("Indeks");
		indeksPanel.add(indeksLabel);

		JLabel lblNewLabel = new JLabel("Odjava");
		lblNewLabel.setBounds(248, 23, 70, 15);
		frame.getContentPane().add(lblNewLabel);

		JPanel indeksInputPanel = new JPanel();
		indeksInputPanel.setBounds(346, 80, 183, 29);
		frame.getContentPane().add(indeksInputPanel);
		
		indeksInput = new JTextField();
		indeksInputPanel.add(indeksInput);
		indeksInput.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBounds(343, 154, 186, 36);
		frame.getContentPane().add(panel);

		JButton btnNewButton = new JButton("Odjavi se");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* For Future development */
				
				/* When this method is envoked, it should pass to service indeks,
				 * and catch potentioal exceptions
				 */

				String indeks = indeksInput.getText();
				
				try{
					service.deleteRecord(indeks);	
				} catch (NumberFormatException e1) {
					System.out.println("ERROR: Indeks not valid");
				} catch (RecordNotExistsException e1) {
					System.out.println("ERROR: Indeks not found");
				}
				
				
				System.out.println("Odjavi se");
			}
		});
		panel.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(77, 154, 70, 15);
		frame.getContentPane().add(lblNewLabel_1);

		// Switching windows
		JButton backButton = new JButton("Nazad");
		backButton.addActionListener(windowController);
		backButton.setBounds(30, 13, 83, 25);
		frame.getContentPane().add(backButton);

	}

	public void hide() {
		frame.setVisible(false);
		
	}

}
