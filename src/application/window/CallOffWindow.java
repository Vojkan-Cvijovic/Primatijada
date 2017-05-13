package application.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CallOffWindow extends Window {

	private WindowController windowController;

	/**
	 * Create the application.
	 */
	public CallOffWindow(WindowController windowController) {
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

		JTextField indeksInput = new JTextField();
		indeksInputPanel.add(indeksInput);
		indeksInput.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBounds(343, 154, 186, 36);
		frame.getContentPane().add(panel);

		JButton btnNewButton = new JButton("Odjavi se");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Odjavis e");
			}
		});
		panel.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(77, 154, 70, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JButton backButton = new JButton("Nazad");
		backButton.addActionListener(windowController);
		backButton.setBounds(30, 13, 83, 25);
		frame.getContentPane().add(backButton);

	}

	public void hide() {
		frame.setVisible(false);
		
	}

}
