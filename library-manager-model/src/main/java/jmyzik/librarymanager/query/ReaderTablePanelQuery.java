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
		TypedQuery<BorrowTransaction> query = entityManagerHandler.getEntityManager().createQuery
				("SELECT b FROM BorrowTransaction b WHERE b.borrower.id = :readerId", BorrowTransaction.class);
		query.setParameter("readerId", reader.getId());
		List<BorrowTransaction> transactionList = query.getResultList();
		return transactionList;
	}

	public void removeTransaction(BorrowTransaction transaction) throws DatabaseUnavailableException {
		open();
		entityManagerHandler.getEntityManager().remove(transaction);
		entityManagerHandler.getEntityTransaction().commit();
	}
}