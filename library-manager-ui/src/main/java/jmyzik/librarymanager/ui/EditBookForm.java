package jmyzik.librarymanager.ui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;

import jmyzik.librarymanager.domain.Book;

public class EditBookForm extends AddBookForm {

	public EditBookForm(JFrame parentFrame) {
		super(parentFrame);
		modifyLayout();
	}
	
	private void modifyLayout() {
		this.setTitle("Edytuj ksi¹¿kê");
		
		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Edytuj dane ksi¹¿ki");
		bookInfoPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
	}
	
	public void display(Book book) {
		setLocationRelativeTo(parentFrame);
		titleField.setText(book.getTitle());
		authorField.setText(book.getAuthor());
		publisherField.setText(book.getPublisher());
		publicationYearField.setText("" + book.getPublicationYear());
		copiesField.setText("" + book.getCopies());
		setVisible(true);
		titleField.requestFocusInWindow();
	}
}