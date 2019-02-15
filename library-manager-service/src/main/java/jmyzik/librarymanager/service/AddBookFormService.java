package jmyzik.librarymanager.service;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.Database;

public class AddBookFormService {

	public void addBook(Book book) {
		Database.INSTANCE.addBook(book);
	}
}
