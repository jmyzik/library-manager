package jmyzik.librarymanager.controller;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.service.AddBookFormService;

public class AddBookFormController {

	private AddBookFormService addBookFormService;
	
	public AddBookFormController() {
		addBookFormService = new AddBookFormService();
	}

	public void addBook(Book book) throws DatabaseUnavailableException {
		addBookFormService.addBook(book);
	}

}
