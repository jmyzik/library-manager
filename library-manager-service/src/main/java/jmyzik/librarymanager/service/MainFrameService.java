package jmyzik.librarymanager.service;

import java.util.List;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.query.MainFrameQuery;

public class MainFrameService {
	
	private MainFrameQuery query;
	
	public MainFrameService() {
		query = new MainFrameQuery();
	}
	
	public List<Book> getAllBooks() throws DatabaseUnavailableException {
		return query.getAllBooks();
	}
	
	public List<Reader> getAllReaders() throws DatabaseUnavailableException {
		return query.getAllReaders();
	}
	
	public boolean removeBook(Book book) throws DatabaseUnavailableException {
		return query.removeBook(book);
	}

	public boolean removeReader(Reader reader) throws DatabaseUnavailableException {
		return query.removeReader(reader);
	}

	public boolean borrowBook(BorrowTransaction transaction) throws DatabaseUnavailableException {
		Book book = transaction.getBook();
		int copies = book.getCopies();
		if (copies < 1) return false;
		book.setCopies(--copies);
		if (!query.modifyBook(book)) return false;
		query.addTransaction(transaction);
		return true;
	}

	public boolean restartConnection() {
		return query.restartConnection();
	}
	
	public void shutdownDatabase() {
		query.shutdown();
	}
}