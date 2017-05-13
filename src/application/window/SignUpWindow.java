package application.window;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class SignUpWindow extends Window{

	private WindowController windowController;
	private JTextField indeksInput;
	private ButtonGroup buttonGroup;
	private JTextField optionInput;

	private boolean showOptions = false;
	private static final int SHORT_OPTION_WIDTH = 10;
	private static final int LONG_OPTION_WIDTH = 20;
	private int optionInputWidth = SHORT_OPTION_WIDTH;
	private static final String SPORT_LABEL = "Sport";
	private static final String PAPERWORK_LABEL = "Rad";
	private final ButtonGroup arrangementButtonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
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
	 * Create the application.
	 */
	public SignUpWindow(WindowController windowController) {
		this.windowController = windowController;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		

		final JLabel optionLabel = new JLabel("");
		final JPanel optionInputPanel = new JPanel();
		optionInput = new JTextField();
		
		
		JPanel indeksPanel = new JPanel();
		indeksPanel.setBounds(95, 39, 83, 29);
		frame.getContentPane().add(indeksPanel);

		JLabel indeksLabel = new JLabel("Indeks");
		indeksPanel.add(indeksLabel);
		
		JLabel lblNewLabel = new JLabel("Prijava");
		lblNewLabel.setBounds(24, 12, 70, 15);
		frame.getContentPane().add(lblNewLabel);

		JPanel indeksInputPanel = new JPanel();
		indeksInputPanel.setBounds(256, 39, 183, 29);
		frame.getContentPane().add(indeksInputPanel);

		indeksInput = new JTextField();
		indeksInputPanel.add(indeksInput);
		indeksInput.setColumns(10);

		JPanel categoryPanel = new JPanel();
		categoryPanel.setBounds(256, 139, 280, 29);
		frame.getContentPane().add(categoryPanel);

		buttonGroup = new ButtonGroup();

		JRadioButton categoryRB_1 = new JRadioButton("Navijac");
		categoryRB_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOptions = false;
				optionLabel.setVisible(showOptions);
				optionInput.setVisible(showOptions);
			}
		});
		categoryRB_1.setSelected(true);
		categoryPanel.add(categoryRB_1);
		buttonGroup.add(categoryRB_1);

		JRadioButton categoryRB_2 = new JRadioButton("Sportista");
		categoryRB_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOptions = true;
				optionLabel.setVisible(showOptions);
				optionLabel.setText(SPORT_LABEL);
				optionInput.setVisible(showOptions);
				optionInput.setColumns(SHORT_OPTION_WIDTH);

		}
		});
		categoryPanel.add(categoryRB_2);
		buttonGroup.add(categoryRB_2);

		JRadioButton categoryRB_3 = new JRadioButton("Naucnik");
		categoryRB_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOptions = true;
				optionLabel.setVisible(showOptions);
				optionLabel.setText(PAPERWORK_LABEL);
				optionInput.setVisible(showOptions);
				optionInput.setColumns(LONG_OPTION_WIDTH);
			}
		});
		categoryPanel.add(categoryRB_3);
		buttonGroup.add(categoryRB_3);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 76, 584, 2);
		frame.getContentPane().add(separator);

		JPanel panel = new JPanel();
		panel.setBounds(95, 131, 103, 29);
		frame.getContentPane().add(panel);

		JLabel categoryLabel = new JLabel("Kategorija");
		panel.add(categoryLabel);



		JPanel optionLabelPanel = new JPanel();
		optionLabelPanel.setBounds(105, 180, 103, 21);
		frame.getContentPane().add(optionLabelPanel);

		optionLabelPanel.add(optionLabel);
		optionLabel.setVisible(showOptions);

		optionInputPanel.setBounds(223, 180, 316, 29);
		frame.getContentPane().add(optionInputPanel);
		
		optionInputPanel.add(optionInput);
		optionInput.setColumns(optionInputWidth);
		optionInput.setVisible(showOptions);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(95, 90, 103, 29);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Aranzman");
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(258, 99, 183, 28);
		frame.getContentPane().add(panel_2);
		
		JRadioButton arrangementOptionRB_1 = new JRadioButton("Ceo");
		arrangementOptionRB_1.setSelected(true);
		arrangementButtonGroup.add(arrangementOptionRB_1);
		panel_2.add(arrangementOptionRB_1);
		
		JRadioButton arrangementOptionRB_2 = new JRadioButton("Pola");
		arrangementButtonGroup.add(arrangementOptionRB_2);
		panel_2.add(arrangementOptionRB_2);
		
		JPanel signupPanel = new JPanel();
		signupPanel.setBounds(353, 258, 183, 45);
		frame.getContentPane().add(signupPanel);
		
		JButton signupButton = new JButton("Prijavi se!");
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// sign up 
				
				
			}
		});
		signupPanel.add(signupButton);
		
		JPanel priceLabelPanel = new JPanel();
		priceLabelPanel.setBounds(35, 258, 56, 29);
		frame.getContentPane().add(priceLabelPanel);
		
		JLabel lblNewLabel_2 = new JLabel("Cena :");
		priceLabelPanel.add(lblNewLabel_2);
		
		JPanel priceOutputPanel = new JPanel();
		priceOutputPanel.setBounds(72, 258, 136, 29);
		frame.getContentPane().add(priceOutputPanel);
		
		JLabel priceOutput = new JLabel("110 $");
		priceOutputPanel.add(priceOutput);
		
		JButton callOffButton = new JButton("Odjava");
		callOffButton.addActionListener(windowController);
		callOffButton.setBounds(501, 12, 83, 25);
		frame.getContentPane().add(callOffButton);
		
	}

	public void hide() {
		frame.setVisible(false);
		
	}
}
