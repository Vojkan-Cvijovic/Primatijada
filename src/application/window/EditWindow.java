package application.window;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.ibm.db2.jcc.am.de;

import application.exception.RecordNotExistsException;
import application.service.PrimatijadaService;

public class EditWindow extends Window {

	private JFrame frame;
	private WindowController windowController;
	private JTextField indeksInput;
	private ButtonGroup buttonGroup;
	private JTextField optionInput;
	private JLabel optionLabel;

	private boolean showOptions = false;
	private static final int SHORT_OPTION_WIDTH = 10;
	private static final int LONG_OPTION_WIDTH = 20;
	private int optionInputWidth = SHORT_OPTION_WIDTH;
	private static final String SPORT_LABEL = "Sport";
	private static final String PAPERWORK_LABEL = "Rad";

	/**
	 * Create the application.
	 */
	public EditWindow(WindowController windowController,
			PrimatijadaService service) {
		this.service = service;
		this.windowController = windowController;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(TITLE);
		frame.setBounds(100, 100, 600, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		optionLabel = new JLabel("");
		final JPanel optionInputPanel = new JPanel();
		optionInput = new JTextField();
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

				String tag = null;

				try {
					tag = service.retriveCategory(indeksInput.getText());
				} catch (NumberFormatException e1) {
					System.out.println("ERROR: Indeks not valid");
				} catch (RecordNotExistsException e1) {
					System.out.println("ERROR: Indeks not found");
				}

				for (Enumeration<AbstractButton> buttons = buttonGroup
						.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.getText().equalsIgnoreCase(tag)) {
						button.setSelected(true);
						if(tag.equalsIgnoreCase(SPORT_LABEL))
							sportOption();
						else if(tag.equalsIgnoreCase(PAPERWORK_LABEL))
							scienceOption();
						else defaultOption();
					}
				}

			}
		});
		indeksInputPanel.add(indeksInput);
		indeksInput.setColumns(10);

		JPanel categoryPanel = new JPanel();
		categoryPanel.setBounds(256, 110, 280, 29);
		frame.getContentPane().add(categoryPanel);

		buttonGroup = new ButtonGroup();

		JRadioButton categoryRB_1 = new JRadioButton("Navijac");
		categoryRB_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultOption();
			}
		});
		categoryRB_1.setSelected(true);
		categoryPanel.add(categoryRB_1);
		buttonGroup.add(categoryRB_1);

		JRadioButton categoryRB_2 = new JRadioButton("Sportista");
		categoryRB_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sportOption();

			}
		});
		categoryPanel.add(categoryRB_2);
		buttonGroup.add(categoryRB_2);

		JRadioButton categoryRB_3 = new JRadioButton("Naucnik");
		categoryRB_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scienceOption();
			}
		});
		categoryPanel.add(categoryRB_3);
		buttonGroup.add(categoryRB_3);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 76, 572, 2);
		frame.getContentPane().add(separator);

		JPanel categoryLabelPanel = new JPanel();
		categoryLabelPanel.setBounds(120, 110, 103, 29);
		frame.getContentPane().add(categoryLabelPanel);

		JLabel categoryLabel = new JLabel("Kategorija");
		categoryLabelPanel.add(categoryLabel);

		JPanel optionLabelPanel = new JPanel();
		optionLabelPanel.setBounds(95, 164, 103, 21);
		frame.getContentPane().add(optionLabelPanel);

		optionLabelPanel.add(optionLabel);
		optionLabel.setVisible(showOptions);

		optionInputPanel.setBounds(242, 156, 316, 29);
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

				String indeks = indeksInput.getText();
				String category = null;

				for (Enumeration<AbstractButton> buttons = buttonGroup
						.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.isSelected())
						category = button.getText();
				}

				try {
					service.updateInfo(indeks, category);
				} catch (NumberFormatException e1) {
					System.out.println("ERROR: Indeks not valid");
				} catch (RecordNotExistsException e1) {
					System.out.println("ERROR: Indeks not found");
				}

			}
		});
		panel.add(updateButton);

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

	private void sportOption() {
		showOptions = true;
		optionLabel.setVisible(showOptions);
		optionLabel.setText(SPORT_LABEL);
		optionInput.setVisible(showOptions);
		optionInput.setColumns(SHORT_OPTION_WIDTH);
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
}
