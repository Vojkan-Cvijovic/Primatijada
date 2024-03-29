package application.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import application.exception.DataBaseBusyException;
import application.exception.EmptyInputException;
import application.exception.IndeksFormatException;
import application.exception.RecordNotExistsException;
import application.service.PrimatijadaService;
import application.service.ValidationService;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class CallOffWindow extends Window {

	private WindowController windowController;
	private JFrame frame;
	private JTextField indeksInput;
	private JLabel indeksErrorOutputLabel;
	private JLabel errorOutputLabel;

	public CallOffWindow(WindowController windowController,
			PrimatijadaService service, ValidationService validationService) {
		this.windowController = windowController;
		this.service = service;
		this.validationService = validationService;
		initialize();
	}

	public void run() {

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
		indeksPanel.setBounds(149, 80, 83, 29);
		frame.getContentPane().add(indeksPanel);

		JLabel indeksLabel = new JLabel("Indeks");
		indeksPanel.add(indeksLabel);

		JLabel lblNewLabel = new JLabel("Odjava");
		lblNewLabel.setBounds(248, 23, 70, 15);
		frame.getContentPane().add(lblNewLabel);

		JPanel indeksInputPanel = new JPanel();
		indeksInputPanel.setBounds(261, 80, 183, 29);
		frame.getContentPane().add(indeksInputPanel);

		indeksInput = new JTextField();
		indeksInputPanel.add(indeksInput);
		indeksInput.setColumns(10);
		indeksInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					validationService.checkIfIndeksExists(indeksInput.getText());
				} catch (NumberFormatException e1) {
					indeksErrorOutputLabel.setText(NUMBER_FORMAT_ERROR);
				} catch (EmptyInputException e1) {
					indeksErrorOutputLabel.setText(EMPTY_INPUT_ERROR);
				} catch (RecordNotExistsException e1) {
					indeksErrorOutputLabel.setText(INDEKS_NOT_EXISTS_ERROR);
				} catch (DataBaseBusyException e1) {
					errorOutputLabel.setForeground(Color.RED);
					errorOutputLabel.setText(DATA_BASE_BUSY_ERROR);
				} catch (IndeksFormatException e1) {
					indeksErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				}
				
			}
		});
		JPanel panel = new JPanel();
		panel.setBounds(318, 180, 186, 36);
		frame.getContentPane().add(panel);

		JButton btnNewButton = new JButton("Odjavi se");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* For Future development */

				/*
				 * When this method is envoked, it should pass to service
				 * indeks, and catch potential exceptions
				 */
				errorOutputLabel.setForeground(Color.RED);
				indeksErrorOutputLabel.setText("");

				String indeks = indeksInput.getText();

				try {
					service.deleteRecord(indeks);
					errorOutputLabel.setForeground(Color.GREEN);
					errorOutputLabel.setText("Uspesno ste se odjavili!");
				
				} catch (NumberFormatException e1) {
					indeksErrorOutputLabel.setText(NUMBER_FORMAT_ERROR);
				} catch (RecordNotExistsException e2) {
					indeksErrorOutputLabel.setText(INDEKS_NOT_EXISTS_ERROR);
				} catch (IndeksFormatException e1) {
					indeksErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				} catch (DataBaseBusyException e1) {
					errorOutputLabel.setForeground(Color.RED);
					errorOutputLabel.setText(DATA_BASE_BUSY_ERROR);
				} catch (EmptyInputException e1) {
					indeksErrorOutputLabel.setText(EMPTY_INPUT_ERROR);
				}

				
			}
		});
		panel.add(btnNewButton);

		// Switching windows
		JButton backButton = new JButton("Nazad");
		backButton.addActionListener(windowController);
		backButton.setBounds(30, 13, 83, 25);
		frame.getContentPane().add(backButton);

		indeksErrorOutputLabel = new JLabel("");
		indeksErrorOutputLabel.setForeground(Color.RED);
		indeksErrorOutputLabel.setBounds(292, 110, 276, 24);
		frame.getContentPane().add(indeksErrorOutputLabel);

		errorOutputLabel = new JLabel("");
		errorOutputLabel.setForeground(Color.RED);
		errorOutputLabel.setBounds(318, 237, 183, 15);
		frame.getContentPane().add(errorOutputLabel);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String ObjButtons[] = { "Da", "Ne" };
				int PromptResult = JOptionPane.showOptionDialog(null,
						"Da li ste sigurni ?", "", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, ObjButtons,
						ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
					windowController.onWindowExit();
				}
			}
		});

	}

	public void hide() {
		frame.setVisible(false);

	}

}
