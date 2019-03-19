package jmyzik.librarymanager.service;

import java.util.List;

import jmyzik.librarymanager.dao.BooksDAO;
import jmyzik.librarymanager.dao.TransactionsDAO;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class ReaderTablePanelService {
	
	private TransactionsDAO transactionsDAO;
	private BooksDAO booksDAO;
	
	public ReaderTablePanelService() {
		transactionsDAO = new TransactionsDAO();
		booksDAO = new BooksDAO();
	}
	
	public List<BorrowTransaction> getAllTransactions(Reader reader) throws DatabaseUnavailableException {
		return transactionsDAO.getAllTransactions(reader);
	}

	public void returnBook(BorrowTransaction transaction) throws DatabaseUnavailableException {
		transactionsDAO.removeTransaction(transaction);
		Book book = transaction.getBook();
		int copies = book.getCopies();
		book.setCopies(++copies);
		booksDAO.modifyBook(book);
	}
}