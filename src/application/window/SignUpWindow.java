package application.window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import application.exception.DataBaseBusyException;
import application.exception.EmptyInputException;
import application.exception.IndeksFormatException;
import application.exception.InvalidInputFormatException;
import application.exception.InvalidInputLengthException;
import application.exception.PrimaryKeyTakenException;
import application.exception.RecordNotExistsException;
import application.service.PrimatijadaService;
import application.service.ValidationService;

public class SignUpWindow extends Window {

	private JFrame frame;
	private WindowController windowController;
	private JTextField indeksInput = null;
	private ButtonGroup categoryRadioButtonGroup;
	private JTextField optionInput;
	private JLabel indeksInputErrorOutputLabel;
	private JLabel errorOutputLabel;
	private JLabel optionsInputErrorOutputLabel;
	private JLabel priceOutputLabel;

	private boolean showOptions = false;
	private static final int SHORT_OPTION_WIDTH = 10;
	private static final int LONG_OPTION_WIDTH = 20;
	private int optionInputWidth = SHORT_OPTION_WIDTH;
	private static final String SPORT_LABEL = "Sport";
	private static final String PAPERWORK_LABEL = "Rad";
	private ButtonGroup arrangementButtonGroup = new ButtonGroup();

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

	public SignUpWindow(WindowController windowController,
			PrimatijadaService service, ValidationService validationService) {
		this.service = service;
		this.windowController = windowController;
		this.validationService = validationService;
		initialize();
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
		frame.setBounds(100, 100, 600, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		final JLabel optionLabel = new JLabel("");
		final JPanel optionInputPanel = new JPanel();
		optionInput = new JTextField();
		optionInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String category = null;
				errorOutputLabel.setText("");

				// finds which radio button is selected
				for (Enumeration<AbstractButton> buttons = categoryRadioButtonGroup
						.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.isSelected())
						category = button.getText();

				}
				optionsInputErrorOutputLabel.setText("");

				try {
					validationService.checkOptionsInput(category,
							optionInput.getText());

				} catch (EmptyInputException e1) {
					optionsInputErrorOutputLabel.setText(EMPTY_INPUT_ERROR);
				} catch (InvalidInputLengthException e1) {
					optionsInputErrorOutputLabel.setText(INPUT_TOO_LONG);
				} catch (InvalidInputFormatException e1) {
					optionsInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				}

			}
		});

		JPanel indeksPanel = new JPanel();
		indeksPanel.setBounds(95, 39, 83, 29);
		frame.getContentPane().add(indeksPanel);

		JLabel indeksLabel = new JLabel("Indeks");
		indeksPanel.add(indeksLabel);

		JLabel signUpLabel = new JLabel("Prijava");
		signUpLabel.setBounds(24, 12, 70, 15);
		frame.getContentPane().add(signUpLabel);

		JPanel indeksInputPanel = new JPanel();
		indeksInputPanel.setBounds(223, 39, 160, 29);
		frame.getContentPane().add(indeksInputPanel);

		indeksInput = new JTextField();
		indeksInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				indeksInputErrorOutputLabel.setText("");
				errorOutputLabel.setText("");

				boolean exists = false;

				try {
					exists = validationService.checkIfIndeksExists(indeksInput
							.getText());

					if (exists) {
						indeksInputErrorOutputLabel.setText(INDEKS_TAKEN_ERROR);
					}

				} catch (NumberFormatException e1) {
					indeksInputErrorOutputLabel.setText(NUMBER_FORMAT_ERROR);
				} catch (EmptyInputException e1) {
					indeksInputErrorOutputLabel.setText(EMPTY_INPUT_ERROR);
				} catch (RecordNotExistsException e1) {
					updatePrice();
				} catch (DataBaseBusyException e1) {
					errorOutputLabel.setForeground(Color.RED);
					errorOutputLabel.setText(DATA_BASE_BUSY_ERROR);
				} catch (IndeksFormatException e1) {
					indeksInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				}

			}
		});

		indeksInputPanel.add(indeksInput);
		indeksInput.setColumns(10);

		JPanel categoryPanel = new JPanel();
		categoryPanel.setBounds(256, 156, 280, 29);
		frame.getContentPane().add(categoryPanel);

		/* Creating button group for options radio buttons */

		categoryRadioButtonGroup = new ButtonGroup();

		JRadioButton categoryRB_1 = new JRadioButton("Navijac");
		categoryRB_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOptions = false;
				optionLabel.setVisible(showOptions);
				optionInput.setVisible(showOptions);
				optionInput.setText("");

				updatePrice();

			}
		});
		categoryRB_1.setSelected(true);
		categoryPanel.add(categoryRB_1);
		categoryRadioButtonGroup.add(categoryRB_1);

		JRadioButton categoryRB_2 = new JRadioButton("Sportista");
		categoryRB_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOptions = true;
				optionLabel.setVisible(showOptions);
				optionLabel.setText(SPORT_LABEL);
				optionInput.setVisible(showOptions);
				optionInput.setColumns(SHORT_OPTION_WIDTH);
				optionInput.setText("");
				updatePrice();
			}
		});
		categoryPanel.add(categoryRB_2);
		categoryRadioButtonGroup.add(categoryRB_2);

		JRadioButton categoryRB_3 = new JRadioButton("Naucnik");
		categoryRB_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOptions = true;
				optionLabel.setVisible(showOptions);
				optionLabel.setText(PAPERWORK_LABEL);
				optionInput.setVisible(showOptions);
				optionInput.setColumns(LONG_OPTION_WIDTH);
				optionInput.setText("");

				updatePrice();

			}
		});
		categoryPanel.add(categoryRB_3);
		categoryRadioButtonGroup.add(categoryRB_3);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 91, 572, 2);
		frame.getContentPane().add(separator);

		JPanel categoryLabelPanel = new JPanel();
		categoryLabelPanel.setBounds(95, 156, 103, 29);
		frame.getContentPane().add(categoryLabelPanel);

		JLabel categoryLabel = new JLabel("Kategorija");
		categoryLabelPanel.add(categoryLabel);

		/*
		 * Poping out options, its visibility depends of input
		 */

		JPanel optionLabelPanel = new JPanel();
		optionLabelPanel.setBounds(108, 206, 103, 21);
		frame.getContentPane().add(optionLabelPanel);

		optionLabelPanel.add(optionLabel);
		optionLabel.setVisible(showOptions);

		optionInputPanel.setBounds(223, 209, 313, 29);
		frame.getContentPane().add(optionInputPanel);

		optionInputPanel.add(optionInput);
		optionInput.setColumns(optionInputWidth);
		optionInput.setVisible(showOptions);

		JPanel arrangementPanel = new JPanel();
		arrangementPanel.setBounds(95, 115, 103, 29);
		frame.getContentPane().add(arrangementPanel);

		/*
		 * Arrangement part of the window
		 */

		JLabel arrangementLabel = new JLabel("Aranzman");
		arrangementPanel.add(arrangementLabel);

		JPanel arrangementOptionPanel = new JPanel();
		arrangementOptionPanel.setBounds(256, 116, 183, 28);
		frame.getContentPane().add(arrangementOptionPanel);

		JRadioButton arrangementOptionRB_1 = new JRadioButton("Ceo");
		arrangementOptionRB_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				updatePrice();
			}
		});
		arrangementOptionRB_1.setSelected(true);
		arrangementButtonGroup.add(arrangementOptionRB_1);
		arrangementOptionPanel.add(arrangementOptionRB_1);

		JRadioButton arrangementOptionRB_2 = new JRadioButton("Pola");
		arrangementOptionRB_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				updatePrice();
			}
		});
		arrangementButtonGroup.add(arrangementOptionRB_2);
		arrangementOptionPanel.add(arrangementOptionRB_2);

		JPanel signupPanel = new JPanel();
		signupPanel.setBounds(353, 283, 183, 45);
		frame.getContentPane().add(signupPanel);

		JButton signupButton = new JButton("Prijavi se!");
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * When sign up button is pressed
				 */
				String indeks = indeksInput.getText();
				String category = null;
				String arrangement = null;
				String options = optionInput.getText();
				errorOutputLabel.setText("");
				indeksInputErrorOutputLabel.setText("");
				optionsInputErrorOutputLabel.setText("");

				// finds which radio button is selected
				for (Enumeration<AbstractButton> buttons = categoryRadioButtonGroup
						.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.isSelected())
						category = button.getText();

				}

				// finds which radio button is selected
				for (Enumeration<AbstractButton> buttons = arrangementButtonGroup
						.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.isSelected())
						arrangement = button.getText();

				}
				// Validation
				try {
					service.signUp(indeks, category, arrangement, options);

					float price = service.calculatePrice(indeks, category,
							arrangement);
					priceOutputLabel.setText(price + " $");
					errorOutputLabel.setForeground(Color.GREEN);
					errorOutputLabel.setText("Uspesno ste se prijavili!");
					
				} catch (PrimaryKeyTakenException e1) {
					indeksInputErrorOutputLabel.setText(INDEKS_TAKEN_ERROR);
				} catch (NumberFormatException e2) {
					indeksInputErrorOutputLabel.setText(NUMBER_FORMAT_ERROR);
				} catch (EmptyInputException e1) {
					if (indeks.trim().equalsIgnoreCase(""))
						indeksInputErrorOutputLabel.setText(EMPTY_INPUT_ERROR);

					if (!category.equalsIgnoreCase("Navijac")
							&& options.trim().equalsIgnoreCase(""))
						optionsInputErrorOutputLabel.setText(EMPTY_INPUT_ERROR);
				} catch (DataBaseBusyException e1) {
					errorOutputLabel.setForeground(Color.RED);
					errorOutputLabel.setText(DATA_BASE_BUSY_ERROR);
				} catch (IndeksFormatException e1) {
					indeksInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				} catch (InvalidInputFormatException e1) {
					optionsInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				} catch (InvalidInputLengthException e1) {
					optionsInputErrorOutputLabel.setText(INPUT_TOO_LONG);
				} 
			}
		});
		signupPanel.add(signupButton);

		JPanel priceLabelPanel = new JPanel();
		priceLabelPanel.setBounds(38, 283, 56, 29);
		frame.getContentPane().add(priceLabelPanel);

		JLabel lblNewLabel_2 = new JLabel("Cena :");
		priceLabelPanel.add(lblNewLabel_2);

		JPanel priceOutputPanel = new JPanel();
		priceOutputPanel.setBounds(72, 283, 136, 29);
		frame.getContentPane().add(priceOutputPanel);

		priceOutputLabel = new JLabel("   ");
		priceOutputPanel.add(priceOutputLabel);

		/*---------------------*/

		// Switching window
		JButton callOffButton = new JButton("Odjava");
		callOffButton.addActionListener(windowController);
		callOffButton.setBounds(501, 12, 83, 25);
		frame.getContentPane().add(callOffButton);

		// Switching window
		JButton edit = new JButton("Izmeni");
		edit.addActionListener(windowController);
		edit.setBounds(501, 43, 83, 25);
		frame.getContentPane().add(edit);

		indeksInputErrorOutputLabel = new JLabel("");
		indeksInputErrorOutputLabel.setForeground(Color.RED);
		indeksInputErrorOutputLabel.setBounds(245, 67, 223, 24);
		frame.getContentPane().add(indeksInputErrorOutputLabel);

		optionsInputErrorOutputLabel = new JLabel("");
		optionsInputErrorOutputLabel.setForeground(Color.RED);
		optionsInputErrorOutputLabel.setBounds(313, 241, 230, 15);
		frame.getContentPane().add(optionsInputErrorOutputLabel);

		errorOutputLabel = new JLabel("");
		errorOutputLabel.setBounds(394, 326, 200, 15);
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

	public void hideWindow() {
		frame.setVisible(false);

	}

	private void updatePrice() {
		String indeks = indeksInput.getText();
		String category = null;
		String arrangement = null;

		// finds which radio button is selected
		for (Enumeration<AbstractButton> buttons = categoryRadioButtonGroup
				.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected())
				category = button.getText();

		}

		// finds which radio button is selected
		for (Enumeration<AbstractButton> buttons = arrangementButtonGroup
				.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected())
				arrangement = button.getText();

		}

		float price;
		try {
			price = service.calculatePrice(indeks, category, arrangement);
			priceOutputLabel.setText(price + " $");
		} catch (IndeksFormatException e1) {
			indeksInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
		} catch (DataBaseBusyException e1) {
			
			errorOutputLabel.setForeground(Color.RED);
			errorOutputLabel.setText(DATA_BASE_BUSY_ERROR);
		} catch (EmptyInputException e1) {
			indeksInputErrorOutputLabel.setText(EMPTY_INPUT_ERROR);
		}

	}
}
