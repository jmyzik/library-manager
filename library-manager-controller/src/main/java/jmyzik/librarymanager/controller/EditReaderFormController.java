package jmyzik.librarymanager.controller;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jmyzik.librarymanager.callbacks.ReaderTableChangedCallback;
import jmyzik.librarymanager.domain.Address;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.EntityManagerHandler;
import jmyzik.librarymanager.service.ReaderFormService;
import jmyzik.librarymanager.ui.EditReaderForm;

public class EditReaderFormController {
	
	private EditReaderForm editReaderForm;
	private ReaderFormService readerFormService;
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
	
	private Reader editedReader = null;
	
	public EditReaderFormController(EditReaderForm editReaderForm) {
		this.editReaderForm = editReaderForm;
		readerFormService = new ReaderFormService();

		cancelButton = editReaderForm.getCancelButton();
		saveButton = editReaderForm.getSaveButton();
		firstNameField = editReaderForm.getFirstNameField();
		lastNameField = editReaderForm.getLastNameField();
		streetField = editReaderForm.getStreetField();
		houseNumberField = editReaderForm.getHouseNumberField();
		apartmentNumberField = editReaderForm.getApartmentNumberField();
		zipCodeField = editReaderForm.getZipCodeField();
		cityField = editReaderForm.getCityField();
		
		addListeners();
	}

	private void addListeners() {
		cancelButton.addActionListener(e -> onCancelClicked());
		saveButton.addActionListener(e -> onSaveClicked());
	}

	public void setReaderTableChangedCallback(ReaderTableChangedCallback readerTableChangedCallback) {
		this.readerTableChangedCallback = readerTableChangedCallback;
	}	

	public void displayForm(Reader reader) {
		editedReader = reader;
		editReaderForm.display(reader);
	}
	
	private void onCancelClicked() {
		editReaderForm.setVisible(false);		
	}
	
	private void onSaveClicked() {
		if (editedReader == null) {
			JOptionPane.showMessageDialog(editReaderForm,
					"Wyst¹pi³ b³¹d. Dane nie zosta³y wprowadzone.",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
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
			JOptionPane.showMessageDialog(editReaderForm,
					"WprowadŸ poprawne dane",
					"B³êdne dane",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Address address = new Address(street, houseNumber, apartmentNumber, zipCode, city);
		editedReader.setFirstName(firstName);
		editedReader.setLastName(lastName);
		editedReader.setAddress(address);

		EntityManager em = null;
		try {
			em = EntityManagerHandler.INSTANCE.getNewEntityManager();
			readerFormService.modifyReader(editedReader, em);
			readerTableChangedCallback.readerTableChanged();
		} catch (IllegalStateException e) {
			JOptionPane.showMessageDialog(editReaderForm,
					"Wyst¹pi³ problem z baz¹ danych, zmiany nie zosta³y wprowadzone",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		editReaderForm.setVisible(false);
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
