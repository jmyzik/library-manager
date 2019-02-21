package jmyzik.librarymanager.service;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.query.AddBookFormQuery;

public class AddBookFormService {
	
	AddBookFormQuery query;
	
	public AddBookFormService() {
		query = new AddBookFormQuery();
	}

	public void addBook(Book book) throws DatabaseUnavailableException {
		query.addBook(book);
	}
}
