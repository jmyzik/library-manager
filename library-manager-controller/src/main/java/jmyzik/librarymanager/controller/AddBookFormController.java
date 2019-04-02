package jmyzik.librarymanager.controller;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.service.AddBookFormService;
import jmyzik.librarymanager.ui.AddBookForm;

public class AddBookFormController {

	private AddBookForm addBookForm;
	private AddBookFormService addBookFormService;
	private BookTableChangedCallback bookTableChangedCallback;
	
	private JButton cancelButton;
	private JButton saveButton;
	private JTextField titleField;
	private JTextField authorField;
	private JTextField publisherField;
	private JTextField publicationYearField;
	private JTextField copiesField;
	
	public AddBookFormController(AddBookForm addBookForm) {
		this.addBookForm = addBookForm;
		addBookFormService = new AddBookFormService();

		cancelButton = addBookForm.getCancelButton();
		saveButton = addBookForm.getSaveButton();
		titleField = addBookForm.getTitleField();
		authorField = addBookForm.getAuthorField();
		publisherField = addBookForm.getPublisherField();
		publicationYearField = addBookForm.getPublicationYearField();
		copiesField = addBookForm.getCopiesField();

		addListeners();
	}

	private void addListeners() {
		cancelButton.addActionListener(e -> onCancelClicked());
		saveButton.addActionListener(e -> onSaveClicked());
	}

	public void setBookTableChangedCallback(BookTableChangedCallback bookTableChangedCallback) {
		this.bookTableChangedCallback = bookTableChangedCallback;
	}
	
	public void displayForm() {
		addBookForm.display();
	}
	
	private void onCancelClicked() {
		addBookForm.setVisible(false);		
	}
	
	private void onSaveClicked() {
		String title = titleField.getText();
		String author = authorField.getText();
		String publisher = publisherField.getText();
		int publicationYear;
		try {
			publicationYear = Integer.parseInt(publicationYearField.getText());
		} catch (NumberFormatException e) {
			publicationYear = -1;
		}
		int copies;
		try {
			copies = Integer.parseInt(copiesField.getText());
		} catch (NumberFormatException e) {
			copies = -1;
		}

		if(!validateData(title, author, publisher, publicationYear, copies)) {
			JOptionPane.showMessageDialog(addBookForm,
					"WprowadŸ poprawne dane",
					"B³êdne dane",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Book newBook = new Book(title, author, publisher, publicationYear, copies);

		try {
			addBookFormService.addBook(newBook);
			bookTableChangedCallback.bookTableChanged();
		} catch (DatabaseUnavailableException e) {
			JOptionPane.showMessageDialog(addBookForm,
					"Wyst¹pi³ problem z baz¹ danych, zmiany nie zosta³y wprowadzone",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
		}
		addBookForm.setVisible(false);
	}
	
	private boolean validateData(String title, String author, String publisher, int publicationYear, int copies) {
		return !title.equals("") &&
				!author.equals("") &&
				!publisher.equals("") &&
				publicationYear != -1 &&
				copies >= 0;
	}
}