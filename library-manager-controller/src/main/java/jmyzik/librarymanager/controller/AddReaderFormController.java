package jmyzik.librarymanager.controller;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jmyzik.librarymanager.callbacks.ReaderTableChangedCallback;
import jmyzik.librarymanager.domain.Address;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.service.AddReaderFormService;
import jmyzik.librarymanager.ui.AddReaderForm;

public class AddReaderFormController {
	
	private AddReaderForm addReaderForm;
	private AddReaderFormService addReaderFormService;
	private ReaderTableChangedCallback readerTableChangedCallback;
	
	private JButton cancelButton;
	private JButton saveButton;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField streetField;
	private JTextField houseNumberField;
	private JTextField apartmentNumberField;
	private JTextField zipCodeField;
	private JTextField cityField;
	
	public AddReaderFormController(AddReaderForm addReaderForm) {
		this.addReaderForm = addReaderForm;
		addReaderFormService = new AddReaderFormService();

		cancelButton = addReaderForm.getCancelButton();
		saveButton = addReaderForm.getSaveButton();
		firstNameField = addReaderForm.getFirstNameField();
		lastNameField = addReaderForm.getLastNameField();
		streetField = addReaderForm.getStreetField();
		houseNumberField = addReaderForm.getHouseNumberField();
		apartmentNumberField = addReaderForm.getApartmentNumberField();
		zipCodeField = addReaderForm.getZipCodeField();
		cityField = addReaderForm.getCityField();
		
		addListeners();
	}

	private void addListeners() {
		cancelButton.addActionListener(e -> onCancelClicked());
		saveButton.addActionListener(e -> onSaveClicked());
	}

	public void setReaderTableChangedCallback(ReaderTableChangedCallback readerTableChangedCallback) {
		this.readerTableChangedCallback = readerTableChangedCallback;
	}	

	public void displayForm() {
		addReaderForm.display();
	}
	
	private void onCancelClicked() {
		addReaderForm.setVisible(false);		
	}
	
	private void onSaveClicked() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String street = streetField.getText();
		int houseNumber;
		try {
			houseNumber = Integer.parseInt(houseNumberField.getText());
		} catch (NumberFormatException e) {
			houseNumber = -1;
		}
		Integer apartmentNumber;
		try {
			apartmentNumber = Integer.parseInt(apartmentNumberField.getText());
		} catch (NumberFormatException e) {
			apartmentNumber = null;
		}
		String zipCode = zipCodeField.getText();
		String city = cityField.getText();

		if(!validateData(firstName, lastName, street, houseNumber, apartmentNumber, zipCode, city)) {
			JOptionPane.showMessageDialog(addReaderForm,
					"WprowadŸ poprawne dane",
					"B³êdne dane",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Address address = new Address(street, houseNumber, apartmentNumber, zipCode, city);
		Reader newReader = new Reader(firstName, lastName, address);

		try {
			addReaderFormService.addReader(newReader);
			readerTableChangedCallback.readerTableChanged();
		} catch (DatabaseUnavailableException e) {
			JOptionPane.showMessageDialog(addReaderForm,
					"Wyst¹pi³ problem z baz¹ danych, zmiany nie zosta³y wprowadzone",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
		}
		addReaderForm.setVisible(false);
	}
	
	private boolean validateData(String firstName, String lastName, String street, int houseNumber, Integer apartmentNumber, String zipCode, String city) {
		return !firstName.equals("") &&
				!lastName.equals("") &&
				!street.equals("") &&
				houseNumber > 0 &&
				(apartmentNumber == null || apartmentNumber > 0) &&
				!zipCode.equals("") &&
				!city.equals("");
	}
}