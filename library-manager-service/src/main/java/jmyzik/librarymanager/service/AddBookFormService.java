package jmyzik.librarymanager.service;

import jmyzik.librarymanager.dao.BooksDAO;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class AddBookFormService {
	
	BooksDAO booksDAO;
	
	public AddBookFormService() {
		booksDAO = new BooksDAO();
	}

	public void addBook(Book book) throws DatabaseUnavailableException {
		booksDAO.addBook(book);
	}
}
