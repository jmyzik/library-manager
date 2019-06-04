package jmyzik.librarymanager.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jmyzik.librarymanager.dao.BooksDAO;
import jmyzik.librarymanager.dao.TransactionsDAO;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;

public class ReaderPanelService {
	
	private TransactionsDAO transactionsDAO;
	private BooksDAO booksDAO;
	
	public ReaderPanelService() {
		transactionsDAO = new TransactionsDAO();
		booksDAO = new BooksDAO();
	}
	
	public List<BorrowTransaction> getAllTransactions(Reader reader, EntityManager em) {
		return transactionsDAO.getAllTransactionsForReader(reader, em);
	}

	public boolean returnBook(BorrowTransaction transaction, EntityManager em) {
		Book book = transaction.getBook();
		int copies = book.getCopies();

		EntityTransaction trans = em.getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
		try {
			transactionsDAO.removeTransaction(transaction, em);
			book.setCopies(++copies);
			booksDAO.modifyBook(book, em);
		} catch (Exception e) {
			trans.rollback();
			return false;
		}
		trans.commit();
		return true;
	}
}