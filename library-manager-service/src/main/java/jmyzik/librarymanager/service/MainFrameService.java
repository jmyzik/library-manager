package jmyzik.librarymanager.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jmyzik.librarymanager.dao.BooksDAO;
import jmyzik.librarymanager.dao.ConnectionManager;
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
	private ConnectionManager connectionManager;
	
	public MainFrameService() {
		booksDAO = new BooksDAO();
		readersDAO = new ReadersDAO();
		transactionsDAO = new TransactionsDAO();
		connectionManager = new ConnectionManager();
	}
	
	public List<Book> getAllBooks(EntityManager em) {
		return booksDAO.getAllBooks(em);
	}
	
	public List<Reader> getAllReaders(EntityManager em) {
		return readersDAO.getAllReaders(em);
	}
	
	public boolean removeBook(Book book, EntityManager em) {
		EntityTransaction trans = em.getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
		if (booksDAO.removeBook(book, em)) {
			trans.commit();
			return true;
		} else {
			trans.rollback();
			return false;
		}
	}

	public boolean removeReader(Reader reader, EntityManager em) {
		EntityTransaction trans = em.getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
		if (readersDAO.removeReader(reader, em)) {
			trans.commit();
			return true;
		} else {
			trans.rollback();
			return false;
		}
	}

	public boolean borrowBook(BorrowTransaction transaction, EntityManager em) {
		Book book = transaction.getBook();
		int copies = book.getCopies();
		if (copies < 1) return false;

		EntityTransaction trans = em.getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
		transactionsDAO.addTransaction(transaction, em);
		book.setCopies(--copies);
		if (booksDAO.modifyBook(book, em)) {
			trans.commit();
			return true;
		} else {
			trans.rollback();
			return false;
		}
	}

	public boolean restartConnection() {
		return connectionManager.restartConnection();
	}
	
	public void shutdownDatabase() {
		connectionManager.shutdown();
	}
}