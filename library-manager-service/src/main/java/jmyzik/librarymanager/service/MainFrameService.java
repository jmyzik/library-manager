package jmyzik.librarymanager.service;

import java.util.List;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.Database;

public class MainFrameService {
	
	public MainFrameService() {
	}
	
	public List<Book> getAllBooks() {
		return Database.INSTANCE.getAllBooks();
	}
	
	public List<Reader> getAllReaders() {
		return Database.INSTANCE.getAllReaders();
	}
	
	public boolean removeBook(Book book) {
		return Database.INSTANCE.removeBook(book);
	}

	public boolean removeReader(Reader reader) {
		return Database.INSTANCE.removeReader(reader);
	}
}
