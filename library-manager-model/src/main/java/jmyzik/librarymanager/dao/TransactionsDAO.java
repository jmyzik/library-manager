package jmyzik.librarymanager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class TransactionsDAO extends GenericDAO {
	
	public List<BorrowTransaction> getAllTransactions(Reader reader) throws DatabaseUnavailableException {
		open();
		TypedQuery<BorrowTransaction> query = entityManagerHandler.getEntityManager().createQuery
				("SELECT b FROM BorrowTransaction b WHERE b.borrower.id = :readerId", BorrowTransaction.class);
		query.setParameter("readerId", reader.getId());
		List<BorrowTransaction> transactionList = query.getResultList();
		return transactionList;
	}

	public void addTransaction(BorrowTransaction transaction) throws DatabaseUnavailableException {
		open();
		entityManagerHandler.getEntityManager().persist(transaction);
		entityManagerHandler.getEntityTransaction().commit();
	}

	public void removeTransaction(BorrowTransaction transaction) throws DatabaseUnavailableException {
		open();
		entityManagerHandler.getEntityManager().remove(transaction);
		entityManagerHandler.getEntityTransaction().commit();
	}
}