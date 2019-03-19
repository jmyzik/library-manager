package jmyzik.librarymanager.service;

import java.util.List;

import jmyzik.librarymanager.dao.BooksDAO;
import jmyzik.librarymanager.dao.GenericDAO;
import jmyzik.librarymanager.dao.ReadersDAO;
import jmyzik.librarymanager.dao.TransactionsDAO;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class MainFrameService {
	
	private BooksDAO booksDAO;
	private ReadersDAO readersDAO;
	private TransactionsDAO transactionsDAO;
	private GenericDAO genericDAO;
	
	public MainFrameService() {
		booksDAO = new BooksDAO();
		readersDAO = new ReadersDAO();
		transactionsDAO = new TransactionsDAO();
		genericDAO = new GenericDAO();
	}
	
	public List<Book> getAllBooks() throws DatabaseUnavailableException {
		return booksDAO.getAllBooks();
	}
	
	public List<Reader> getAllReaders() throws DatabaseUnavailableException {
		return readersDAO.getAllReaders();
	}
	
	public boolean removeBook(Book book) throws DatabaseUnavailableException {
		return booksDAO.removeBook(book);
	}

	public boolean removeReader(Reader reader) throws DatabaseUnavailableException {
		return readersDAO.removeReader(reader);
	}

	public boolean borrowBook(BorrowTransaction transaction) throws DatabaseUnavailableException {
		Book book = transaction.getBook();
		int copies = book.getCopies();
		if (copies < 1) return false;
		book.setCopies(--copies);
		if (!booksDAO.modifyBook(book)) return false;
		transactionsDAO.addTransaction(transaction);
		return true;
	}

	public boolean restartConnection() {
		return genericDAO.restartConnection();
	}
	
	public void shutdownDatabase() {
		genericDAO.shutdown();
	}
}