package jmyzik.librarymanager.controller;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.EntityManagerHandler;
import jmyzik.librarymanager.service.BookFormService;
import jmyzik.librarymanager.ui.EditBookForm;

public class EditBookFormController {

	private EditBookForm editBookForm;
	private BookFormService bookFormService;
	private BookTableChangedCallback bookTableChangedCallback;
	
	private JButton cancelButton;
	private JButton saveButton;
	private JTextField titleField;
	private JTextField authorField;
	private JTextField publisherField;
	private JTextField publicationYearField;
	private JTextField copiesField;

	private Book editedBook = null;
	
	public EditBookFormController(EditBookForm editBookForm) {
		this.editBookForm = editBookForm;
		bookFormService = new BookFormService();

		cancelButton = editBookForm.getCancelButton();
		saveButton = editBookForm.getSaveButton();
		titleField = editBookForm.getTitleField();
		authorField = editBookForm.getAuthorField();
		publisherField = editBookForm.getPublisherField();
		publicationYearField = editBookForm.getPublicationYearField();
		copiesField = editBookForm.getCopiesField();

		addListeners();
	}

	private void addListeners() {
		cancelButton.addActionListener(e -> onCancelClicked());
		saveButton.addActionListener(e -> onSaveClicked());
	}

	public void setBookTableChangedCallback(BookTableChangedCallback bookTableChangedCallback) {
		this.bookTableChangedCallback = bookTableChangedCallback;
	}
	
	public void displayForm(Book book) {
		editedBook = book;
		editBookForm.display(book);
	}
	
	private void onCancelClicked() {
		editBookForm.setVisible(false);		
	}
	
	private void onSaveClicked() {
		if (editedBook == null) {
			JOptionPane.showMessageDialog(editBookForm,
					"Wyst¹pi³ b³¹d. Dane nie zosta³y wprowadzone.",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
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
			JOptionPane.showMessageDialog(editBookForm,
					"WprowadŸ poprawne dane",
					"B³êdne dane",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		editedBook.setTitle(title);
		editedBook.setAuthor(author);
		editedBook.setPublisher(publisher);
		editedBook.setPublicationYear(publicationYear);
		editedBook.setCopies(copies);

		EntityManager em = null;
		try {
			em = EntityManagerHandler.INSTANCE.getNewEntityManager();
			bookFormService.modifyBook(editedBook, em);
			bookTableChangedCallback.bookTableChanged();
		} catch (IllegalStateException e) {
			JOptionPane.showMessageDialog(editBookForm,
					"Wyst¹pi³ problem z baz¹ danych, zmiany nie zosta³y wprowadzone",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		editBookForm.setVisible(false);
	}
	
	private boolean validateData(String title, String author, String publisher, int publicationYear, int copies) {
		return !title.equals("") &&
				!author.equals("") &&
				!publisher.equals("") &&
				publicationYear != -1 &&
				copies >= 0;
	}
}