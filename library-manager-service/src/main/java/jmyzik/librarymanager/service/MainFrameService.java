package jmyzik.librarymanager.service;

import java.util.List;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.query.MainFrameQuery;

public class MainFrameService {
	
	MainFrameQuery query;
	
	public MainFrameService() {
		query = new MainFrameQuery();
	}
	
	public List<Book> getAllBooks() {
		return query.getAllBooks();
	}
	
	public List<Reader> getAllReaders() {
		return query.getAllReaders();
	}
	
	public boolean removeBook(Book book) {
		return query.removeBook(book);
	}

	public boolean removeReader(Reader reader) {
		return query.removeReader(reader);
	}
}