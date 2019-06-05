package jmyzik.librarymanager.ui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;

import jmyzik.librarymanager.domain.Reader;

public class EditReaderForm extends AddReaderForm {

	public EditReaderForm(JFrame parentFrame) {
		super(parentFrame);
		modifyLayout();
	}
	
	private void modifyLayout() {
		this.setTitle("Edytuj czytelnika");
		
		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Edytuj dane czytelnika");
		readerInfoPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
	}
	
	public void display(Reader reader) {
		setLocationRelativeTo(parentFrame);
		firstNameField.setText(reader.getFirstName());
		lastNameField.setText(reader.getLastName());
		streetField.setText(reader.getAddress().getStreet());
		houseNumberField.setText("" + reader.getAddress().getHouseNumber());
		Integer apartmentNumber = reader.getAddress().getApartmentNumber();
		apartmentNumberField.setText("" + ((apartmentNumber != null) ? apartmentNumber : ""));
		zipCodeField.setText(reader.getAddress().getZipCode());
		cityField.setText(reader.getAddress().getCity());
		setVisible(true);
		firstNameField.requestFocusInWindow();
	}
}
