package jmyzik.librarymanager.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.model.EntityManagerHandler;

public class ReaderTablePanelQuery extends AbstractQuery {
	
	public List<BorrowTransaction> getAllTransactions(Reader reader) throws DatabaseUnavailableException {
		open();
		long readerId = reader.getId();
		// TODO need to work on the query!!!!
		TypedQuery<BorrowTransaction> query = entityManagerHandler.getEntityManager().createQuery("SELECT b FROM BorrowTransaction b WHERE b.borrower.id = readerId", BorrowTransaction.class);
		List<BorrowTransaction> transactionList = query.getResultList();
		return transactionList;
	}
}