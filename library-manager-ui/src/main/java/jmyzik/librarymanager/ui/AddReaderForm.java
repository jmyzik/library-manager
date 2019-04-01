package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class AddReaderForm extends JDialog {

	private JFrame parentFrame;
	
	private JButton cancelButton;
	private JButton saveButton;
	
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel streetLabel;
	private JLabel houseNumberLabel;
	private JLabel apartmentNumberLabel;
	private JLabel zipCodeLabel;
	private JLabel cityLabel;
	
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField streetField;
	private JTextField houseNumberField;
	private JTextField apartmentNumberField;
	private JTextField zipCodeField;
	private JTextField cityField;
	
	public AddReaderForm(JFrame parentFrame) {
		super(parentFrame, "Dodaj czytelnika", true);

		this.parentFrame = parentFrame;
		initializeVariables();
		constructLayout();
		setWindow();
	}

	private void initializeVariables() {
		cancelButton = new JButton("Anuluj");
		saveButton = new JButton("Zapisz");

		firstNameLabel = new JLabel("Imiê");
		firstNameField = new JTextField(15);
		lastNameLabel = new JLabel("Nazwisko");
		lastNameField = new JTextField(15);
		streetLabel = new JLabel("Ulica");
		streetField = new JTextField(15);
		houseNumberLabel = new JLabel("Nr domu");
		houseNumberField = new JTextField(15);
		apartmentNumberLabel = new JLabel("Nr mieszkania");
		apartmentNumberField = new JTextField(15);
		zipCodeLabel = new JLabel("Kod pocztowy");
		zipCodeField = new JTextField(15);
		cityLabel = new JLabel("Miasto");
		cityField = new JTextField(15);
	}
	
	private void constructLayout() {

		JPanel bookInfoPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		JLabel[] labels = new JLabel[] { firstNameLabel, lastNameLabel, streetLabel, houseNumberLabel, apartmentNumberLabel, zipCodeLabel, cityLabel };
		JTextField[] textFields = new JTextField[] { firstNameField, lastNameField, streetField, houseNumberField, apartmentNumberField, zipCodeField, cityField };
		
		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Dodaj nowego czytelnika");
		bookInfoPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

		bookInfoPanel.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);

		for (int i = 0; i < labels.length; i++) {
			gc.gridy = i;

			gc.gridx = 0;
			gc.anchor = GridBagConstraints.EAST;
			gc.insets = rightPadding;
			bookInfoPanel.add(labels[i], gc);

			gc.gridx = 1;
			gc.anchor = GridBagConstraints.WEST;
			gc.insets = noPadding;
			bookInfoPanel.add(textFields[i], gc);			
		}

		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(saveButton);
		buttonsPanel.add(cancelButton);

		setLayout(new BorderLayout());
		add(bookInfoPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.PAGE_END);
	}

	private void setWindow() {
		setSize(350, 350);
		setResizable(false);
		setLocationRelativeTo(parentFrame);
	}
		
	public void display() {
		setLocationRelativeTo(parentFrame);
		firstNameField.setText("");
		lastNameField.setText("");
		streetField.setText("");
		houseNumberField.setText("");
		apartmentNumberField.setText("");
		zipCodeField.setText("");
		cityField.setText("");
		setVisible(true);
		firstNameField.requestFocusInWindow();
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JTextField getFirstNameField() {
		return firstNameField;
	}

	public JTextField getLastNameField() {
		return lastNameField;
	}

	public JTextField getStreetField() {
		return streetField;
	}

	public JTextField getHouseNumberField() {
		return houseNumberField;
	}

	public JTextField getApartmentNumberField() {
		return apartmentNumberField;
	}

	public JTextField getZipCodeField() {
		return zipCodeField;
	}

	public JTextField getCityField() {
		return cityField;
	}
}