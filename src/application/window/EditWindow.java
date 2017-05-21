package application.window;

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
import application.exception.InvalidInputException;
import application.exception.InvalidInputFormatException;

import application.exception.RecordNotExistsException;
import application.model.Primatijada;
import application.service.PrimatijadaService;
import application.service.ValidationService;

import java.awt.Color;

public class EditWindow extends Window {

	private JFrame frame;
	private WindowController windowController;
	private JTextField indeksInput;
	private ButtonGroup buttonGroup;
	private JTextField optionInput;
	private JLabel optionLabel;
	private JLabel indeksInputErrorOutputLabel;
	private JLabel optionsInputErrorOutputLabel;
	private JLabel errorOutputLabel;
	private JLabel priceOutputLabel;

	private boolean showOptions = false;
	private static final int SHORT_OPTION_WIDTH = 10;
	private static final int LONG_OPTION_WIDTH = 20;
	private int optionInputWidth = SHORT_OPTION_WIDTH;
	private static final String SPORT_LABEL = "Sport";
	private static final String PAPERWORK_LABEL = "Rad";

	/**
	 * Create the application.
	 */
	public EditWindow(WindowController windowController, PrimatijadaService service,
			ValidationService validationService) {
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

		optionLabel = new JLabel("");
		final JPanel optionInputPanel = new JPanel();
		optionInput = new JTextField();
		optionInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String category = null;
				optionsInputErrorOutputLabel.setText("");

				// finds which radio button is selected
				for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.isSelected())
						category = button.getText();

				}
				optionsInputErrorOutputLabel.setText("");

				try {
					validationService.checkOptionsInput(category, optionInput.getText());
				} catch (EmptyInputException e1) {
					optionsInputErrorOutputLabel.setText(EMPTY_INPUT_ERROR);
				} catch (InvalidInputException e1) {
					optionsInputErrorOutputLabel.setText(INPUT_TOO_LONG);
				} catch (InvalidInputFormatException e1) {
					optionsInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				}

			}

		});
		buttonGroup = new ButtonGroup();

		JPanel indeksPanel = new JPanel();
		indeksPanel.setBounds(140, 39, 83, 29);
		frame.getContentPane().add(indeksPanel);

		JLabel indeksLabel = new JLabel("Indeks");
		indeksPanel.add(indeksLabel);

		JPanel indeksInputPanel = new JPanel();
		indeksInputPanel.setBounds(256, 39, 183, 29);
		frame.getContentPane().add(indeksInputPanel);

		indeksInput = new JTextField();
		indeksInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/*
				 * when indeksInput textField is filled and pressed Enter this
				 * method is invoked, it displays db data for screen
				 */

				errorOutputLabel.setText("");
				indeksInputErrorOutputLabel.setText("");
				optionsInputErrorOutputLabel.setText("");

				Primatijada primatijada = null;
				String tag = null;

				try {
					primatijada = service.retrievePrimatijada(indeksInput.getText());

					switch (primatijada.getTip()) {
					case 's':
						tag = SPORT_LABEL;
					case 'n':
						tag = PAPERWORK_LABEL;
					default:
						tag = "Navijac";
					}
					// searching which button to activate
					for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
						AbstractButton button = buttons.nextElement();

						if (button.getText().equalsIgnoreCase(tag)) {
							button.setSelected(true);
							if (tag.equalsIgnoreCase(SPORT_LABEL)) {
								sportOption(primatijada);
								updatePrice();
							} else if (tag.equalsIgnoreCase(PAPERWORK_LABEL)) {
								scienceOption(primatijada);
								updatePrice();
							} else {
								defaultOption();
								updatePrice();
							}
						}
					}
				} catch (NumberFormatException e1) {
					indeksInputErrorOutputLabel.setText(NUMBER_FORMAT_ERROR);
				} catch (RecordNotExistsException e1) {

					System.out.println("Ne postoji");
					indeksInputErrorOutputLabel.setText(INDEKS_NOT_EXISTS_ERROR);
				} catch (DataBaseBusyException e1) {
					errorOutputLabel.setText(DATA_BASE_BUSY_ERROR);
				} catch (IndeksFormatException e1) {
					indeksInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				} catch (EmptyInputException e1) {
					indeksInputErrorOutputLabel.setText(EMPTY_INPUT_ERROR);

				}

			}
		});
		indeksInputPanel.add(indeksInput);
		indeksInput.setColumns(10);

		JPanel categoryPanel = new JPanel();
		categoryPanel.setBounds(256, 130, 280, 29);
		frame.getContentPane().add(categoryPanel);

		buttonGroup = new ButtonGroup();

		JRadioButton categoryRB_1 = new JRadioButton("Navijac");
		categoryRB_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultOption();
				updatePrice();
			}
		});
		categoryRB_1.setSelected(true);
		categoryPanel.add(categoryRB_1);
		buttonGroup.add(categoryRB_1);

		JRadioButton categoryRB_2 = new JRadioButton("Sportista");
		categoryRB_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sportOption();
				updatePrice();
			}
		});
		categoryPanel.add(categoryRB_2);
		buttonGroup.add(categoryRB_2);

		JRadioButton categoryRB_3 = new JRadioButton("Naucnik");
		categoryRB_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scienceOption();
				updatePrice();
			}
		});
		categoryPanel.add(categoryRB_3);
		buttonGroup.add(categoryRB_3);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 97, 572, 2);
		frame.getContentPane().add(separator);

		JPanel categoryLabelPanel = new JPanel();
		categoryLabelPanel.setBounds(120, 130, 103, 29);
		frame.getContentPane().add(categoryLabelPanel);

		JLabel categoryLabel = new JLabel("Kategorija");
		categoryLabelPanel.add(categoryLabel);

		JPanel optionLabelPanel = new JPanel();
		optionLabelPanel.setBounds(95, 164, 103, 21);
		frame.getContentPane().add(optionLabelPanel);

		optionLabelPanel.add(optionLabel);
		optionLabel.setVisible(showOptions);

		optionInputPanel.setBounds(245, 164, 316, 29);
		frame.getContentPane().add(optionInputPanel);

		optionInputPanel.add(optionInput);
		optionInput.setColumns(optionInputWidth);
		optionInput.setVisible(showOptions);

		JButton backButton = new JButton("Nazad");

		backButton.setBounds(30, 13, 83, 25);
		frame.getContentPane().add(backButton);
		backButton.addActionListener(windowController);

		JPanel panel = new JPanel();
		panel.setBounds(245, 234, 223, 39);
		frame.getContentPane().add(panel);

		JButton updateButton = new JButton("Izmeni");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/*
				 * This button is invoked when Izmeni button is pressed and it
				 * picks up all data from input and sends it to service
				 */

				errorOutputLabel.setText("");
				indeksInputErrorOutputLabel.setText("");
				optionsInputErrorOutputLabel.setText("");

				String indeks = indeksInput.getText();
				String category = null;
				String options = optionInput.getText();

				for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.isSelected())
						category = button.getText();
				}

				// Validation
				try {
					service.updateRecord(indeks, category, options);
					float price = service.calculatePrice(indeks, category);
					priceOutputLabel.setText(price + " $");
				} catch (NumberFormatException e1) {
					indeksInputErrorOutputLabel.setText(NUMBER_FORMAT_ERROR);
				} catch (RecordNotExistsException e1) {
					indeksInputErrorOutputLabel.setText(INDEKS_NOT_EXISTS_ERROR);
				} catch (IndeksFormatException e1) {
					indeksInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				} catch (EmptyInputException e1) {
					optionsInputErrorOutputLabel.setText(EMPTY_INPUT_ERROR);
				} catch (InvalidInputException e1) {
					optionsInputErrorOutputLabel.setText(INPUT_TOO_LONG);
				} catch (InvalidInputFormatException e1) {
					optionsInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
				} catch (DataBaseBusyException e1) {
					errorOutputLabel.setText(DATA_BASE_BUSY_ERROR);
				}

			}
		});
		panel.add(updateButton);

		indeksInputErrorOutputLabel = new JLabel("");
		indeksInputErrorOutputLabel.setForeground(Color.RED);
		indeksInputErrorOutputLabel.setBounds(266, 70, 270, 15);
		frame.getContentPane().add(indeksInputErrorOutputLabel);

		optionsInputErrorOutputLabel = new JLabel("");
		optionsInputErrorOutputLabel.setForeground(Color.RED);
		optionsInputErrorOutputLabel.setBounds(281, 205, 280, 15);
		frame.getContentPane().add(optionsInputErrorOutputLabel);

		errorOutputLabel = new JLabel("");
		errorOutputLabel.setBounds(269, 285, 199, 15);
		frame.getContentPane().add(errorOutputLabel);

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

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String ObjButtons[] = { "Da", "Ne" };
				int PromptResult = JOptionPane.showOptionDialog(null, "Da li ste sigurni ?", "",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					e.getWindow().dispose();
					windowController.onWindowExit();
				}
			}
		});

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

	public void hide() {
		frame.setVisible(false);

	}

	private void sportOption(Primatijada primatijada) {

		showOptions = true;
		optionLabel.setVisible(showOptions);
		optionLabel.setText(SPORT_LABEL);
		optionInput.setVisible(showOptions);
		optionInput.setColumns(SHORT_OPTION_WIDTH);
		optionInput.setText(primatijada.getSport());
	}

	private void sportOption() {

		showOptions = true;
		optionLabel.setVisible(showOptions);
		optionLabel.setText(SPORT_LABEL);
		optionInput.setVisible(showOptions);
		optionInput.setColumns(SHORT_OPTION_WIDTH);
	}

	private void scienceOption(Primatijada primatijada) {
		showOptions = true;
		optionLabel.setVisible(showOptions);
		optionLabel.setText(PAPERWORK_LABEL);
		optionInput.setVisible(showOptions);
		optionInput.setColumns(LONG_OPTION_WIDTH);
		optionInput.setText(primatijada.getRad());
	}

	private void scienceOption() {
		showOptions = true;
		optionLabel.setVisible(showOptions);
		optionLabel.setText(PAPERWORK_LABEL);
		optionInput.setVisible(showOptions);
		optionInput.setColumns(LONG_OPTION_WIDTH);
	}

	private void defaultOption() {
		showOptions = false;
		optionLabel.setVisible(showOptions);
		optionInput.setVisible(showOptions);
	}

	private void updatePrice() {

		String indeks = indeksInput.getText();
		String category = null;

		// finds which radio button is selected
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected())
				category = button.getText();

		}

		float price;
		try {
			price = service.calculatePrice(indeks, category);
			priceOutputLabel.setText(price + " $");
		} catch (IndeksFormatException e1) {
			indeksInputErrorOutputLabel.setText(INVALID_INPUT_FORMAT);
		} catch (RecordNotExistsException e1) {

		} catch (DataBaseBusyException e1) {
			errorOutputLabel.setText(DATA_BASE_BUSY_ERROR);
		} catch (EmptyInputException e1) {
			indeksInputErrorOutputLabel.setText(EMPTY_INPUT_ERROR);
		}

	}
}
